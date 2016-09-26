package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.AdvertisementBean;
import cn.org.citycloud.srdz.bean.AdvertisementSearch;

/**
 * 广告service接口.
 *
 * @author demon
 * @Date 2016/4/25 14:26
 */
public interface AdvertisementService {

    /**
     * 获取广告列表
     *
     * @param adSearch
     * @return
     */
    public Object getAdvertisementList(AdvertisementSearch adSearch);

    /**
     * 获取广告详细信息
     *
     * @param adId
     * @return
     */
    public Object getAdDetail(int adId);

    /**
     * 添加广告
     *
     * @param adBean
     * @return
     */
    public Object addAd(AdvertisementBean adBean);

    /**
     * 修改广告
     *
     * @param adBean
     * @param adId
     * @return
     */
    public Object updateAd(AdvertisementBean adBean, int adId);

    /**
     * 获取楼层广告
     *
     * @return
     */
    public Object getGoodsClassList();

    /**
     * 修改楼层广告
     *
     * @param classImage
     * @param classId
     * @return
     */
    public Object updateGoodsClass(String classImage, int classId);

    /**
     * 查询楼层广告详情
     *
     * @param classId
     * @return
     */
    public Object getGoodsClassDetail(int classId);
}
