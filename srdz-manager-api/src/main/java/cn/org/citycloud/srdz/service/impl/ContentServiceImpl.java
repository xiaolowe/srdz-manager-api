package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.ContentBean;
import cn.org.citycloud.srdz.entity.Content;
import cn.org.citycloud.srdz.entity.ContentModule;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.repository.ContentDao;
import cn.org.citycloud.srdz.service.ContentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * 内容管理service实现.
 *
 * @author demon
 * @Date 2016/4/19 13:59
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Override
    public Object getContentList() {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return contentDao.findAll(sort);
    }

    @Override
    public Object getContentDetail(int contetnId) {
        return contentDao.findOne(contetnId);
    }

    @Override
    public Object addContent(ContentBean contentBean) throws BusinessErrorException {
        checkTheTotal(contentBean.getContentModuleId());
        Content content = new Content();
        ContentModule contentModule = new ContentModule();
        contentModule.setContentModuleId(contentBean.getContentModuleId());
        BeanUtils.copyProperties(contentBean, content);
        content.setCreateDate(new Date());
        content.setUpdateDate(new Date());
        content.setContentModule(contentModule);
        contentDao.save(content);
        return content;
    }

    @Override
    public Object updateContent(ContentBean contentBean, int contentId) {
        Content content = contentDao.findOne(contentId);
        ContentModule contentModule = new ContentModule();
        contentModule.setContentModuleId(contentBean.getContentModuleId());
        BeanUtils.copyProperties(contentBean, content);
        content.setUpdateDate(new Date());
        content.setContentModule(contentModule);
        contentDao.save(content);
        return content;
    }

    /**
     * 检查每个模块下的文章数量
     *
     * @param moduleId
     * @throws BusinessErrorException
     */
    private void checkTheTotal(int moduleId) throws BusinessErrorException {
        Specification<Content> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate = cb.and(predicate, cb.equal(root.get("contentModule").get("contentModuleId"), moduleId));
            return query.where(predicate).getRestriction();
        };
        List<Content> list = contentDao.findAll(spec);
        if (list.size() > 5) {
            throw new BusinessErrorException("019", "每个分类下的文章数量不能超过5篇");
        }
    }
}
