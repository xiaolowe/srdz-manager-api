package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 供应商dao接口
 *
 * @author demon
 */
public interface SupplierDao extends JpaRepository<Supplier, Integer>, JpaSpecificationExecutor<Supplier> {
}
