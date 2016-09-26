package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.MessageSearch;
import cn.org.citycloud.srdz.service.MessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 消息管理controller.
 *
 * @author demon
 * @Date 2016/4/22 10:17
 */
@RestController
@Api(tags = "消息管理")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取消息列表",notes="获取消息列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="stime",value="开始时间 yyyy-MM-dd",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="etime",value="结束时间 yyyy-MM-dd",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="keyWord",value="关键词",required=false,dataType="string",paramType="query")
    })
    public Object getMessageList(@ApiIgnore MessageSearch messageSearch) {
        return messageService.getMessageList(messageSearch);
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.GET)
    @ApiOperation(value="获取消息详情",notes="获取消息详情",consumes="application/json",produces="application/json")
    public Object getMessageDetail(@ApiParam(name="messageId",value="活动Id",required=true) @PathVariable int messageId) {
        return messageService.getMessageDetail(messageId);
    }

    @RequestMapping(value = "/unread", method = RequestMethod.GET)
    @ApiOperation(value="获取当前用户的未读消息列表",notes="获取当前用户的未读消息列表",consumes="application/json",produces="application/json")
    public Object getUnreadList() {
        return messageService.getUnreadList();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value="获取当前用户的消息列表",notes="获取当前用户的消息列表",consumes="application/json",produces="application/json")
    public Object getAllMsg() {
        return messageService.getAllMsg();
    }
}
