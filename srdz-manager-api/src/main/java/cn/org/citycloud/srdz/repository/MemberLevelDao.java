package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.MemberLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 会员等级管理dao接口.
 *
 * @author demon
 * @Date 2016/4/20 9:12
 */
public interface MemberLevelDao extends JpaRepository<MemberLevel, Integer>, JpaSpecificationExecutor<MemberLevel> {
}
