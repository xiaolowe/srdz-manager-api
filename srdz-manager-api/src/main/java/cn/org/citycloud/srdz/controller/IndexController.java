package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.core.BaseController;
import cn.org.citycloud.srdz.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/26 15:12
 */
@RestController
@Api(tags = "首页")
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取首页统计数据",notes="获取首页统计数据",consumes="application/json",produces="application/json")
    public Object getStatisticsData() {
        return indexService.getStatisticsData(getUserToken().getRoleCode());
    }

    @RequestMapping(value = "/chartData", method = RequestMethod.GET)
    @ApiOperation(value="获取首页图表数据",notes="获取首页图表数据",consumes="application/json",produces="application/json")
    public Object getChartData() {
        return indexService.getChartsData();
    }
}
