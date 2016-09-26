package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.SupplierOther;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/3 16:37
 */
public interface SupplierOtherDao extends JpaRepository<SupplierOther, Integer>, JpaSpecificationExecutor<SupplierOther> {
    List<SupplierOther> findByStatus(int status);
}
