package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.MessageSearch;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.Message;
import cn.org.citycloud.srdz.repository.MessageDao;
import cn.org.citycloud.srdz.service.MessageService;
import cn.org.citycloud.srdz.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 消息管理service接口实现.
 *
 * @author demon
 * @Date 2016/4/22 9:38
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public Object getMessageList(MessageSearch messageSearch) {
        int page = messageSearch.getPageNo();
        int size = messageSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<Message> spec = new Specification<Message>() {

            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if(!StringUtils.isEmpty(messageSearch.getStime())) {
                    String stime = messageSearch.getStime() + " 00:00:00";
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createDate"), DateUtils.parse(stime, DateUtils.DATE_PATTERN_1)));
                }
                if(!StringUtils.isEmpty(messageSearch.getEtime())) {
                    String etime = messageSearch.getEtime() + " 23:59:59";
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createDate"), DateUtils.parse(etime, DateUtils.DATE_PATTERN_1)));
                }
                if(!StringUtils.isEmpty(messageSearch.getKeyWord())) {
                    predicate = cb.and(predicate, cb.like(root.get("messageContent"), "%" + messageSearch.getKeyWord() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return messageDao.findAll(spec, pageable);
    }

    @Override
    public Object getMessageDetail(int messageId) {
        return messageDao.findOne(messageId);
    }

    @Override
    public Object getUnreadList() {
        Specification<Message> spec = new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("platform"), Constants.MSG_ADMIN));
                predicate = cb.and(predicate, cb.equal(root.get("receiverId"), Constants.USER_TOKEN.getUserId()));
                predicate = cb.and(predicate, cb.equal(root.get("status"), 0));
                return query.where(predicate).getRestriction();
            }
        };
        return messageDao.findAll(spec);
    }

    @Override
    public Object getAllMsg() {
        Specification<Message> spec = new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("platform"), Constants.MSG_ADMIN));
                predicate = cb.and(predicate, cb.equal(root.get("receiverId"), Constants.USER_TOKEN.getUserId()));
                return query.where(predicate).getRestriction();
            }
        };
        return messageDao.findAll(spec);
    }
}
