package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.AdvertisementBean;
import cn.org.citycloud.srdz.bean.AdvertisementSearch;
import cn.org.citycloud.srdz.service.AdvertisementService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 广告controller.
 *
 * @author demon
 * @Date 2016/4/25 14:51
 */
@RestController
@Api(tags = "广告管理")
@RequestMapping("/ads")
public class AdvertisementController {

    @Autowired
    private AdvertisementService adService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取广告banner列表",notes="获取广告banner列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="stime",value="开始时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="advertisementName",value="广告名称",required=false,dataType="string",paramType="query")
    })
    public Object getAdList(@ApiIgnore AdvertisementSearch adSearch) {
        return adService.getAdvertisementList(adSearch);
    }

    @RequestMapping(value = "/{adId}", method = RequestMethod.GET)
    @ApiOperation(value="获取广告banner详情",notes="获取广告banner详情",consumes="application/json",produces="application/json")
    public Object getAdDetail(@ApiParam(name="adId",value="广告Id",required=true) @PathVariable int adId) {
        return adService.getAdDetail(adId);
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    @ApiOperation(value="添加广告banner",notes="添加广告banner",consumes="application/json",produces="application/json")
    public Object addAd(@RequestBody @Valid AdvertisementBean adBean) {
        return adService.addAd(adBean);
    }

    @RequestMapping(value="/{adId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改广告banner",notes="修改广告banner",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateAd(@RequestBody @Valid AdvertisementBean adBean, @ApiParam(name="adId",value="活动Id",required=true) @PathVariable int adId) {
        return adService.updateAd(adBean, adId);
    }

    @RequestMapping(value = "/class", method = RequestMethod.GET)
    @ApiOperation(value="获取楼层广告banner列表",notes="获取楼层广告banner列表",consumes="application/json",produces="application/json")
    public Object getGoodsClassList() {
        return adService.getGoodsClassList();
    }


    @RequestMapping(value="/class/{classId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改楼层广告banner",notes="修改楼层广告banner",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateGoodsClass(@RequestBody String classImage, @ApiParam(name="classId",value="楼层Id",required=true) @PathVariable int classId) {
        return adService.updateGoodsClass(classImage, classId);
    }

    @RequestMapping(value = "/class/{classId}", method = RequestMethod.GET)
    @ApiOperation(value="获取楼层广告详情",notes="获取楼层广告详情",consumes="application/json",produces="application/json")
    public Object getGoodsClassDetail(@ApiParam(name="classId",value="楼层Id",required=true) @PathVariable int classId) {
        return adService.getGoodsClassDetail(classId);
    }
}
