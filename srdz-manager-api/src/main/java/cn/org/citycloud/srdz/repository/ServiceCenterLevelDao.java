package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.ServiceCenterLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 服务中心等级dao接口.
 *
 * @author demon
 * @Date 2016/4/18 14:14
 */
public interface ServiceCenterLevelDao extends JpaRepository<ServiceCenterLevel, Integer>, JpaSpecificationExecutor<ServiceCenterLevel> {
}
