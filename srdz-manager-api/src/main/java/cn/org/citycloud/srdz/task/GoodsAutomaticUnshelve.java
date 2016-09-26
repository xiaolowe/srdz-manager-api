package cn.org.citycloud.srdz.task;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.DiscountGood;
import cn.org.citycloud.srdz.entity.Goods;
import cn.org.citycloud.srdz.repository.GoodsDao;
import cn.org.citycloud.srdz.repository.SpecialGoodsDao;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 商品自动下架:库存为0，到达自动下架时间.
 *
 * @author demon
 * @Date 2016/5/5 10:44
 */
public class GoodsAutomaticUnshelve {

    private final static Logger logger = LoggerFactory.getLogger(OrderAutomaticReceive.class);

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private SpecialGoodsDao specialGoodsDao;

    /**
     * 自动下架
     */
    public void autoUnshelve() {
        logger.info("---------------商品自动下架定时任务开始执行");
        Iterator<Goods> iterator = getNeedShelveGoodsList().iterator();
        while (iterator.hasNext()) {
            Goods goods = iterator.next();
            goods.setGoodsStatus(Constants.GOODS_UNSHELVE);
            goods.setUpdateTime(new Date());
            goodsDao.save(goods);
            logger.info("商品自动下架，商品编号:{}", goods.getGoodsId());
        }
        logger.info("---------------商品自动下架定时任务执行结束");
    }

    /**
     * 特惠商品自动下架
     */
    public void autoUnshelveSpecial() {
        logger.info("---------------特惠商品自动下架定时任务开始执行");
        Iterator<DiscountGood> iterator = getDiscountList().iterator();
        while (iterator.hasNext()) {
            DiscountGood discountGood = iterator.next();
            Goods goods = discountGood.getGoods();
            goods.setUpdateTime(new Date());
            goods.setGoodsStatus(Constants.GOODS_UNSHELVE);
            goodsDao.save(goods);
            logger.info("特惠商品自动下架，商品编号:{}", goods.getGoodsId());
        }
        logger.info("---------------特惠商品自动下架定时任务执行结束");
    }

    /**
     * 获取需要下架的商品列表
     *
     * @return
     */
    private List<Goods> getNeedShelveGoodsList() {
        Specification<Goods> spec = new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 商品自动下架查询条件为：库存为0，到达自动下架时间
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("goodsStatus"), Constants.GOODS_SHELVE));
                Predicate p1 = cb.equal(root.get("surplus"), 0);
                Predicate p2 = cb.lessThanOrEqualTo(root.get("autoDownTime"), new Date());
                Predicate p3 = cb.or(p1, p2);
                predicate = cb.and(predicate, p3);
                return query.where(predicate).getRestriction();
            }
        };

        return goodsDao.findAll(spec);
    }

    /**
     * 获取需要下架的特惠商品列表
     *
     * @return
     */
    private List<DiscountGood> getDiscountList() {
        Specification<DiscountGood> spec = new Specification<DiscountGood>() {
            @Override
            public Predicate toPredicate(Root<DiscountGood> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 商品自动下架查询条件为：库存为0，到达自动下架时间
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("goods").get("goodsStatus"), Constants.GOODS_SHELVE));
                Predicate p1 = cb.equal(root.get("surplus"), 0);
                Predicate p2 = cb.lessThanOrEqualTo(root.get("autoDownTime"), new Date());
                Predicate p3 = cb.or(p1, p2);
                predicate = cb.and(predicate, p3);
                return query.where(predicate).getRestriction();
            }
        };
        return specialGoodsDao.findAll(spec);
    }
}
