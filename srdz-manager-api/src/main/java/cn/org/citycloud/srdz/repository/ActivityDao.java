package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 营销活动dao接口.
 *
 * @author demon
 * @Date 2016/4/20 14:13
 */
public interface ActivityDao extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {
}
