package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.OrderSearch;
import cn.org.citycloud.srdz.entity.Order;
import cn.org.citycloud.srdz.repository.OrderDao;
import cn.org.citycloud.srdz.service.OrderService;
import cn.org.citycloud.srdz.utils.DateUtils;
import cn.org.citycloud.srdz.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单管理service接口实现.
 *
 * @author demon
 * @Date 2016/4/25 10:41
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @PersistenceContext
    private EntityManager em;


    @Override
    public Object getOrderList(OrderSearch orderSearch) {
        int page = orderSearch.getPageNo();
        int size = orderSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "orderTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        return orderDao.findAll(getSpec(orderSearch), pageable);
    }

    @Override
    public Object getOrderDetail(int orderId) {
        return orderDao.findOne(orderId);
    }

    @Override
    public void downOrderExcel(OrderSearch orderSearch, ByteArrayOutputStream os) throws IOException {
        // 创建第一个sheet
        String columnNames[] = {"支付单号", "收支类型", "订单号", "创建时间", "付款时间", "成本价", "订单总金额", "优惠券金额", "实际收入金额", "平台分成比例", "平台分成", "服务中心比例", "服务中心分成", "分销利润率", "分销利润", "供应商收益", "状态", "联系人", "联系电话", "付款方式", "供应商", "服务中心"};//列名
        String keys[] = {"payId", "inOrOut", "orderId", "orderTime", "payTime", "initPrice", "orderPrice", "couponPrice", "payPrice", "platformRates", "platformAmount", "serviceCenterRates", "serviceCenterAmount", "salesRates", "salesAmount", "supplierAmount", "orderStatus", "contactName", "contactPhone", "payCode", "supplierName", "serviceName"};//map中的key
        List<Order> resultList = orderDao.findAll(getSpec(orderSearch));
        List<Map<String, Object>> list = createExcelRecord(resultList);
        Workbook wb = ExcelUtil.createWorkBook(list, keys, columnNames);
        String columnNames2[] = {"支付单号", "订单号", "商品编号", "商品名称", "分类1", "分类2", "分类3", "规格", "单价", "商品数量", "总价", "状态", "关联供货商"};
        String keys2[] = {"payId", "orderId", "goodsId", "goodsName", "classify1", "classify2", "classify3", "standard", "price", "goodsNum", "total", "status", "supplierName"};
        StringBuffer sql = new StringBuffer("SELECT t1.pay_id, t1.order_id, t2.goods_id, t2.goods_name, t3.goods_class_name, t3.goods_class_two_name, t3.goods_class_three_name, t3.standard, t2.goods_price, t2.goods_num, t2.goods_pay_price, t1.order_status, t4.supplier_name FROM orders t1 LEFT JOIN order_goods t2 ON t1.order_id = t2.order_id LEFT JOIN goods t3 ON t2.goods_id = t3.goods_id LEFT JOIN supplier t4 ON t3.supplier_id = t4.supplier_id where 1 = 1");
        if (orderSearch.getOrderStatus() != -1) {
            sql.append(" and t1.order_status = " + orderSearch.getOrderStatus());
        }
        if (!StringUtils.isEmpty(orderSearch.getOrderId())) {
            sql.append(" and t1.order_id like '%" + orderSearch.getOrderId() + "%'");
        }
        if (!StringUtils.isEmpty(orderSearch.getMemberName())) {
            sql.append(" and t1.memeber_name like '%" + orderSearch.getMemberName() + "%'");
        }
        if (!StringUtils.isEmpty(orderSearch.getSupplierName())) {
            sql.append(" and t4.supplier_name like '%" + orderSearch.getSupplierName() + "%'");
        }
        if (!StringUtils.isEmpty(orderSearch.getStime())) {
            sql.append(" and t1.order_time >= '" + orderSearch.getStime() + "'");
        }
        if (!StringUtils.isEmpty(orderSearch.getEtime())) {
            sql.append(" and t1.order_time <= '" + orderSearch.getEtime() + "'");
        }
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> result = query.getResultList();
        ExcelUtil.createSheet(wb, keys2, columnNames2, result, "订单报表(2)");
        wb.write(os);
    }

    /**
     * 获取查询条件
     *
     * @param orderSearch
     * @return
     */
    private Specification<Order> getSpec(OrderSearch orderSearch) {
        Specification<Order> spec = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (orderSearch.getBackOrderStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("backOrderStatus"), orderSearch.getBackOrderStatus()));
                }
                if (!StringUtils.isEmpty(orderSearch.getStime())) {
                    String stime = orderSearch.getStime() + " 00:00:00";
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("orderTime"), DateUtils.parse(stime, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(orderSearch.getEtime())) {
                    String etime = orderSearch.getEtime() + " 23:59:59";
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("orderTime"), DateUtils.parse(etime, DateUtils.DATE_PATTERN_1)));
                }
                if (orderSearch.getOrderStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("orderStatus"), orderSearch.getOrderStatus()));
                }
                if (orderSearch.getOrderType() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("orderType"), orderSearch.getOrderType()));
                }
                if (!StringUtils.isEmpty(orderSearch.getMemberName())) {
                    predicate = cb.and(predicate, cb.like(root.get("memberName"), "%" + orderSearch.getMemberName() + "%"));
                }
                if (!StringUtils.isEmpty(orderSearch.getSupplierName())) {
                    predicate = cb.and(predicate, cb.like(root.get("orderInfo").get("supplierName"), "%" + orderSearch.getSupplierName() + "%"));
                    // TODO 分销是不是要查询
                }
                if (!StringUtils.isEmpty(orderSearch.getOrderId())) {
                    predicate = cb.and(predicate, cb.like(root.get("orderId").as(String.class), "%" + orderSearch.getOrderId() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return spec;
    }

    private List<Map<String, Object>> createExcelRecord(List<Order> orders) {
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "订单报表(1)");
        resultMap.add(map);
        Order order = null;
        for (int j = 0; j < orders.size(); j++) {
            order = orders.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("payId", order.getPayId());
            mapValue.put("inOrOut", "收入");
            mapValue.put("orderId", order.getOrderId());
            mapValue.put("orderTime", order.getOrderTime());
            mapValue.put("payTime", order.getPayTime());
            mapValue.put("initPrice", order.getInitPrice());
            mapValue.put("orderPrice", order.getOrderPrice());
            mapValue.put("couponPrice", order.getCouponPrice());
            mapValue.put("payPrice", order.getPayPrice());
            if (order.getSalesOrderInfo() != null) {
                mapValue.put("platformRates", order.getSalesOrderInfo().getPlatformRates());
                mapValue.put("platformAmount", order.getSalesOrderInfo().getPlatformAmount());
                mapValue.put("serviceCenterRates", order.getSalesOrderInfo().getServiceCenterRates());
                mapValue.put("serviceCenterAmount", order.getSalesOrderInfo().getServiceCenterAmount());
                mapValue.put("salesRates", order.getSalesOrderInfo().getSaleRates());
                mapValue.put("salesAmount", order.getSalesOrderInfo().getSaleAmount());
                mapValue.put("supplierName", "");
                mapValue.put("serviceName", "");
            } else if (order.getOrderInfo() != null) {
                mapValue.put("platformRates", order.getOrderInfo().getPlatformRates());
                mapValue.put("platformAmount", order.getOrderInfo().getPlatformAmount());
                mapValue.put("serviceCenterRates", order.getOrderInfo().getServiceCenterRates());
                mapValue.put("serviceCenterAmount", order.getOrderInfo().getServiceCenterAmount());
                mapValue.put("salesRates", "");
                mapValue.put("salesAmount", "");
                mapValue.put("supplierName", order.getOrderInfo().getSupplierName());
                mapValue.put("serviceName", order.getOrderInfo().getSupplier().getServiceCenter().getServiceCenterName());
            }
            mapValue.put("supplierAmount", order.getSupplierAmount());
            mapValue.put("orderStatus", order.getOrderStatus());
            mapValue.put("contactName", order.getContactName());
            mapValue.put("contactPhone", order.getContactPhone());
            mapValue.put("payCode", order.getPayCode());
            resultMap.add(mapValue);
        }
        return resultMap;

    }
}
