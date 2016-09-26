package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.PlatformCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/20 14:15
 */
public interface PlatformCouponDao extends JpaRepository<PlatformCoupon, Integer>, JpaSpecificationExecutor<PlatformCoupon> {
}
