package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.DiscountGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/7/12 16:06
 */
public interface DiscountGoodDao extends JpaRepository<DiscountGood, Integer>, JpaSpecificationExecutor<DiscountGood> {
}
