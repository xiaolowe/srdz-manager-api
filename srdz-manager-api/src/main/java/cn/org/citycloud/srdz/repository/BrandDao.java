package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 15:27
 */
public interface BrandDao extends JpaRepository<Brand, Integer>, JpaSpecificationExecutor<Brand> {
}
