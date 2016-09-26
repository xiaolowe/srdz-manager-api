package cn.org.citycloud.srdz.controller;

import cn.org.citycloud.srdz.core.BaseController;
import cn.org.citycloud.srdz.service.AdminUserService;
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
 * @Date 2016/4/25 17:44
 */
@RestController
@Api(tags = "帐号中心")
@RequestMapping("/accountCenter")
public class AccountCenterController extends BaseController {

    @Autowired
    private AdminUserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value="获取用户信息",notes="获取用户信息",consumes="application/json",produces="application/json")
    public Object getUserInfo() {
        return userService.getUserInfo(getUserId());
    }

}
