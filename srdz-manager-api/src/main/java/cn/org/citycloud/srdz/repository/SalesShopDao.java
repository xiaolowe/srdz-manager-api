package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.SalesShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 分销商店铺dao接口.
 *
 * @author demon
 * @Date 2016/4/19 16:37
 */
public interface SalesShopDao extends JpaRepository<SalesShop, Integer>, JpaSpecificationExecutor<SalesShop> {
}
