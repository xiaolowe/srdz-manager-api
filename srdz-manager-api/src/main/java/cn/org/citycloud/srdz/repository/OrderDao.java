package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * dao接口管理.
 *
 * @author demon
 * @Date 2016/4/25 10:31
 */
public interface OrderDao extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    /**
     * 根据订单状态，是否异常查询订单列表
     *
     * @param orderStatus
     * @return
     */
    public List<Order> findByOrderStatusAndException(int orderStatus, int exception);

    /**
     * 根据订单状态查询订单列表
     *
     * @param orderStatus
     * @return
     */
    public List<Order> findByOrderStatus(int orderStatus);

    /**
     * 根据订单异常值查询订单列表
     *
     * @param exception
     * @return
     */
    public List<Order> findByException(int exception);
}
