package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.*;
import cn.org.citycloud.srdz.exception.BusinessErrorException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 商品管理.
 *
 * @author demon
 * @Date 2016/4/25 15:19
 */
public interface GoodsService {

    /**
     * 获取商品列表
     *
     * @param goodsSearch
     * @return
     */
    public Object getGoodsList(GoodsSearch goodsSearch);

    /**
     * 获取商品详细信息
     *
     * @param goodsId
     * @return
     */
    public Object getGoodsDetail(int goodsId);

//    /**
//     * 强制下架
//     *
//     * @param goodsId
//     * @return
//     */
//    public Object unShelve(int goodsId) throws IOException;

    /**
     * 商品批量禁售
     *
     * @param goodsIds
     */
    public void forbidSales(int[] goodsIds) throws IOException;

    /**
     * 批量取消禁售
     *
     * @param goodsIds
     */
    public void cancelForbid(int[] goodsIds) throws IOException;

    /**
     * 设为推荐
     *
     * @param goodsIds
     * @return
     */
    public void setRecommended(int[] goodsIds);

    /**
     * 取消推荐
     *
     * @param goodsIds
     * @return
     */
    public void cancelRecommended(int[] goodsIds);

    /**
     * 获取商品评论列表
     *
     * @param evaluateGoodsSearch
     * @return
     */
    public Object getGoodsCommentsList(EvaluateGoodsSearch evaluateGoodsSearch);

    /**
     * 屏蔽商品评论
     *
     * @param evaluateId
     * @return
     */
    public Object shieldComment(int evaluateId);

    /**
     * 取消屏蔽商品评论
     *
     * @param evaluateId
     * @return
     */
    public Object cancelShieldComment(int evaluateId);

    /**
     * 获取品牌列表
     *
     * @param brandSearch
     * @return
     */
    public Object getBrandList(BrandSearch brandSearch);

    /**
     * 获取品牌详细
     *
     * @param brandId
     * @return
     */
    public Object getBrandDetail(int brandId);

    /**
     * 添加品牌
     *
     * @param brandBean
     * @return
     */
    public Object addBrand(BrandBean brandBean);

    /**
     * 修改品牌
     *
     * @param brandBean
     * @param brandId
     * @return
     */
    public Object updateBrand(BrandBean brandBean, int brandId);

    /**
     * 获取一级分类
     *
     * @return
     */
    public Object getFirstLevelClass();

    /**
     * 根据父Id获取分类
     *
     * @param pid
     * @return
     */
    public Object getGoodClassByPid(int pid);

    /**
     * 添加分类
     *
     * @param classBean
     * @return
     */
    public Object addGoodClass(GoodsCategoryBean classBean) throws BusinessErrorException;

    /**
     * 修改分类
     *
     * @param classBean
     * @param classId
     * @return
     */
    public Object updateGoodClass(GoodsCategoryBean classBean, int classId) throws BusinessErrorException;

    /**
     * 获取分类列表（商品分类管理）
     *
     * @return
     */
    public Object getGoodClassList();

    /**
     * 获取分类列表（商品库管理）
     *
     * @param classSearch
     * @return
     */
    public Object getGoodClassList(GoodsClassSearch classSearch);

    /**
     * 获取分类详情
     *
     * @param classId
     * @return
     */
    public Object getGoodClassDetail(int classId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * 删除分类
     *
     * @param classId
     */
    public void deleteGoodClass(int classId) throws BusinessErrorException;

    /**
     * 根据三级分类名称模糊查询出三级分类名称列表
     *
     * @param name
     * @return
     */
    public Object getThdClassListByName(String name);
}
