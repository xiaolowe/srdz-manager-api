package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 服务中心dao接口.
 *
 * @author demon
 * @Date 2016/4/18 11:35
 */
public interface ServiceCenterDao extends JpaRepository<ServiceCenter, Integer>, JpaSpecificationExecutor<ServiceCenter> {
    /**
     * 根据联系电话查询列表
     *
     * @param contactPhone
     * @return
     */
    List<ServiceCenter> findByContactPhone(String contactPhone);
}
