package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.*;
import cn.org.citycloud.srdz.service.SalesService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 分销商controller.
 *
 * @author demon
 * @Date 2016/4/19 17:16
 */
@RestController
@Api(tags = "分销管理")
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @RequestMapping(value = "/member", method = RequestMethod.GET)
    @ApiOperation(value="获取分销会员列表",notes="获取分销会员列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="status",value="用户状态 1为开启 0为关闭",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="salesMemberName",value="会员名称",required=false,dataType="string",paramType="query")
    })
    public Object getSalesMemberList(@ApiIgnore SalesMemberSearch salesMemberSearch) {
        return salesService.getSalesMemberList(salesMemberSearch);
    }

    @RequestMapping(value = "/member/{salesMemberId}", method = RequestMethod.GET)
    @ApiOperation(value="获取某条分销会员详情",notes="获取某条分销会员详细信息",consumes="application/json",produces="application/json")
    public Object getSalesMemberDetail(@ApiParam(name="salesMemberId",value="分销会员Id",required=true) @PathVariable int salesMemberId) {
        return salesService.getSalesMemberDetail(salesMemberId);
    }

    @RequestMapping(value="/member/{salesMemberId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改会员信息",notes="修改会员信息",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateSalesMember(@RequestBody @Valid SalesMemberBean salesMemberBean, @ApiParam(name="salesMemberId",value="分销店铺Id")@PathVariable int salesMemberId) {
        return salesService.updateSalesMember(salesMemberBean, salesMemberId);
    }

    @RequestMapping(value="/member/{salesMemberId}/passed",method=RequestMethod.PUT)
    @ApiOperation(value="会员审核通过",notes="会员审核通过",consumes="application/json",produces="application/json")
    public Object passedSaledMember(@ApiParam(name="salesMemberId",value="会员Id",required=true) @PathVariable int salesMemberId) {
        return salesService.passedSalesMember(salesMemberId);
    }

    @RequestMapping(value="/member/{salesMemberId}/rejected",method=RequestMethod.PUT)
    @ApiOperation(value="会员审核驳回",notes="会员审核驳回",consumes="application/json",produces="application/json")
    public Object rejectedSaledMember(@ApiParam(name="salesMemberId",value="会员Id",required=true) @PathVariable int salesMemberId, @RequestBody @Valid RejectedBean rejectedBean) {
        return salesService.rejectedSalesMember(salesMemberId, rejectedBean.getReason());
    }

    @RequestMapping(value="/member/{salesMemberId}/status",method=RequestMethod.PUT)
    @ApiOperation(value="修改会员状态",notes="修改会员状态",consumes="application/json",produces="application/json")
    public Object closeMember(@ApiParam(name="salesMemberId",value="会员Id",required=true) @PathVariable int salesMemberId, @RequestBody @Valid int status) {
        return salesService.changeStatus(salesMemberId, status);
    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    @ApiOperation(value="获取分销店铺列表",notes="获取分销店铺列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="status",value="店铺状态 0  待审核   1 已审核  2 冻结  3 审核驳回",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="salesShopName",value="店铺名称",required=false,dataType="string",paramType="query")
    })
    public Object getSalesShopList(@ApiIgnore SalesShopSearch salesShopSearch) {
        return salesService.getSalesShopList(salesShopSearch);
    }

    @RequestMapping(value = "/shop/{salesShopId}", method = RequestMethod.GET)
    @ApiOperation(value="获取分销店铺详情",notes="获取分销店铺详情",consumes="application/json",produces="application/json")
    public Object getSalesShopDetail(@ApiParam(name="salesShopId",value="分销店铺Id",required=true) @PathVariable int salesShopId) {
        return salesService.getSalesShopDetail(salesShopId);
    }

    @RequestMapping(value="/shop/{salesShopId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改店铺信息",notes="修改店铺信息",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateSalesShop(@RequestBody @Valid SalesShopBean salesShopBean, @ApiParam(name="salesShopId",value="分销店铺Id")@PathVariable int salesShopId) {
        return salesService.updateSalesShop(salesShopBean, salesShopId);
    }
}
