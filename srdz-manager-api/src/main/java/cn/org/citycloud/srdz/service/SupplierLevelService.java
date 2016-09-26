package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.SupplierLevelBean;

/**
 * 供应商等级service接口.
 *
 * @author demon
 * @Date 2016/4/15 14:01
 */
public interface SupplierLevelService {

    /**
     * 获取供应商等级列表
     *
     * @return Object
     */
    public Object getSupplierLevelList();

    /**
     * 获取供应商等级详情
     *
     * @param levelId
     * @return
     */
    public Object getSupplierLevelDetail(int levelId);

    /**
     * 添加供应商等级
     *
     * @param supplierLevelBean 供应商等级bean
     * @return Object
     */
    public Object addSupplierLevel(SupplierLevelBean supplierLevelBean);

    /**
     * 修改供应商等级信息
     *
     * @param supplierLevelBean 供应商等级bean
     * @param supplierlevelId 供应商等级id
     * @return Object
     */
    public Object updateSupplierLevel(SupplierLevelBean supplierLevelBean, int supplierlevelId);
}
