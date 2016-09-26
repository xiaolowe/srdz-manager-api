package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.NoticeBean;
import cn.org.citycloud.srdz.bean.page.Page;
import cn.org.citycloud.srdz.entity.Notice;
import cn.org.citycloud.srdz.repository.NoticeDao;
import cn.org.citycloud.srdz.service.NoticeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 17:23
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Object getNoticeList(Page page) {
        int pageNo = page.getPageNo();
        int size = page.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(pageNo - 1, size, sort);
        return noticeDao.findAll(pageable);

    }

    @Override
    public Object getNoticeDetail(int noticeId) {
        return noticeDao.findOne(noticeId);
    }

    @Override
    public Object addNotice(NoticeBean noticeBean) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeBean, notice);
        notice.setCreateDate(new Date());
        notice.setUpdateDate(new Date());
        noticeDao.save(notice);
        return notice;
    }

    @Override
    public Object updateNotice(NoticeBean noticeBean, int noticeId) {
        Notice notice = noticeDao.findOne(noticeId);
        BeanUtils.copyProperties(noticeBean, notice);
        notice.setUpdateDate(new Date());
        noticeDao.save(notice);
        return notice;
    }
}
