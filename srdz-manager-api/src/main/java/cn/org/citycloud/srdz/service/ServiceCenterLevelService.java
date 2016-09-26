package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.ServiceCenterLevelBean;

/**
 * 服务中心service接口.
 *
 * @author demon
 * @Date 2016/4/18 14:16
 */
public interface ServiceCenterLevelService {
    /**
     * 获取服务中心等级列表
     *
     * @return Object
     */
    public Object getServiceCenterLevelList();

    /**
     * 获取等级详情
     *
     * @param levelId
     * @return
     */
    public Object getServiceCenterLevelDetail(int levelId);

    /**
     * 添加服务中心等级
     *
     * @param serviceCenterLevelBean 服务中心等级bean
     * @return Object
     */
    public Object addServiceCenterLevel(ServiceCenterLevelBean serviceCenterLevelBean);

    /**
     * 修改服务中心等级信息
     *
     * @param serviceCenterLevelBean 服务中心等级bean
     * @param serviceCenterlevelId 服务中心等级id
     * @return Object
     */
    public Object updateServiceCenterLevel(ServiceCenterLevelBean serviceCenterLevelBean, int serviceCenterlevelId);
}
