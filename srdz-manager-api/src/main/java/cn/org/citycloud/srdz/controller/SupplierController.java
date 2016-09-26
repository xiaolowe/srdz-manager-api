package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.RejectedBean;
import cn.org.citycloud.srdz.bean.SupplierBean;
import cn.org.citycloud.srdz.bean.SupplierSearch;
import cn.org.citycloud.srdz.core.BaseController;
import cn.org.citycloud.srdz.service.SupplierService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 供应商controller.
 *
 * @author Administrator
 * @Date 2016/4/15 14:40
 */
@RestController
@Api(tags = "供应商管理")
@RequestMapping("/supplier")
public class SupplierController extends BaseController {

    @Autowired
    private SupplierService supplierService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有已审核的供应商列表", notes = "获取所有已审核的供应商列表", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", required = false, dataType = "string", paramType = "query")
    })
    public Object getAllSupplier(@ApiIgnore SupplierSearch supplierSearch) {
        return supplierService.getAllSupplier(supplierSearch);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "获取供应商列表", notes = "获取供应商列表", consumes = "application/json", produces = "application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name = "levelId", value = "供应商等级", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态 0 默认（未认证）  1 已认证     2  已审核    3  驳回  4  冻结", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "supplierName", value = "供应商名称", required = false, dataType = "string", paramType = "query")
    })
    public Object getSupplierList(@ApiIgnore SupplierSearch supplierSearch) {
        return supplierService.getSupplierList(supplierSearch);
    }

    @RequestMapping(value = "/{supplierId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取某条供应商信息", notes = "获取某条供应商详细信息", consumes = "application/json", produces = "application/json")
    public Object getSupplierDetail(@ApiParam(name = "supplierId", value = "供应商ID", required = true) @PathVariable int supplierId) {
        return supplierService.getSupplierDetail(supplierId);
    }


    @RequestMapping(value = "/{supplierId}/passed", method = RequestMethod.PUT)
    @ApiOperation(value = "供应商审核通过", notes = "审核通过某某个供应商", consumes = "application/json", produces = "application/json")
    public Object passedSupplier(@ApiParam(name = "supplierId", value = "供应商ID", required = true) @PathVariable int supplierId) {
        return supplierService.passedSupplier(supplierId);
    }

    @RequestMapping(value = "/{supplierId}/rejected", method = RequestMethod.PUT)
    @ApiOperation(value = "供应商审核驳回", notes = "审核驳回某个供应商", consumes = "application/json", produces = "application/json")
    public Object rejectedSupplier(@ApiParam(name = "supplierId", value = "供应商ID", required = true) @PathVariable int supplierId, @RequestBody @Valid RejectedBean rejectedBean) {
        return supplierService.rejectedSupplier(supplierId, rejectedBean.getReason());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "添加供应商", notes = "添加供应商信息", consumes = "application/json", produces = "application/json")
    public Object addSupplier(@RequestBody @Valid SupplierBean supplierBean) {
        return supplierService.addSupplier(supplierBean);
    }

    @RequestMapping(value = "/{supplierId}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改供应商信息", notes = "修改供应商信息", consumes = "application/json", produces = "application/json", httpMethod = "PUT")
    public Object updateMessage(@RequestBody @Valid SupplierBean supplierBean, @ApiParam(name = "supplierId", value = "供应商Id") @PathVariable int supplierId) {
        return supplierService.updateSupplier(supplierBean, supplierId);
    }

}
