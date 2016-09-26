package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.ContentBean;
import cn.org.citycloud.srdz.exception.BusinessErrorException;

/**
 * 内容管理service接口.
 *
 * @author demon
 * @Date 2016/4/19 13:49
 */
public interface ContentService {

    /**
     * 获取内容管理列表
     *
     * @return
     */
    public Object getContentList();

    /**
     * 获取内容详情
     *
     * @param contetnId
     * @return
     */
    public Object getContentDetail(int contetnId);

    /**
     * 添加内容
     *
     * @param contentBean
     * @return
     */
    public Object addContent(ContentBean contentBean) throws BusinessErrorException;

    /**
     * 修改内容
     *
     * @param contentBean
     * @param contentId
     * @return
     */
    public Object updateContent(ContentBean contentBean, int contentId);
}
