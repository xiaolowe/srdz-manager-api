package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.Cash;
import cn.org.citycloud.srdz.entity.Order;
import cn.org.citycloud.srdz.entity.SalesMember;
import cn.org.citycloud.srdz.entity.Supplier;
import cn.org.citycloud.srdz.repository.*;
import cn.org.citycloud.srdz.service.IndexService;
import cn.org.citycloud.srdz.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/26 14:29
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private PayInfoDao payInfoDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private SalesMemberDao salesMemberDao;

    @Autowired
    private CashDao cashDao;

    @PersistenceContext
    private EntityManager em;


    @Override
    public Object getStatisticsData(int roleCode) {
        Map<String, Object> resultMap = new HashMap<>();
        // 如果登录用户为管理员
        if (roleCode == Constants.ROLE_ADMIN) {
            resultMap.put("todayTrades", getTodayTrades());
            resultMap.put("todayOrders", getTodayOrders());
            resultMap.put("supplierAudit", getSupplierAudit());
            resultMap.put("saleMemberAudit", getSalesMemberAudit());
        } else if (roleCode == Constants.ROLE_FIN) { // 如果登录用户为财务
            resultMap.put("cashAudit", getCashAudit());
        } else if (roleCode == Constants.ROLE_TELLER) { // 如果登录用户为出纳
            resultMap.put("caseEnsure", getCashEnsure());
        } else if (roleCode == Constants.ROLE_SUPER) { // 如果登录用户为超管
            resultMap.put("todayTrades", getTodayTrades().toString());
            resultMap.put("todayOrders", getTodayOrders());
            resultMap.put("supplierAudit", getSupplierAudit());
            resultMap.put("saleMemberAudit", getSalesMemberAudit());
            resultMap.put("cashAudit", getCashAudit());
            resultMap.put("caseEnsure", getCashEnsure());
        }
        return resultMap;
    }

    @Override
    public BigDecimal getTodayTrades() {
        BigDecimal result = payInfoDao.todayTrade(DateUtils.getDayStartTime(), DateUtils.getDayEndTime());
        if (result == null) {
            result = new BigDecimal(0);
            result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    @Override
    public Long getTodayOrders() {
        return orderDao.count(new Specification<cn.org.citycloud.srdz.entity.Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate where = cb.conjunction();
                Path<Date> addTime = root.get("orderTime");
                where = cb.and(where, cb.between(addTime, DateUtils.getDayStartTime(), DateUtils.getDayEndTime()));
                return query.where(where).getRestriction();
            }
        });
    }

    @Override
    public Long getSupplierAudit() {
        return supplierDao.count(new Specification<Supplier>() {
            @Override
            public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("status"), 1));
                return query.where(predicate).getRestriction();
            }
        });
    }

    @Override
    public Long getSalesMemberAudit() {
        return salesMemberDao.count(new Specification<SalesMember>() {
            @Override
            public Predicate toPredicate(Root<SalesMember> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("status"), 2));
                return query.where(predicate).getRestriction();
            }
        });
    }

    @Override
    public Long getCashAudit() {
        return cashDao.count(new Specification<Cash>() {
            @Override
            public Predicate toPredicate(Root<Cash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("confirmStatus"), 0));
                return query.where(predicate).getRestriction();
            }
        });
    }

    @Override
    public Long getCashEnsure() {
        return cashDao.count(new Specification<Cash>() {
            @Override
            public Predicate toPredicate(Root<Cash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("confirmStatus"), 1));
                return query.where(predicate).getRestriction();
            }
        });
    }

    @Override
    public Object getChartsData() {
        String dateSql = "select date from calendar where date >= date_sub(curdate(),interval 7 day) and date < curdate() order by date";
        String paySql = "select ifnull(SUM(t2.pay_money), 0) sumMoney from calendar t1 left join pay_info t2 on TO_DAYS(t1.date) = TO_DAYS(t2.pay_time) where t1.date >= (curdate()-7) and t1.date < curdate() group by t1.date order by t1.date";
        String orderSql = "select count(t2.order_id) countOrder from calendar t1 left join orders t2 on TO_DAYS(t1.date) = TO_DAYS(t2.order_time) where t1.date >= (curdate()-7) and t1.date < curdate() group by t1.date order by t1.date";
        Query dateQuery = em.createNativeQuery(dateSql);
        Query orderQuery = em.createNativeQuery(orderSql);
        Query payQuery = em.createNativeQuery(paySql);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("date", dateQuery.getResultList());
        resultMap.put("orderCount", orderQuery.getResultList());
        resultMap.put("paySum", payQuery.getResultList());
        return resultMap;
    }
}
