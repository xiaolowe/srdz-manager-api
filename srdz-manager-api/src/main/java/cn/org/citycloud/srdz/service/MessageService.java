package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.MessageSearch;

/**
 * 消息管理service接口.
 *
 * @author demon
 * @Date 2016/4/22 9:19
 */
public interface MessageService {
    /**
     * 获取消息列表
     *
     * @param messageSearch
     * @return
     */
    public Object getMessageList(MessageSearch messageSearch);

    /**
     * 获取消息详情
     *
     * @param messageId
     * @return
     */
    public Object getMessageDetail(int messageId);

    /**
     * 获取当前用户的未读信息列表
     *
     * @return
     */
    public Object getUnreadList();

    /**
     * 获取当前用户的消息列表（所有）
     *
     * @return
     */
    public Object getAllMsg();
}
