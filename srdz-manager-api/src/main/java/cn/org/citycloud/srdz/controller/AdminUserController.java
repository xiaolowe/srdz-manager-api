package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.bean.UserLoginBean;
import cn.org.citycloud.srdz.bean.UserPwdBean;
import cn.org.citycloud.srdz.core.BaseController;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.service.AdminUserService;
import io.swagger.annotations.*;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.TimeoutException;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 9:12
 */
@RestController
@Api(tags = "用户登录")
@RequestMapping("/user")
public class AdminUserController extends BaseController {

    @Autowired
    private AdminUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Transactional
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public Object login(@ApiParam(value = "用户登录信息", required = true) @Valid @RequestBody UserLoginBean userLoginBean) throws InterruptedException, TimeoutException, BusinessErrorException, MemcachedException {
        return userService.login(userLoginBean);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
    public void logout() throws InterruptedException, MemcachedException, TimeoutException {
        userService.logout(getToken());
    }

    @RequestMapping(value = "/modifyUserPwd", method = RequestMethod.POST)
    @ApiOperation(value = "修改登录密码", notes = "修改登录密码")
    @ApiImplicitParams(value={@ApiImplicitParam(name="token",value="token",required=false,dataType="string",paramType="header")})
    public void changePwd(@ApiParam(value = "登录密码修改信息", required = true) @Valid @RequestBody UserPwdBean userPwdBean) throws BusinessErrorException {
        userService.changePwd(userPwdBean, getUserId());
    }
}
