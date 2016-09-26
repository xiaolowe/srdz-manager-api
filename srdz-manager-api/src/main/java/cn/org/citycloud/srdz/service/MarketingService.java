package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.ActivityBean;
import cn.org.citycloud.srdz.bean.ActivitySearch;
import cn.org.citycloud.srdz.bean.GoodsSearch;
import cn.org.citycloud.srdz.bean.PlatformCouponBean;
import cn.org.citycloud.srdz.exception.BusinessErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 营销管理service接口.
 *
 * @author demon
 * @Date 2016/4/20 14:16
 */
public interface MarketingService {
    /**
     * 获取活动列表
     *
     * @param activitySearch
     * @return
     */
    public Object getActivityList(ActivitySearch activitySearch);

    /**
     * 获取活动详情
     *
     * @param activityId
     * @return
     */
    public Object getActivityDetail(int activityId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * 添加活动
     *
     * @param activityBean
     * @return
     */
    public Object addActivity(ActivityBean activityBean) throws BusinessErrorException;

    /**
     * 修改活动详情
     *
     * @param activityBean
     * @param activityId
     * @return
     */
    public Object updateActivity(ActivityBean activityBean, int activityId) throws BusinessErrorException;

//    /**
//     * 获取平台优惠券列表
//     *
//     * @param platformCouponSearch
//     * @return
//     */
//    public Object getPlatformCouponList(PlatformCouponSearch platformCouponSearch);

    /**
     * 获取平台优惠券详情
     *
     * @param couponId
     * @return
     */
    public Object getPlatformCouponDetail(int couponId);

    /**
     * 添加平台优惠券
     *
     * @param platformCouponBean
     * @return
     */
    public Object addPlatformCoupon(PlatformCouponBean platformCouponBean) throws BusinessErrorException;

    /**
     * 修改平台优惠券
     *
     * @param platformCouponBean
     * @param couponId
     * @return
     */
    public Object updatePlatformCoupon(PlatformCouponBean platformCouponBean, int couponId) throws BusinessErrorException;

    /**
     * 停发平台优惠券
     *
     * @param couponId
     * @return
     */
    public Object stopPlatformCoupon(int couponId);

    /**
     * 获取特惠商品列表
     *
     * @param goodsSearch
     * @return
     */
    public Object getSpecialGoodsList(GoodsSearch goodsSearch);

    /**
     * 获取特惠商品详细信息
     *
     * @param goodsId
     * @return
     */
    public Object getSpecialGoodsDetail(int goodsId);

    /**
     * 特惠商品审核通过
     *
     * @param goodsId
     * @return
     */
    public Object passedSpecialGoods(int goodsId);

    /**
     * 特惠商品审核驳回
     *
     * @param goodsId
     * @return
     */
    public Object rejectedSpecialGoods(int goodsId);

    /**
     * 特惠商品下架
     *
     * @param goodsId
     * @return
     */
    public Object unShelveGoods(int goodsId);

    /**
     * 下载参与名单
     *
     * @param actId 活动id
     * @param os
     */
    public void downInvoledList(int actId, ByteArrayOutputStream os) throws IOException;
}
