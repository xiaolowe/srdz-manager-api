package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.AfterSaleBean;
import cn.org.citycloud.srdz.bean.AfterSaleSearch;
import cn.org.citycloud.srdz.bean.ServiceCenterBean;
import cn.org.citycloud.srdz.bean.ServiceCenterSearch;
import cn.org.citycloud.srdz.exception.BusinessErrorException;

import java.lang.reflect.InvocationTargetException;

/**
 * 服务中心service接口.
 *
 * @author demon
 * @Date 2016/4/18 12:54
 */
public interface ServiceCenterService {

    /**
     * 获取服务中心列表
     *
     * @param serviceCenterSearch
     * @return
     */
    public Object getServiceCenterList(ServiceCenterSearch serviceCenterSearch);

    /**
     * 获取服务中心详情
     *
     * @param serviceCenterId
     * @return
     */
    public Object getServiceCenterDetail(int serviceCenterId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * 添加服务中心
     *
     * @param serviceCenterBean
     * @return
     */
    public Object addServiceCenter(ServiceCenterBean serviceCenterBean) throws BusinessErrorException;

    /**
     * 修改服务中心
     *
     * @param serviceCenterBean
     * @param serviceCenterId
     * @return
     */
    public Object updateServiceCenter(ServiceCenterBean serviceCenterBean, int serviceCenterId) throws BusinessErrorException;

    /**
     * 获取服务中心售后处理列表
     *
     * @param afterSaleSearch
     * @return
     */
    public Object getAfterSaleList(AfterSaleSearch afterSaleSearch);

    /**
     * 获取服务中心售后处理详情
     *
     * @param afterSaleId
     * @return
     */
    public Object getAfterSaleDetail(int afterSaleId);

    /**
     * 提交服务中心售后处理
     *
     * @param afterSaleBean
     * @param afterSaleId
     * @return
     */
    public Object handledAfterSale(AfterSaleBean afterSaleBean, int afterSaleId);

    /**
     * 服务中心重置密码
     *
     * @param serviceId
     */
    void resetPwd(int serviceId);
}
