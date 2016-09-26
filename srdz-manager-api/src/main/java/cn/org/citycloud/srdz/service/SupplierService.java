package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.SupplierBean;
import cn.org.citycloud.srdz.bean.SupplierSearch;
import cn.org.citycloud.srdz.entity.Supplier;

/**
 * 供应商service
 *
 * @author demon
 * @Date 2016/4/15 14:01
 */
public interface SupplierService {
    /**
     * 获取供应商列表（不分页）
     *
     * @return
     */
    public Object getAllSupplier(SupplierSearch supplierSearch);

    /**
     * 获取供应商列表
     *
     * @param supplierSearch 查询条件
     * @return Object
     */
    public Object getSupplierList(SupplierSearch supplierSearch);

    /**
     * 获取供应商详情
     *
     * @param supplierId 供应商id
     * @return Object
     */
    public Object getSupplierDetail(int supplierId);

    /**
     * 供应商账号审核通过
     *
     * @param supplierId 供应商id
     * @return Object
     */
    public Object passedSupplier(int supplierId);

    /**
     * 供应商账号审核驳回
     *
     * @param supplierId 供应商id
     * @param rejectedReason
     * @return Object
     */
    public Object rejectedSupplier(int supplierId, String rejectedReason);

    /**
     * 添加供应商
     *
     * @param supplierBean 供应商bean
     * @return Object
     */
    public Object addSupplier(SupplierBean supplierBean);

    /**
     * 修改供应商
     *
     * @param supplierBean 供应商bean
     * @param supplierId 供应商id
     *
     * @return Object
     */
    public Object updateSupplier(SupplierBean supplierBean, int supplierId);
}
