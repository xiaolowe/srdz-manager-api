package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.MemberLevelBean;
import cn.org.citycloud.srdz.bean.MemberSearch;
import cn.org.citycloud.srdz.service.MemberService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;

/**
 * 会员管理controller.
 *
 * @author demon
 * @Date 2016/4/20 9:50
 */
@RestController
@Api(tags = "会员管理")
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取会员列表",notes="获取会员列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="memberStatus",value="会员状态 1为开启 0为关闭",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="memberName",value="会员名称",required=false,dataType="string",paramType="query")
    })
    public Object getMemberList(@ApiIgnore MemberSearch memberSearch) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return memberService.getMemberList(memberSearch);
    }

    @RequestMapping(value="/{memberId}",method=RequestMethod.GET)
    @ApiOperation(value="获取会员详情",notes="获取会员详情",consumes="application/json",produces="application/json",httpMethod="GET")
    public Object getMemberDetail(@ApiParam(name="memberId",value="会员Id")@PathVariable int memberId) {
        return memberService.getMemberDetail(memberId);
    }

    @RequestMapping(value="/{memberId}/enable",method=RequestMethod.PUT)
    @ApiOperation(value="启用会员",notes="启用会员",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object enabledMember(@ApiParam(name="memberId",value="会员Id")@PathVariable int memberId) {
        return memberService.enableMember(memberId);
    }

    @RequestMapping(value="/{memberId}/disable",method=RequestMethod.PUT)
    @ApiOperation(value="禁用会员",notes="禁用会员",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object disabledMember(@ApiParam(name="memberId",value="会员Id")@PathVariable int memberId) {
        return memberService.disableMember(memberId);
    }

    @RequestMapping(value = "/level", method = RequestMethod.GET)
    @ApiOperation(value="获取会员等级列表",notes="获取会员等级列表",consumes="application/json",produces="application/json")
    public Object getMemberLevelList() {
        return memberService.getMemberLevelList();
    }

    @RequestMapping(value="/level",method=RequestMethod.POST)
    @ApiOperation(value="添加会员等级",notes="添加会员等级信息",consumes="application/json",produces="application/json")
    public Object addMemberLevel(@RequestBody @Valid MemberLevelBean memberLevelBean) {
        return memberService.addMemberLevel(memberLevelBean);
    }

    @RequestMapping(value="/{memberLevelId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改会员等级信息",notes="修改会员等级信息",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateMemberLevel(@RequestBody @Valid MemberLevelBean memberLevelBean, @ApiParam(name="memberLevelId",value="会员等级Id")@PathVariable int memberLevelId) {
        return memberService.updateMemberLevel(memberLevelBean, memberLevelId);
    }
}
