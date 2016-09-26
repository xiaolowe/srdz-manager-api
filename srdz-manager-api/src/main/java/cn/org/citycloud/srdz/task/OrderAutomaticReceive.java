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
 * 发货后10日后，买家无操作，系统自动收货.
 *
 * @author demon
 * @Date 2016/5/5 9:15
 */
public class OrderAutomaticReceive {

    private final static Logger logger = LoggerFactory.getLogger(OrderAutomaticReceive.class);

    @Autowired
    private OrderDao orderDao;

    /**
     * 自动收货
     */
    public void automaticReceive() {
        logger.info("---------------自动收货定时任务开始执行");
        Iterator<Order> iterator = getWaitReceiveList().iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            int daysDiff = DateUtils.dayDiffFromToday(order.getSendGoodsTime());
            // 发货10日后，自动收货
            if (Math.abs(daysDiff) > 10) {
                order.setOrderStatus(Constants.ORDER_AWAIT_EVALUATE);
                order.setUpdateTime(new Date());
                orderDao.save(order);
                logger.info("修改订单状态为自动收货，订单号:{}", order.getOrderStatus());
            }
        }
        logger.info("---------------自动收货定时任务执行结束");
    }

    /**
     * 获取订单状态为待收货的订单列表
     *
     * @return
     */
    private List<Order> getWaitReceiveList() {
        return orderDao.findByOrderStatusAndException(Constants.ORDER_AWAIT_RECEIVE, Constants.ORDER_EXCEPTION_NORMAL);
    }

}
