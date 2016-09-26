package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 后台管理用户dao接口.
 *
 * @author demon
 * @Date 2016/4/22 16:04
 */
public interface AdminUserDao extends JpaRepository<AdminUser, Integer>, JpaSpecificationExecutor<AdminUser> {
    AdminUser findByUserNameAndUserPwd(String userName, String userPwd);

    List<AdminUser> findByUserName(String userName);
}
