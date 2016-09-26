package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.OrderGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/7/12 16:04
 */
public interface OrderGoodsDao extends JpaRepository<OrderGoods, Integer>, JpaSpecificationExecutor<OrderGoods> {
    List<OrderGoods> findByOrderId(int orderId);
}
