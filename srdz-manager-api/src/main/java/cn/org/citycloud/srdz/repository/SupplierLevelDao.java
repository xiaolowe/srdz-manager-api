package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.SupplierLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 供应商等级dao接口
 *
 * @author demon
 */
public interface SupplierLevelDao extends JpaRepository<SupplierLevel, Integer>, JpaSpecificationExecutor<SupplierLevel> {
}
