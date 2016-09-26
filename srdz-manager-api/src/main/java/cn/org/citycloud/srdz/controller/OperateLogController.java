package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.OperateLogSearch;
import cn.org.citycloud.srdz.service.OperateLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 日志管理.
 *
 * @author demon
 * @Date 2016/5/14 17:08
 */
@RestController
@Api(tags = "日志管理")
@RequestMapping("/log")
public class OperateLogController {

    @Autowired
    private OperateLogService logService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取日志列表",notes="获取日志列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="busId",value="单据Id",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="busCode",value="业务类型编码,1供应商 2供应商等级 3服务中心 4服务中心等级 5分销 6会员等级 7财务管理",required=true,dataType="int",paramType="query")
    })
    public Object getLogList(@ApiIgnore OperateLogSearch search) {
        return logService.getLogList(search);
    }
}
