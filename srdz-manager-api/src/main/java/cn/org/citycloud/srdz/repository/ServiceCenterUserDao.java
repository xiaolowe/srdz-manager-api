package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.ServiceCenterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/11 17:20
 */
public interface ServiceCenterUserDao extends JpaRepository<ServiceCenterUser, Integer>, JpaSpecificationExecutor<ServiceCenterUser> {
    ServiceCenterUser findByServiceCenterId(int serviceId);
}
