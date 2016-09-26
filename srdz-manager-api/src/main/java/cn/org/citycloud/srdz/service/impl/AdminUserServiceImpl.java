package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.*;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.constants.ErrorCodes;
import cn.org.citycloud.srdz.entity.AdminUser;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.repository.AdminUserDao;
import cn.org.citycloud.srdz.service.AdminUserService;
import cn.org.citycloud.srdz.utils.SmsUtil;
import com.auth0.jwt.JWTSigner;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * 后台管理用户service实现.
 *
 * @author demon
 * @Date 2016/4/22 16:26
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;

    @Autowired
    private MemcachedClient cacheClient;

    @Override
    public Object login(UserLoginBean userLogin) throws BusinessErrorException, InterruptedException, MemcachedException, TimeoutException {
        AdminUser user = adminUserDao.findByUserNameAndUserPwd(userLogin.getUserName(), userLogin.getUserPwd());
        if (user == null) {
            throw new BusinessErrorException(ErrorCodes.NON_EXIST_MEMBER, "账号或者密码不正确");
        }
        if (Constants.MEMBER_STATE_CLOSED == user.getUserStatus()) {
            throw new BusinessErrorException(ErrorCodes.WRONG_MEMBER, "此会员已经被禁用！");
        }
        String token = generateToken(user.getAdminUserId(), Constants.TOKEN_SECRET);
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUserId(user.getAdminUserId());
        userToken.setUserName(user.getUserName());
        userToken.setExpiresIn(Constants.TOKEN_EXPIRES_IN);
        userToken.setCreateTs(System.currentTimeMillis());
        userToken.setRoleCode(user.getRoleCode());
        cacheClient.set(token, (int) Constants.TOKEN_EXPIRES_IN, userToken);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        resultMap.put("userId", user.getAdminUserId());
        resultMap.put("userName", user.getUserName());
        resultMap.put("roleCode", user.getRoleCode());
        return resultMap;
    }

    @Override
    public void logout(String token) throws InterruptedException, MemcachedException, TimeoutException {
        cacheClient.delete(token);
    }

    @Override
    public void changePwd(UserPwdBean userPwdBean, int userId) throws BusinessErrorException {
        AdminUser userInfo = adminUserDao.findOne(userId);
        if (userInfo == null) {
            throw new BusinessErrorException(ErrorCodes.NON_EXIST_MEMBER, "用户账号不存在");
        }
        if (!userPwdBean.getUserPwd().equals(userPwdBean.getConfirmPwd())) {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "两次输入的密码不一致，请重新输入");
        }

        if (!userPwdBean.getOldUserPwd().equals(userInfo.getUserPwd())) {
            throw new BusinessErrorException(ErrorCodes.PARAM_ERROR, "密码输入错误，请重新输入");
        }
        userInfo.setUserPwd(userPwdBean.getUserPwd());
        userInfo.setUpdateTime(new Date());
        adminUserDao.save(userInfo);
    }

    /**
     * 生成token值
     *
     * @param userId
     * @param secret
     * @return
     */
    private String generateToken(int userId, String secret) {
        JWTSigner jwtSigner = new JWTSigner(secret);
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", userId);
        claims.put("crtime", System.currentTimeMillis());
        String token = jwtSigner.sign(claims);
        return token;
    }

    @Override
    public Object getUserInfo(int userId) {
        AdminUserBean adminUserBean = new AdminUserBean();
        BeanUtils.copyProperties(adminUserDao.findOne(userId), adminUserBean);
        return adminUserBean;
    }

    @Override
    public Object getUserList(AdminUserSearch adminUserSearch) {
        int page = adminUserSearch.getPageNo();
        int pageSize = adminUserSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page - 1, pageSize, sort);
        Specification<AdminUser> spec = new Specification<AdminUser>() {

            @Override
            public Predicate toPredicate(Root<AdminUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (adminUserSearch.getRoleCode() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("roleCode"), adminUserSearch.getRoleCode()));
                }
                if (!StringUtils.isEmpty(adminUserSearch.getUserTruename())) {
                    predicate = cb.and(predicate, cb.like(root.get("userTruename"), "%" + adminUserSearch.getUserTruename() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return adminUserDao.findAll(spec, pageable);
    }

    @Override
    public Object getUserDetail(int userId) {
        return adminUserDao.findOne(userId);
    }

    @Override
    public Object addUser(AccountBean accountBean) throws BusinessErrorException {
        checkUnique(accountBean.getUserName(), false, 0);
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(accountBean, adminUser);
        adminUser.setCreateTime(new Date());
        adminUser.setUpdateTime(new Date());
        // TODO 设置默认密码
        adminUser.setUserPwd("888888");
        adminUserDao.save(adminUser);
        return adminUser;
    }

    @Override
    public Object updateUser(AccountBean accountBean, int userId) throws BusinessErrorException {
        checkUnique(accountBean.getUserName(), true, userId);
        AdminUser adminUser = adminUserDao.findOne(userId);
        BeanUtils.copyProperties(accountBean, adminUser);
        adminUser.setUpdateTime(new Date());
        adminUserDao.save(adminUser);
        return adminUser;
    }

    @Override
    public Object resetPwd(int userId) {
        String pwd = SmsUtil.getRandomStr(false, 6);
        AdminUser adminUser = adminUserDao.findOne(userId);
        adminUser.setUserPwd(pwd);
        adminUser.setUpdateTime(new Date());
        adminUserDao.save(adminUser);
        // 发送短信
        Map<String, String> data = new HashMap<>();
        data.put("userName", adminUser.getUserName());
        data.put("pwd", pwd);
        SmsUtil.sendSms(adminUser.getPhone(), SmsUtil.getMessageTpl(Constants.SMS_RESET_PWD, data));
        return adminUser;
    }

    /**
     * 判断是否唯一(添加时判断)
     *
     * @param userName
     * @return
     */
    private void checkUnique(String userName, boolean isUpdate, int userId) throws BusinessErrorException {
        List<AdminUser> userList = new ArrayList<>();
        if (!isUpdate) {
            userList = adminUserDao.findByUserName(userName);
        } else {
            Specification<AdminUser> spec = (root, query, cb) -> {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("userName"), userName));
                predicate = cb.and(predicate, cb.notEqual(root.get("userName"), adminUserDao.findOne(userId).getUserName()));
                return query.where(predicate).getRestriction();
            };
            userList = adminUserDao.findAll(spec);
        }
        if (userList.size() > 0) {
            throw new BusinessErrorException("017", "用户名重复");
        }
    }
}
