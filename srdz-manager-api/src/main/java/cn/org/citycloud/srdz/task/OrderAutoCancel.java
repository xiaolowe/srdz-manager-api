package cn.org.citycloud.srdz.task;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.*;
import cn.org.citycloud.srdz.repository.*;
import cn.org.citycloud.srdz.utils.SiteMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * 取消超过一定时间的订单，默认三天.
 *
 * @author demon
 * @Date 2016/5/25 16:00
 */
public class OrderAutoCancel {

    private final static Logger logger = LoggerFactory.getLogger(OrderAutoCancel.class);

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private OrderGoodsDao orderGoodsDao;

    @Autowired
    private DiscountGoodDao discountGoodDao;

    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder() {
        logger.info("---------------取消超过三天未支付的订单定时任务开始执行");
        for (Integer obj : findOrders()) {
            int orderId = obj;
            Order order = orderDao.findOne(orderId);
            order.setOrderStatus(Constants.ORDER_ALREADY_CANCEL);
            orderDao.save(order);
            logger.info("取消超过三天未支付的订单，订单号：" + orderId);

            // 修改商品库存
            List<OrderGoods> orderGoodList = orderGoodsDao.findByOrderId(orderId);
            for (OrderGoods orderGood : orderGoodList) {
                Goods good = goodsDao.findOne(orderGood.getGoodsId());
                // 判断是否是特惠商品
                if (good.getDiscountFlg() == 1) {
                    DiscountGood discountGood = discountGoodDao.findOne(good.getGoodsId());
                    discountGood.setAlreadySale(discountGood.getAlreadySale() - orderGood.getGoodsNum());
                    discountGood.setSurplus(discountGood.getSurplus() + orderGood.getGoodsNum());
                    discountGood.setUpdateTime(new Date());
                    discountGoodDao.save(discountGood);
                } else {
                    good.setAlreadySale(good.getAlreadySale() - orderGood.getGoodsNum());
                    good.setSurplus(good.getSurplus() + orderGood.getGoodsNum());
                    good.setUpdateTime(new Date());
                    goodsDao.save(good);
                }
            }

            // 发送站内信
            Message message = SiteMsgUtils.getMessage(order.getMemberName(), order.getMemberId(), orderId, Constants.ORDER_CANCEL);
            message.setPlatform(Constants.MSG_USER);
            messageDao.save(message);
            // TODO 发送站内信给用户
            logger.info("发送站内信给用户，发送内容：'" + message.getMessageContent() + "', 用户id:" + message.getReceiverId());
        }
        logger.info("---------------取消超过三天未支付的订单定时任务执行结束");
    }

    /**
     * 查询超过三天未支付的订单
     *
     * @return
     */
    public List<Integer> findOrders() {
        String sql = "select order_id from orders where pay_time is null and TIMESTAMPDIFF(DAY, order_time, now()) > 3 and order_status = 10";
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }
}
