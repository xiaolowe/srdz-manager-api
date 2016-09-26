package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.NoticeBean;
import cn.org.citycloud.srdz.bean.page.Page;
import cn.org.citycloud.srdz.service.NoticeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 17:26
 */
@RestController
@Api(tags = "公告管理")
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取公告列表",notes="获取公告列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query")
    })
    public Object getNoticeList(@ApiIgnore Page page) {
        return noticeService.getNoticeList(page);
    }

    @RequestMapping(value = "/{noticeId}", method = RequestMethod.GET)
    @ApiOperation(value="获取公告详情",notes="获取公告详情",consumes="application/json",produces="application/json")
    public Object getNoticeDetail(@ApiParam(name="noticeId",value="公告Id",required=true) @PathVariable int noticeId) {
        return noticeService.getNoticeDetail(noticeId);
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    @ApiOperation(value="添加公告",notes="添加公告",consumes="application/json",produces="application/json")
    public Object addNotice(@RequestBody @Valid NoticeBean noticeBean) {
        return noticeService.addNotice(noticeBean);
    }

    @RequestMapping(value="/{noticeId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改公告",notes="修改公告",consumes="application/json",produces="application/json")
    public Object updateNotice(@RequestBody @Valid NoticeBean noticeBean, @ApiParam(name="noticeId",value="公告Id",required=true) @PathVariable int noticeId) {
        return noticeService.updateNotice(noticeBean, noticeId);
    }
}
