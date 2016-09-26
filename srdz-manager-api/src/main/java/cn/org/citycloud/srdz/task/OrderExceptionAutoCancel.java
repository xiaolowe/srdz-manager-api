package cn.org.citycloud.srdz.task;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.Order;
import cn.org.citycloud.srdz.repository.OrderDao;
import cn.org.citycloud.srdz.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 订单异常自动取消，条件：买家发起，10日.
 *
 * @author demon
 * @Date 2016/5/5 14:17
 */
public class OrderExceptionAutoCancel {

    private final static Logger logger = LoggerFactory.getLogger(OrderExceptionAutoCancel.class);

    @Autowired
    private OrderDao orderDao;

    /**
     * 自动取消异常
     */
    public void cancelException() {
        logger.info("---------------自动取消订单异常定时任务开始执行");
        Iterator<Order> iterator = getExceptionOrderList().iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (Math.abs(DateUtils.dayDiffFromToday(order.getExceptionTime())) > 10) {
                order.setUpdateTime(new Date());
                order.setException(Constants.ORDER_EXCEPTION_NORMAL);
                order.setOrderStatus(Constants.ORDER_ALREADY_SEND);
                orderDao.save(order);
                logger.info("自动取消订单异常，订单编号:{}", order.getOrderId());
            }
        }
        logger.info("---------------自动取消订单异常定时任务执行结束");
    }

    /**
     * 获取满足条件的异常订单列表
     *
     * @return
     */
    private List<Order> getExceptionOrderList() {
        return orderDao.findByException(Constants.ORDER_EXCEPTION_BUYER);
    }
}
