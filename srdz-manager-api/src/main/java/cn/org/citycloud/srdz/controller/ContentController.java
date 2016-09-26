package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.ContentBean;
import cn.org.citycloud.srdz.bean.ContentSearch;
import cn.org.citycloud.srdz.bean.SupplierBean;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.service.ContentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 内容管理controller.
 *
 * @author demon
 * @Date 2016/4/19 14:15
 */
@RestController
@Api(tags = "内容管理")
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取内容管理列表",notes="获取内容管理列表",consumes="application/json",produces="application/json")
    public Object getContentList() {
        return contentService.getContentList();
    }

    @RequestMapping(value = "/{contentId}", method = RequestMethod.GET)
    @ApiOperation(value="获取某条内容详情",notes="获取某条内容详细信息",consumes="application/json",produces="application/json")
    public Object getContentDetail(@ApiParam(name="contentId",value="内容ID",required=true) @PathVariable int contentId) {
        return contentService.getContentDetail(contentId);
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    @ApiOperation(value="添加内容",notes="添加内容信息",consumes="application/json",produces="application/json")
    public Object addSupplier(@RequestBody @Valid ContentBean contentBean) throws BusinessErrorException {
        return contentService.addContent(contentBean);
    }

    @RequestMapping(value="/{contentId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改内容信息",notes="修改内容信息",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateMessage(@RequestBody @Valid ContentBean contentBean,@ApiParam(name="contentId",value="内容Id")@PathVariable int contentId){
        return contentService.updateContent(contentBean, contentId);
    }
}
