package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.AccountBean;
import cn.org.citycloud.srdz.bean.AdminUserSearch;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.service.AdminUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 18:25
 */
@RestController
@Api(tags = "账号管理")
@RequestMapping("/account")
public class AccountsController {

    @Autowired
    private AdminUserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取用户列表",notes="获取用户列表",consumes="application/json",produces="application/json")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name="pageNo",value="页数",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="pageSize",value="每页大小",required=false,dataType="int",paramType="query"),
            @ApiImplicitParam(name="roleCode",value="用户角色",required=false,dataType="string",paramType="query"),
            @ApiImplicitParam(name="userTruename",value="账号使用人姓名",required=false,dataType="string",paramType="query")
    })
    public Object getUserList(@ApiIgnore  AdminUserSearch adminUserSearch) {
        return userService.getUserList(adminUserSearch);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ApiOperation(value="获取用户详情",notes="获取用户详情",consumes="application/json",produces="application/json")
    public Object getUserDetail(@ApiParam(name="userId",value="用户Id",required=true) @PathVariable int userId) {
        return userService.getUserDetail(userId);
    }

    @RequestMapping(value="/",method=RequestMethod.POST)
    @ApiOperation(value="添加用户",notes="添加用户",consumes="application/json",produces="application/json")
    public Object addUser(@RequestBody @Valid AccountBean accountBean) throws BusinessErrorException {
        return userService.addUser(accountBean);
    }

    @RequestMapping(value="/{userId}",method=RequestMethod.PUT)
    @ApiOperation(value="修改用户",notes="修改用户",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object updateUser(@RequestBody @Valid AccountBean accountBean, @ApiParam(name="userId",value="用户Id",required=true) @PathVariable int userId) throws BusinessErrorException {
        return userService.updateUser(accountBean, userId);
    }

    @RequestMapping(value="/{userId}/resetPwd",method=RequestMethod.PUT)
    @ApiOperation(value="重置密码",notes="重置密码",consumes="application/json",produces="application/json",httpMethod="PUT")
    public Object resetPwd(@ApiParam(name="userId",value="用户Id",required=true) @PathVariable int userId) {
        return userService.resetPwd(userId);
    }
}
