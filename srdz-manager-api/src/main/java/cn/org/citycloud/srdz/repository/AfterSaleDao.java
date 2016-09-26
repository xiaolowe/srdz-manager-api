package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.AfterSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/29 14:32
 */
public interface AfterSaleDao extends JpaRepository<AfterSale, Integer>, JpaSpecificationExecutor<AfterSale> {
}
