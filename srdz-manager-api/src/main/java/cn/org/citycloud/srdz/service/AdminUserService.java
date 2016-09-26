package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.AccountBean;
import cn.org.citycloud.srdz.bean.AdminUserSearch;
import cn.org.citycloud.srdz.bean.UserLoginBean;
import cn.org.citycloud.srdz.bean.UserPwdBean;
import cn.org.citycloud.srdz.entity.AdminUser;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.concurrent.TimeoutException;

/**
 * 后台管理用户service接口.
 *
 * @author demon
 * @Date 2016/4/22 16:08
 */
public interface AdminUserService {
    /**
     * 用户登录
     *
     * @param userLogin
     * @return
     */
    public Object login(UserLoginBean userLogin) throws BusinessErrorException, InterruptedException, MemcachedException, TimeoutException;

    /**
     * 注销登录
     *
     * @param token
     */
    public void logout(String token) throws InterruptedException, MemcachedException, TimeoutException;

    /**
     * 修改密码
     *
     * @param userPwdBean
     * @param userId
     */
    public void changePwd(UserPwdBean userPwdBean, int userId) throws BusinessErrorException;

    /**
     * 获取用户详细信息（账号中心）
     *
     * @param userId
     * @return
     */
    public Object getUserInfo(int userId);

    /**
     * 查询用户列表
     *
     * @param adminUserSearch
     * @return
     */
    public Object getUserList(AdminUserSearch adminUserSearch);

    /**
     * 获取用户详情（账号管理）
     *
     * @param userId
     * @return
     */
    public Object getUserDetail(int userId);

    /**
     * 添加用户
     *
     * @param accountBean
     * @return
     */
    public Object addUser(AccountBean accountBean) throws BusinessErrorException;

    /**
     * 修改用户
     *
     * @param accountBean
     * @param userId
     * @return
     */
    public Object updateUser(AccountBean accountBean, int userId) throws BusinessErrorException;

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    public Object resetPwd(int userId);
}
