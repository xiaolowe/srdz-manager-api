package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.SupplierLevelBean;
import cn.org.citycloud.srdz.service.SupplierLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 供应商等级controller
 *
 * @author demon
 * @Date 2016/4/18 8:35
 */
@RestController
@Api(tags = "供应商管理")
@RequestMapping("/supplier/level")
public class SupplierLevelController {

    @Autowired
    private SupplierLevelService supplierLevelService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取供应商等级列表",notes="获取供应商等级列表",consumes="application/json",produces="application/json")
    public Object getSupplierLevelList() {
        return supplierLevelService.getSupplierLevelList();
    }

    @RequestMapping(value="/{levelId}",method=RequestMethod.GET)
    @ApiOperation(value="获取某条供应商等级信息",notes="获取某条供应商等级信息",consumes="application/json",produces="application/json")
    public Object getServiceCenterDetail(@ApiParam(name="levelId",value="等级id",required=true) @PathVariable int levelId) {
        return supplierLevelService.getSupplierLevelDetail(levelId);
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    @ApiOperation(value="添加供应商等级",notes="添加供应商等级信息",consumes="application/json",produces="application/json")
    public Object addSupplier(@RequestBody @Valid SupplierLevelBean supplierLevelBean){
        return supplierLevelService.addSupplierLevel(supplierLevelBean);
    }

    @RequestMapping(value="/{supplierLevelId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改供应商等级信息",notes="修改供应商等级信息",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateMessage(@RequestBody @Valid SupplierLevelBean supplierLevelBean,@ApiParam(name="supplierLevelId",value="供应商等级Id")@PathVariable int supplierLevelId){
        return supplierLevelService.updateSupplierLevel(supplierLevelBean, supplierLevelId);
    }
}
