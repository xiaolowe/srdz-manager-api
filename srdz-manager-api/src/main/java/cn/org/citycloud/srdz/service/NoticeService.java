package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.NoticeBean;
import cn.org.citycloud.srdz.bean.page.Page;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 17:20
 */
public interface NoticeService {

    /**
     * 获取公告列表
     *
     * @param page
     * @return
     */
    public Object getNoticeList(Page page);

    /**
     * 获取公告详情
     *
     * @param noticeId
     * @return
     */
    public Object getNoticeDetail(int noticeId);

    /**
     * 添加公告
     *
     * @param noticeBean
     * @return
     */
    public Object addNotice(NoticeBean noticeBean);

    /**
     * 修改公告
     *
     * @param noticeBean
     * @param noticeId
     * @return
     */
    public Object updateNotice(NoticeBean noticeBean, int noticeId);
}
