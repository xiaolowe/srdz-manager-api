package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.*;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.service.MarketingService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * 营销管理controller.
 *
 * @author demon
 * @Date 2016/4/20 15:37
 */
@RestController
@Api(tags = "营销管理")
@RequestMapping("/marketing")
public class MarketingController {

    @Autowired
    private MarketingService marketingService;

    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    @ApiOperation(value="获取营销活动列表",notes="获取营销活动列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="status",value="状态（0 禁用   1 启用）",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="start",value="开始时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="end",value="结束时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="activityName",value="活动名称",required=false,dataType="string",paramType="query")
    })
    public Object getActivityList(@ApiIgnore ActivitySearch activitySearch) {
        return marketingService.getActivityList(activitySearch);
    }

    @RequestMapping(value = "/activity/{activityId}", method = RequestMethod.GET)
    @ApiOperation(value="获取营销活动详情",notes="获取营销活动详情",consumes="application/json",produces="application/json")
    public Object getActivityDetail(@ApiParam(name="activityId",value="活动Id",required=true) @PathVariable int activityId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return marketingService.getActivityDetail(activityId);
    }

    @RequestMapping(value="/activity",method=RequestMethod.POST)
    @ApiOperation(value="添加营销活动",notes="添加营销活动",consumes="application/json",produces="application/json")
    public Object addActivity(@RequestBody @Valid ActivityBean activityBean) throws BusinessErrorException {
        return marketingService.addActivity(activityBean);
    }

    @RequestMapping(value="/activity/{activityId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改营销活动信息",notes="修改营销活动信息",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateActivity(@RequestBody @Valid ActivityBean activityBean, @ApiParam(name="activityId",value="活动Id",required=true) @PathVariable int activityId) throws BusinessErrorException {
        return marketingService.updateActivity(activityBean, activityId);
    }

//    @RequestMapping(value = "/coupon", method = RequestMethod.GET)
//    @ApiOperation(value="获取优惠券列表",notes="获取优惠券列表",consumes="application/json",produces="application/json")
//    public Object getPlatformCouponList(@ApiIgnore PlatformCouponSearch platformCouponSearch) {
//        return marketingService.getPlatformCouponList(platformCouponSearch);
//    }

    @RequestMapping(value = "/coupon/{couponId}", method = RequestMethod.GET)
    @ApiOperation(value="获取优惠券详情",notes="获取优惠券详情",consumes="application/json",produces="application/json")
    public Object getPlatformCouponDetail(@ApiParam(name="couponId",value="优惠券Id",required=true) @PathVariable int couponId) {
        return marketingService.getPlatformCouponDetail(couponId);
    }

    @RequestMapping(value="/coupon",method=RequestMethod.POST)
    @ApiOperation(value="添加优惠券",notes="添加优惠券",consumes="application/json",produces="application/json")
    public Object addPlatformCoupon(@RequestBody @Valid PlatformCouponBean platformCouponBean) throws BusinessErrorException {
        return marketingService.addPlatformCoupon(platformCouponBean);
    }

    @RequestMapping(value="/coupon/{couponId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改优惠券",notes="修改优惠券",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updatePlatformCoupon(@RequestBody @Valid PlatformCouponBean platformCouponBean, @ApiParam(name="couponId",value="优惠券Id",required=true) @PathVariable int couponId) throws BusinessErrorException {
        return marketingService.updatePlatformCoupon(platformCouponBean, couponId);
    }

    @RequestMapping(value="/coupon/{couponId}/stop",method=RequestMethod.PUT)
    @ApiOperation(value="停发优惠券",notes="停发优惠券",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object stopPlatformCoupon(@ApiParam(name="couponId",value="优惠券Id",required=true) @PathVariable int couponId) {
        return marketingService.stopPlatformCoupon(couponId);
    }

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ApiOperation(value="获取特惠商品列表",notes="获取特惠商品列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="goodsClassId",value="商品分类Id(一级分类)",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="goodsVerify",value="商品审核（0 未审核 1 审核）",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="goodsName",value="商品名称",required=false,dataType="string",paramType="query")
    })
    public Object getSpecialGoodsList(@ApiIgnore GoodsSearch goodsSearch) {
        return marketingService.getSpecialGoodsList(goodsSearch);
    }

    @RequestMapping(value = "/goods/{goodsId}", method = RequestMethod.GET)
    @ApiOperation(value="获取特惠商品详情",notes="获取特惠商品详情",consumes="application/json",produces="application/json")
    public Object getSpecialGoodsDetail(@ApiParam(name="goodsId",value="商品Id",required=true) @PathVariable int goodsId) {
        return marketingService.getSpecialGoodsDetail(goodsId);
    }

    @RequestMapping(value = "/goods/{goodsId}/passed", method = RequestMethod.PUT)
    @ApiOperation(value="特惠商品审核通过",notes="特惠商品审核通过",consumes="application/json",produces="application/json")
    public Object passedSpecialGoods(@ApiParam(name="goodsId",value="商品Id",required=true) @PathVariable int goodsId) {
        return marketingService.passedSpecialGoods(goodsId);
    }

    @RequestMapping(value = "/goods/{goodsId}/rejected", method = RequestMethod.PUT)
    @ApiOperation(value="特惠商品审核驳回",notes="特惠商品审核驳回",consumes="application/json",produces="application/json")
    public Object rejectedSpecialGoods(@ApiParam(name="goodsId",value="商品Id",required=true) @PathVariable int goodsId) {
        return marketingService.rejectedSpecialGoods(goodsId);
    }

    @RequestMapping(value = "/goods/{goodsId}/unshelved", method = RequestMethod.PUT)
    @ApiOperation(value="特惠商品下架",notes="特惠商品下架",consumes="application/json",produces="application/json")
    public Object unShelveGoods(@ApiParam(name="goodsId",value="商品Id",required=true) @PathVariable int goodsId) {
        return marketingService.unShelveGoods(goodsId);
    }

    @RequestMapping(value = "/downInvoledList/{actId}", method = RequestMethod.GET)
    @ApiOperation(value="下载参与名单",notes="下载参与名单",consumes="application/json",produces="application/json")
    public void downInvoledList(@ApiParam(name="actId",value="活动Id",required=true) @PathVariable int actId, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        marketingService.downInvoledList(actId, os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("优惠券参与者名单.xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];
        int bytesRead;
        // Simple read/write loop.
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bos.close();
    }
}
