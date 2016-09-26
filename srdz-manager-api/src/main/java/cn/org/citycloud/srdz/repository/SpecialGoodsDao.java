package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.DiscountGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 特惠商品dao接口.
 *
 * @author demon
 * @Date 2016/4/20 16:24
 */
public interface SpecialGoodsDao extends JpaRepository<DiscountGood, Integer>, JpaSpecificationExecutor<DiscountGood> {
}
