package cn.org.citycloud.srdz.task;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.EvaluateGood;
import cn.org.citycloud.srdz.entity.Order;
import cn.org.citycloud.srdz.repository.EvaluateGoodsDao;
import cn.org.citycloud.srdz.repository.OrderDao;
import cn.org.citycloud.srdz.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 自动评论，条件：5日后收货后未评价.
 *
 * @author demon
 * @Date 2016/5/5 17:15
 */
public class AutoEvaluate {

    private final static Logger logger = LoggerFactory.getLogger(AutoEvaluate.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private EvaluateGoodsDao evaluateGoodsDao;

    /**
     * 自动好评
     */
    public void autoEvaluate() {
        logger.info("---------------自动好评定时任务开始执行");
        Iterator<Order> iterator = getOrderList().iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            // 判断未评价的订单的收货时间超过5天
            if (Math.abs(DateUtils.dayDiffFromToday(order.getGetGoodsTime())) > 5) {
                EvaluateGood evaluateGood = new EvaluateGood();
                evaluateGood.setOrderId(order.getOrderId());
                evaluateGood.setGevalAddtime(new Date());
                evaluateGood.setGevalScores(10);
                evaluateGoodsDao.save(evaluateGood);
                order.setOrderStatus(Constants.ORDER_DONE);
                orderDao.save(order);
                logger.info("自动好评，订单编号:{}, 生成的评价编号:{}", order.getOrderId(), evaluateGood.getGevalId());
            }
        }
        logger.info("---------------自动好评定时任务执行结束");
    }

    /**
     * 查询待评价列表
     *
     * @return
     */
    private List<Order> getOrderList() {
        return orderDao.findByOrderStatus(Constants.ORDER_AWAIT_EVALUATE);
    }
}
