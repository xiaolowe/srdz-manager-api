package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.SalesMemberBean;
import cn.org.citycloud.srdz.bean.SalesMemberSearch;
import cn.org.citycloud.srdz.bean.SalesShopBean;
import cn.org.citycloud.srdz.bean.SalesShopSearch;

/**
 * 分销管理service接口.
 *
 * @author demon
 * @Date 2016/4/19 16:01
 */
public interface SalesService {

    /**
     * 获取分销商列表
     *
     * @param salesMemberSearch
     * @return
     */
    public Object getSalesMemberList(SalesMemberSearch salesMemberSearch);

    /**
     * 获取分销商详情
     *
     * @param salesMemberId
     * @return
     */
    public Object getSalesMemberDetail(int salesMemberId);

    /**
     * 修改分销商
     *
     * @param salesMemberBean
     * @param salesMemberId
     * @return
     */
    public Object updateSalesMember(SalesMemberBean salesMemberBean, int salesMemberId);

    /**
     * 分销商审核通过
     *
     * @param salesMemberId
     * @return
     */
    public Object passedSalesMember(int salesMemberId);

    /**
     * 分销商审核驳回
     *
     * @param salesMemberId
     * @param reason
     * @return
     */
    public Object rejectedSalesMember(int salesMemberId, String reason);

    /**
     * 获取分销店铺列表
     *
     * @param salesShopSearch
     * @return
     */
    public Object getSalesShopList(SalesShopSearch salesShopSearch);

    /**
     * 获取分销店铺详情
     *
     * @param salesShopId
     * @return
     */
    public Object getSalesShopDetail(int salesShopId);

    /**
     * 修改分销店铺
     *
     * @param salesShopBean
     * @param salesShopId
     * @return
     */
    public Object updateSalesShop(SalesShopBean salesShopBean, int salesShopId);

    /**
     * 修改分销会员状态
     *
     * @param memberId
     * @param status
     * @return
     */
    public Object changeStatus(int memberId, int status);
}
