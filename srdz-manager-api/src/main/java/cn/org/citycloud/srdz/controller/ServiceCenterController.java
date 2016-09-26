package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.AfterSaleBean;
import cn.org.citycloud.srdz.bean.AfterSaleSearch;
import cn.org.citycloud.srdz.bean.ServiceCenterBean;
import cn.org.citycloud.srdz.bean.ServiceCenterSearch;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.service.ServiceCenterService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

/**
 * 服务中心controller.
 *
 * @author demon
 * @Date 2016/4/18 13:14
 */
@RestController
@Api(tags = "服务中心管理")
@RequestMapping("/serviceCenter")
public class ServiceCenterController {

    @Autowired
    private ServiceCenterService serviceCenterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取服务中心列表",notes="获取服务中心列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="status",value="服务中心状态，0 待审核  1  已审核    2  驳回  3  冻结",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="serviceCenterLevelId",value="服务中心等级id",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="serviceCenterName",value="服务中心名称",required=false,dataType="string",paramType="query")
    })
    public Object getServiceCenterList(@ApiIgnore ServiceCenterSearch serviceCenterSearch) {
        return serviceCenterService.getServiceCenterList(serviceCenterSearch);
    }

    @RequestMapping(value="/{serviceCenterId}",method=RequestMethod.GET)
    @ApiOperation(value="获取某条服务中心信息",notes="获取某条服务中心详细信息",consumes="application/json",produces="application/json")
    public Object getServiceCenterDetail(@ApiParam(name="serviceCenterId",value="服务中心ID",required=true) @PathVariable int serviceCenterId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return serviceCenterService.getServiceCenterDetail(serviceCenterId);
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    @ApiOperation(value="添加服务中心",notes="添加服务中心信息",consumes="application/json",produces="application/json")
    public Object addServiceCenter(@RequestBody @Valid ServiceCenterBean serviceCenterBean) throws BusinessErrorException {
        return serviceCenterService.addServiceCenter(serviceCenterBean);
    }

    @RequestMapping(value="/resetPwd/{serviceCenterId}",method=RequestMethod.PUT)
    @ApiOperation(value="重置密码",notes="重置密码",consumes="application/json",produces="application/json",httpMethod="PUT")
    public void resetPwd(@ApiParam(name="serviceCenterId",value="服务中心Id")@PathVariable int serviceCenterId) throws BusinessErrorException {
        serviceCenterService.resetPwd(serviceCenterId);
    }

    @RequestMapping(value="/{serviceCenterId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改服务中心信息",notes="修改服务中心信息",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateServiceCenter(@RequestBody @Valid ServiceCenterBean serviceCenterBean, @ApiParam(name="serviceCenterId",value="服务中心Id")@PathVariable int serviceCenterId) throws BusinessErrorException {
        return serviceCenterService.updateServiceCenter(serviceCenterBean, serviceCenterId);
    }

    @RequestMapping(value = "/afterSale/", method = RequestMethod.GET)
    @ApiOperation(value="获取服务中心售后处理列表",notes="获取服务中心售后处理列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="memberName",value="用户名称",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="supplierName",value="供应商名称",required=false,dataType="string",paramType="query")
    })
    public Object getAfterSaleList(@ApiIgnore AfterSaleSearch afterSaleSearch) {
        return serviceCenterService.getAfterSaleList(afterSaleSearch);
    }

    @RequestMapping(value="/afterSale/{afterSaleId}",method=RequestMethod.GET)
    @ApiOperation(value="获取售后处理详情",notes="获取售后处理详情",consumes="application/json",produces="application/json")
    public Object getAfterSaleDetail(@ApiParam(name="afterSaleId",value="售后处理ID",required=true) @PathVariable int afterSaleId) {
        return serviceCenterService.getAfterSaleDetail(afterSaleId);
    }

    @RequestMapping(value="/afterSale/{afterSaleId}",method=RequestMethod.PUT)
    @ApiOperation(value="提交售后处理",notes="提交售后处理",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object handledAfterSale(@RequestBody @Valid AfterSaleBean afterSaleBean, @ApiParam(name="afterSaleId",value="售后处理ID",required=true) @PathVariable int afterSaleId) {
        return serviceCenterService.handledAfterSale(afterSaleBean, afterSaleId);
    }

}
