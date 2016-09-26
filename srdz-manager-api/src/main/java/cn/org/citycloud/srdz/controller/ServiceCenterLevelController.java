package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.ServiceCenterLevelBean;
import cn.org.citycloud.srdz.service.ServiceCenterLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 服务中心等级controller.
 *
 * @author demon
 * @Date 2016/4/18 14:25
 */
@RestController
@Api(tags = "服务中心管理")
@RequestMapping("/serviceCenter/level")
public class ServiceCenterLevelController {
    
    @Autowired
    private ServiceCenterLevelService serviceCenterLevelService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取服务中心等级列表",notes="获取服务中心等级列表",consumes="application/json",produces="application/json")
    public Object getServiceCenterLevelList() {
        return serviceCenterLevelService.getServiceCenterLevelList();
    }

    @RequestMapping(value="/{levelId}",method=RequestMethod.GET)
    @ApiOperation(value="获取某条服务中心等级信息",notes="获取某条服务中心等级详细信息",consumes="application/json",produces="application/json")
    public Object getServiceCenterDetail(@ApiParam(name="levelId",value="等级id",required=true) @PathVariable int levelId) {
        return serviceCenterLevelService.getServiceCenterLevelDetail(levelId);
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    @ApiOperation(value="添加服务中心等级",notes="添加服务中心等级信息",consumes="application/json",produces="application/json")
    public Object addSupplier(@RequestBody @Valid ServiceCenterLevelBean serviceCenterLevelBean){
        return serviceCenterLevelService.addServiceCenterLevel(serviceCenterLevelBean);
    }

    @RequestMapping(value="/{serviceCenterLevelId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改服务中心等级信息",notes="修改服务中心等级信息",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateMessage(@RequestBody @Valid ServiceCenterLevelBean serviceCenterLevelBean, @ApiParam(name="serviceCenterLevelId",value="服务中心等级Id")@PathVariable int serviceCenterLevelId){
        return serviceCenterLevelService.updateServiceCenterLevel(serviceCenterLevelBean, serviceCenterLevelId);
    }
}
