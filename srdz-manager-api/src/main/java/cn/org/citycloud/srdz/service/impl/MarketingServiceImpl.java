package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.ActivityBean;
import cn.org.citycloud.srdz.bean.ActivitySearch;
import cn.org.citycloud.srdz.bean.GoodsSearch;
import cn.org.citycloud.srdz.bean.PlatformCouponBean;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.Activity;
import cn.org.citycloud.srdz.entity.DiscountGood;
import cn.org.citycloud.srdz.entity.Goods;
import cn.org.citycloud.srdz.entity.PlatformCoupon;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.repository.ActivityDao;
import cn.org.citycloud.srdz.repository.GoodsDao;
import cn.org.citycloud.srdz.repository.PlatformCouponDao;
import cn.org.citycloud.srdz.repository.SpecialGoodsDao;
import cn.org.citycloud.srdz.service.MarketingService;
import cn.org.citycloud.srdz.utils.BeanUtil;
import cn.org.citycloud.srdz.utils.DateUtils;
import cn.org.citycloud.srdz.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/20 14:55
 */
@Service
public class MarketingServiceImpl implements MarketingService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private PlatformCouponDao couponDao;

    @Autowired
    private SpecialGoodsDao specialGoodsDao;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public Object getActivityList(ActivitySearch activitySearch) {
        int page = activitySearch.getPageNo();
        int size = activitySearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<Activity> spec = new Specification<Activity>() {
            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (activitySearch.getStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("status"), activitySearch.getStatus()));
                }
                if(!StringUtils.isEmpty(activitySearch.getActivityName())) {
                    predicate = cb.and(predicate, cb.like(root.get("activityName"), "%" + activitySearch.getActivityName() + "%"));
                }
                if (!StringUtils.isEmpty(activitySearch.getStart())) {
                    String start = activitySearch.getStart() + " 00:00:00";
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("startTime"), DateUtils.parse(start, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(activitySearch.getEnd())) {
                    String end = activitySearch.getEnd() + " 23:59:59";
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("endTime"), DateUtils.parse(end, DateUtils.DATE_PATTERN_1)));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return activityDao.findAll(spec, pageable);
    }

    @Override
    public Object getActivityDetail(int activityId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Activity activity = activityDao.findOne(activityId);
        Map<String, Object> resultMap = BeanUtil.beanToMap(activity, Activity.class);
        // 活动添加属性：是否可编辑
        List<PlatformCoupon> sortedList = new ArrayList<>(activity.getPlatformCouponSet());
        Collections.sort(sortedList);
        resultMap.put("platformCouponSet", sortedList);
        resultMap.put("editable", getEditable(activity.getStartTime()));
        return resultMap;
    }

    @Override
    public Object addActivity(ActivityBean activityBean) throws BusinessErrorException {
        checkDatePeriod(activityBean.getStartTime(), activityBean.getEndTime());
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityBean, activity);
        activity.setCreateDate(new Date());
        activity.setUpdateDate(new Date());
        activityDao.save(activity);
        return activity;
    }

    @Override
    public Object updateActivity(ActivityBean activityBean, int activityId) throws BusinessErrorException {
        checkDatePeriod(activityBean.getStartTime(), activityBean.getEndTime());
        Activity activity = activityDao.findOne(activityId);
        if (getEditable(activity.getStartTime()) == 0) {
            BeanUtils.copyProperties(activityBean, activity);
            activity.setUpdateDate(new Date());
        } else {
            activity.setActivityDesc(activityBean.getActivityDesc());
            activity.setBannerImage(activityBean.getBannerImage());
            activity.setUpdateDate(new Date());
        }
        activityDao.save(activity);
        return activity;
    }

    @Override
    public Object getPlatformCouponDetail(int couponId) {
        return couponDao.findOne(couponId);
    }

    @Override
    public Object addPlatformCoupon(PlatformCouponBean platformCouponBean) throws BusinessErrorException {
        checkDatePeriod(platformCouponBean.getEffectiveTime(), platformCouponBean.getExpirationTime());
        if (platformCouponBean.getLimitGet() < platformCouponBean.getEverydayGet()) {
            throw new BusinessErrorException("020", "每日限领不能大于每人限领");
        }
        PlatformCoupon platformCoupon = new PlatformCoupon();
        BeanUtils.copyProperties(platformCouponBean, platformCoupon);
        platformCoupon.setCreateDate(new Date());
        platformCoupon.setUpdateDate(new Date());
        platformCoupon.setCouponStatus(10);
        couponDao.save(platformCoupon);
        return platformCoupon;
    }

    @Override
    public Object updatePlatformCoupon(PlatformCouponBean platformCouponBean, int couponId) throws BusinessErrorException {
        checkDatePeriod(platformCouponBean.getEffectiveTime(), platformCouponBean.getExpirationTime());
        PlatformCoupon platformCoupon = couponDao.findOne(couponId);
        Activity activity = activityDao.findOne(platformCouponBean.getActivityId());
        if (getEditable(activity.getStartTime()) == 0) {
            BeanUtils.copyProperties(platformCouponBean, platformCoupon);
            platformCoupon.setUpdateDate(new Date());
        } else {
            stopPlatformCoupon(platformCoupon.getCouponId());
            platformCoupon.setUpdateDate(new Date());
        }
        couponDao.save(platformCoupon);
        return platformCoupon;
    }

    @Override
    public Object stopPlatformCoupon(int couponId) {
        PlatformCoupon platformCoupon = couponDao.findOne(couponId);
        platformCoupon.setCouponStatus(Constants.COUPON_STOP);
        couponDao.save(platformCoupon);
        return platformCoupon;
    }

    @Override
    public Object getSpecialGoodsList(GoodsSearch goodsSearch) {
        int page = goodsSearch.getPageNo();
        int size = goodsSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<DiscountGood> spec = new Specification<DiscountGood>() {
            @Override
            public Predicate toPredicate(Root<DiscountGood> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if(goodsSearch.getGoodsClassId() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("goods").get("goodsClassId"), goodsSearch.getGoodsClassId()));
                }
                if(goodsSearch.getGoodsVerify() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("goodsVerify"), goodsSearch.getGoodsVerify()));
                }
                if (!StringUtils.isEmpty(goodsSearch.getSupplierName())) {
                    predicate = cb.and(predicate, cb.like(root.get("goods").get("supplier").get("supplierName"), "%" + goodsSearch.getSupplierName() + "%"));
                }
                if (!StringUtils.isEmpty(goodsSearch.getGoodsName())) {
                    predicate = cb.and(predicate, cb.like(root.get("goods").get("goodsName"), "%" + goodsSearch.getGoodsName() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return specialGoodsDao.findAll(spec, pageable);
    }

    @Override
    public Object getSpecialGoodsDetail(int goodsId) {
        return specialGoodsDao.findOne(goodsId);
    }

    @Transactional
    @Override
    public Object passedSpecialGoods(int goodsId) {
        DiscountGood goods = specialGoodsDao.findOne(goodsId);
        goods.setGoodsVerify(1);
        goods.setUpdateTime(new Date());
        specialGoodsDao.save(goods);
        Goods normalGoods = goods.getGoods();
        normalGoods.setGoodsStatus(Constants.GOODS_SHELVE);
        normalGoods.setUpdateTime(new Date());
        goodsDao.save(normalGoods);
        return goods;
    }

    @Override
    public Object rejectedSpecialGoods(int goodsId) {
        DiscountGood goods = specialGoodsDao.findOne(goodsId);
        goods.setGoodsVerify(2);
        goods.setUpdateTime(new Date());
        specialGoodsDao.save(goods);
        return goods;
    }

    @Override
    public Object unShelveGoods(int goodsId) {
        DiscountGood goods = specialGoodsDao.findOne(goodsId);
        goods.getGoods().setGoodsStatus(2);
        goods.setUpdateTime(new Date());
        specialGoodsDao.save(goods);
        return goods;
    }

    @Override
    public void downInvoledList(int actId, ByteArrayOutputStream os) throws IOException {
        StringBuffer sql = new StringBuffer("select t2.member_name, t1.coupon_name, t1.create_date, getDic('COUPOU_STATUS', t5.coupon_status), t4.order_time, t1.order_id from member_coupon t1 ");
        sql.append(" left join member t2 on t1.member_id = t2.member_id ");
        sql.append(" left join member_coupon t5 on t1.coupon_id = t5.coupon_id ");
        sql.append(" left join coupon_activity t3 on t3.coupon_id = t1.coupon_id ");
        sql.append(" left join orders t4 on t1.order_id = t4.order_id ");
        sql.append(" where t3.activity_id =  " + actId);
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> resultList = query.getResultList();
        Workbook wb = new HSSFWorkbook();
        String[] columnNames = {"用户名称","优惠券名称","领取时间","是否使用","使用时间","关联的订单"};
        ExcelUtil.createSheet(wb, "参与者名单", columnNames, resultList);
        wb.write(os);
    }

    /**
     * 判断活动是否已经开始，并返回是否可编辑,0表示可编辑，1表示不可编辑
     *
     * @param start
     * @return
     */
    private int getEditable(Date start) {
        int editable = 0; // 0表示可编辑，1表示不可编辑
        // 如果活动开始时间在当前时间之前，即活动已经开始，不可编辑，则返回小于0
        if (start.compareTo(new Date()) < 1) {
            editable = 1;
        }
        return editable;
    }

    /**
     * 验证时间开始时间是否大于结束时间
     *
     * @param start
     * @param end
     * @throws BusinessErrorException
     */
    private void checkDatePeriod(Date start, Date end) throws BusinessErrorException {
        if (start.compareTo(end) > 0) {
            throw new BusinessErrorException("023", "开始时间不能大于结束时间");
        }
    }
}
