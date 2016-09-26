package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.InOutSumBean;
import cn.org.citycloud.srdz.bean.ReportBean;
import cn.org.citycloud.srdz.bean.ReportGoodsItemBean;
import cn.org.citycloud.srdz.bean.ReportSearch;
import cn.org.citycloud.srdz.service.ReportService;
import cn.org.citycloud.srdz.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报表service接口实现.
 *
 * @author demon
 * @Date 2016/4/22 14:34
 */
@Service
public class ReportServiceImpl implements ReportService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Object getReport(ReportSearch reportSearch) {
        if (reportSearch.getSupplierId() != -1) {
            return getReportBySupplier(reportSearch);
        }
        if (reportSearch.getClassfyId() != -1) {
            return getReportByClassify(reportSearch);
        }
        return getAllReport(reportSearch);
    }

    @Override
    public Object getItemList(ReportSearch reportSearch) {
        if (reportSearch.getClassfyId() != -1) {
            return getGoodsItemList(reportSearch);
        }
        return getBudgetList(reportSearch);
    }

    @Override
    public void downReportExcel(ReportSearch reportSearch, ByteArrayOutputStream os) throws IOException {
        Workbook wb = new HSSFWorkbook();
        createSheet1(wb, reportSearch);
        createSheet2(wb, reportSearch);
        createSheet3(wb, reportSearch);
        createSheet4(wb, reportSearch);
        createSheet5(wb, reportSearch);
        createSheet6(wb, reportSearch);
        wb.write(os);
    }

    /**
     * 查询所有的报表统计信息
     *
     * @param reportSearch
     * @return
     */
    private Object getAllReport(ReportSearch reportSearch) {
        // 查询统计数据
        StringBuffer sql = new StringBuffer("SELECT ifnull(sum(apply_money), 0) money_count, count(1) itemCount FROM cash WHERE confirm_status = 2 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(paying_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(paying_time) = " + reportSearch.getMonth());
        }
        sql.append(" UNION ALL");
        sql.append(" SELECT ifnull((sum(t1.pay_money)), 0) money_count, count(1) itemCount FROM pay_info t1 where 1 = 1 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(pay_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(pay_time) = " + reportSearch.getMonth());
        }
        Query query = em.createNativeQuery(sql.toString());
        List result = query.getResultList();
        return assembleReportBean(result);
    }

    /**
     * 根据供应商查询报表统计信息
     *
     * @param reportSearch
     * @return
     */
    private Object getReportBySupplier(ReportSearch reportSearch) {
        // 查询统计数据
        StringBuffer sql = new StringBuffer("SELECT ifnull(sum(apply_money), 0) money_count, count(1) itemCount FROM cash WHERE confirm_status = 2 ");
        sql.append("and supplier_id = " + reportSearch.getSupplierId());
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(paying_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(paying_time) = " + reportSearch.getMonth());
        }
        sql.append(" UNION ALL");
        sql.append(" SELECT ifnull((sum(t1.pay_money)), 0) money_count, count(1) itemCount FROM pay_info t1 ");
        sql.append(" where t1.supplier_id =" + reportSearch.getSupplierId());
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(pay_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(pay_time) = " + reportSearch.getMonth());
        }
        Query query = em.createNativeQuery(sql.toString());
        List result = query.getResultList();
        return assembleReportBean(result);
    }

    /**
     * 根据商品分类查询报表统计信息
     *
     * @param reportSearch
     * @return
     */
    private Object getReportByClassify(ReportSearch reportSearch) {
        StringBuffer sql = new StringBuffer("SELECT 0 money_count, 0 itemCount FROM cash WHERE confirm_status = 2 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(paying_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(paying_time) = " + reportSearch.getMonth());
        }
        sql.append(" UNION ALL");
        sql.append(" SELECT IFNULL((SUM(t1.pay_money)), 0) money_count, COUNT(1) itemCount FROM pay_info t1 ");
        sql.append(" LEFT JOIN orders t2 ON t1.pay_id = t2.pay_id");
        sql.append(" LEFT JOIN order_goods t3 ON t2.order_id = t3.order_id");
        sql.append(" LEFT JOIN goods t4 ON t3.goods_id = t4.goods_id ");
        sql.append(" WHERE t4.goods_class_id =  " + reportSearch.getClassfyId());
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t1.pay_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t1.pay_time) = " + reportSearch.getMonth());
        }
        Query query = em.createNativeQuery(sql.toString());
        List result = query.getResultList();
        return assembleReportBean(result);
    }

    /**
     * 获取商品分类报表详情
     *
     * @param reportSearch
     * @return
     */
    private Object getGoodsItemList(ReportSearch reportSearch) {
        StringBuffer sql = new StringBuffer("SELECT t1.goods_id,t1.goods_name,");
        sql.append("( SELECT count(1) FROM orders t2 INNER JOIN order_goods t3 ON t2.order_id = t3.order_id WHERE t3.goods_id = t1.goods_id and t2.order_status = 40 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t2.order_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t2.order_time) = " + reportSearch.getMonth());
        }
        sql.append(" ) orderCount,");
        sql.append("( SELECT count(1) FROM pay_info t3 INNER JOIN orders t4 ON t3.pay_id = t4.pay_id INNER JOIN order_goods t5 ON t5.order_id = t4.order_id WHERE t5.goods_id = t1.goods_id ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t3.pay_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t3.pay_time) = " + reportSearch.getMonth());
        }
        sql.append(") payCount,");
        sql.append("t1.already_sale,t1.standard,");
        sql.append("( SELECT sum(t5.pay_money) FROM pay_info t5 INNER JOIN orders t6 ON t5.pay_id = t6.pay_id INNER JOIN order_goods t7 ON t6.order_id = t7.order_id WHERE t7.goods_id = t1.goods_id ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t5.pay_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t5.pay_time) = " + reportSearch.getMonth());
        }
        sql.append(" ) paySum");
        sql.append(" FROM goods t1 where 1 = 1 ");
        if (reportSearch.getClassfyId() != -1) {
            sql.append(" and t1.goods_class_id = " + reportSearch.getClassfyId());
        }
        sql.append(" order by paySum desc");
        int page = reportSearch.getPageNo();
        int pageSize = reportSearch.getPageSize();
        int start = (page - 1) * pageSize;
        sql.append(" limit " + start + "," + pageSize);
        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> result = query.getResultList();
        List<ReportGoodsItemBean> resultList = assembleReortItemList(result);
        StringBuffer countSql = new StringBuffer("select count(1) from goods where 1 = 1");
        if (reportSearch.getClassfyId() != -1) {
            countSql.append(" and goods_class_id = " + reportSearch.getClassfyId());
        }
        Query countQuery = em.createNativeQuery(countSql.toString());
        BigInteger total = (BigInteger) countQuery.getSingleResult();
        Pageable pageable = new PageRequest(page - 1, pageSize);
        Page<ReportGoodsItemBean> resultPage = new PageImpl<>(resultList, pageable, total.longValue());
        return resultPage;
    }


    /**
     * 查询收支列表(分页)
     *
     * @param reportSearch
     * @return
     */
    private Object getBudgetList(ReportSearch reportSearch) {
        StringBuffer sql = assembleSql(reportSearch, true);
        int page = reportSearch.getPageNo();
        int pageSize = reportSearch.getPageSize();
        int start = (page - 1) * pageSize;
        sql.append(" limit " + start + "," + pageSize);
        Query query = em.createNativeQuery(sql.toString());
        List<ReportBean> result = assembleBudget(query.getResultList());
        StringBuffer countSql = new StringBuffer("select count(1) from calendar where 1 = 1");
        if (reportSearch.getYear() != -1) {
            countSql.append(" and YEAR(date) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            countSql.append(" and MONTH(date) = " + reportSearch.getMonth());
        }
        Query countQuery = em.createNativeQuery(countSql.toString());
        BigInteger total = (BigInteger) countQuery.getSingleResult();
        Pageable pageable = new PageRequest(page - 1, pageSize);
        Page<ReportBean> resultPage = new PageImpl<>(result, pageable, total.longValue());
        return resultPage;
    }

    /**
     * 查询收支列表，用于报表下载
     *
     * @param reportSearch
     * @return
     */
    private List<Object[]> getBudgeList(ReportSearch reportSearch) {
        Query query = em.createNativeQuery(assembleSql(reportSearch, false).toString());
        return query.getResultList();
    }


    /**
     * 组合报表详情List
     *
     * @param list
     * @return
     */
    private List<ReportGoodsItemBean> assembleReortItemList(List<Object[]> list) {
        List<ReportGoodsItemBean> reportGoodsItemBeanList = new ArrayList<>();
        for (Object[] arr : list) {
            if (arr.length == 7) {
                ReportGoodsItemBean reportGoodsItemBean = new ReportGoodsItemBean();
                reportGoodsItemBean.setGoodsId((Integer) arr[0]);
                reportGoodsItemBean.setGoodsName((String) arr[1]);
                reportGoodsItemBean.setProduceOrderCount((BigInteger) arr[2]);
                reportGoodsItemBean.setTradeOrderCount((BigInteger) arr[3]);
                reportGoodsItemBean.setOutStockCount((int) arr[4]);
                reportGoodsItemBean.setStandard((String) arr[5]);
                reportGoodsItemBean.setPaySum((BigDecimal) arr[6]);
                reportGoodsItemBeanList.add(reportGoodsItemBean);
            }
        }
        return reportGoodsItemBeanList;
    }

    /**
     * 组合报表bean
     *
     * @param list
     * @return
     */
    private ReportBean assembleReportBean(List list) {
        ReportBean reportBean = new ReportBean();
        if (list.size() == 2) {
            Object[] outArr = (Object[]) list.get(0);
            Object[] inArr = (Object[]) list.get(1);
            if (inArr.length == 2 && outArr.length == 2) {
                reportBean.setOutMoney((BigDecimal) outArr[0]);
                reportBean.setOutReceiptsCount((BigInteger) outArr[1]);
                reportBean.setInMoney((BigDecimal) inArr[0]);
                reportBean.setInReceiptsCount((BigInteger) inArr[1]);
            }

        }
        return reportBean;
    }

    /**
     * 组装收支详情列表
     *
     * @param list
     * @return
     */
    private List<ReportBean> assembleBudget(List<Object[]> list) {
        List<ReportBean> result = new ArrayList<>();
        for (Object[] objArr : list) {
            if (objArr.length == 5) {
                ReportBean reportBean = new ReportBean();
                reportBean.setDate((Date) objArr[0]);
                reportBean.setInReceiptsCount((BigInteger) objArr[1]);
                reportBean.setInMoney((BigDecimal) objArr[2]);
                reportBean.setOutReceiptsCount((BigInteger) objArr[3]);
                reportBean.setOutMoney((BigDecimal) objArr[4]);
                result.add(reportBean);
            }
        }
        return result;
    }

    /**
     * 组装查询报表详情sql
     *
     * @param reportSearch
     * @param isPageable
     * @return
     */
    private StringBuffer assembleSql(ReportSearch reportSearch, boolean isPageable) {
        StringBuffer sql = new StringBuffer("SELECT ");
        if (isPageable) {
            sql.append("date,");
        } else {
            sql.append("DATE_FORMAT(date, '%m月%d日'),");
        }
        // 查询收入单据数
        sql.append("(SELECT count(1) FROM pay_info WHERE DATE_FORMAT(pay_time, '%Y-%m-%d') = date ");
        if (reportSearch.getSupplierId() != -1) {
            sql.append("and supplier_id = " + reportSearch.getSupplierId());
        }
        sql.append(") inCount, ");
        // 查询收入
        sql.append("(SELECT ifnull((sum(pay_money)), 0) FROM pay_info WHERE DATE_FORMAT(pay_time, '%Y-%m-%d') = date ");
        if (reportSearch.getSupplierId() != -1) {
            sql.append("and supplier_id = " + reportSearch.getSupplierId());
        }
        sql.append(") income,");
        // 查询支出单据数
        sql.append("(SELECT count(1) from cash where confirm_status = 2 and DATE_FORMAT(paying_time, '%Y-%m-%d') = date ");
        if (reportSearch.getSupplierId() != -1) {
            sql.append("and supplier_id = " + reportSearch.getSupplierId());
        }
        sql.append(") outCount, ");
        // 查询支出
        sql.append("(SELECT ifnull(sum(apply_money), 0) from cash where confirm_status = 2 and DATE_FORMAT(paying_time, '%Y-%m-%d') = date ");
        if (reportSearch.getSupplierId() != -1) {
            sql.append("and supplier_id = " + reportSearch.getSupplierId());
        }
        sql.append(") outcome ");
        sql.append("FROM calendar where 1 = 1");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(date) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(date) = " + reportSearch.getMonth());
        }
        return sql;
    }

    /**
     * 组装汇总信息bean
     *
     * @param list
     * @return
     */
    private InOutSumBean assembleSumBean(List<Object[]> list) {
        InOutSumBean inOutSumBean = new InOutSumBean();
        inOutSumBean.setInUnpayCount((BigInteger) list.get(0)[0]);
        inOutSumBean.setInUnpaySum((BigDecimal) list.get(0)[1]);
        inOutSumBean.setInPayedCount((BigInteger) list.get(1)[0]);
        inOutSumBean.setInPayedSum((BigDecimal) list.get(1)[1]);
        inOutSumBean.setOutUnpayCount((BigInteger) list.get(2)[0]);
        inOutSumBean.setOutUnpaySum((BigDecimal) list.get(2)[1]);
        inOutSumBean.setOutPayedCount((BigInteger) list.get(3)[0]);
        inOutSumBean.setOutPayedSum((BigDecimal) list.get(3)[1]);
        return inOutSumBean;
    }

    /**
     * 查询产品分析表商品列表的数据
     *
     * @param reportSearch
     * @return
     */
    private List<Object[]> getProductList(ReportSearch reportSearch) {
        StringBuffer sql = new StringBuffer("SELECT t1.goods_id,t1.goods_name,t1.goods_class_name,t1.goods_class_two_name,t1.goods_class_three_name,");
        sql.append("( SELECT count(1) FROM orders t2 INNER JOIN order_goods t3 ON t2.order_id = t3.order_id WHERE t3.goods_id = t1.goods_id ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t2.order_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t2.order_time) = " + reportSearch.getMonth());
        }
        sql.append(" ) orderCount,");
        sql.append("( SELECT count(1) FROM pay_info t3 INNER JOIN orders t4 ON t3.pay_id = t4.pay_id INNER JOIN order_goods t5 ON t5.order_id = t4.order_id WHERE t5.goods_id = t1.goods_id ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t3.pay_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t3.pay_time) = " + reportSearch.getMonth());
        }
        sql.append(") payCount,");
        sql.append("t1.already_sale,t1.standard,");
        sql.append("( SELECT sum(t5.pay_money) FROM pay_info t5 INNER JOIN orders t6 ON t5.pay_id = t6.pay_id INNER JOIN order_goods t7 ON t6.order_id = t7.order_id WHERE t7.goods_id = t1.goods_id ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t5.pay_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t5.pay_time) = " + reportSearch.getMonth());
        }
        sql.append(" ) paySum");
        sql.append(" FROM goods t1 where 1 = 1 ");
        if (reportSearch.getClassfyId() != -1) {
            sql.append(" and t1.goods_class_id = " + reportSearch.getClassfyId());
        }
        sql.append(" order by paySum desc");
        Query query = em.createNativeQuery(sql.toString());
        return query.getResultList();
    }

    /**
     * 查询出入帐汇总信息（用于报表）
     *
     * @param reportSearch
     * @return
     */
    private List<Object[]> getInOrOutSum(ReportSearch reportSearch) {
        StringBuffer sql = new StringBuffer("select count(1), ifnull(SUM(pay_price), 0) from orders where order_status = 10");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(order_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(order_time) = " + reportSearch.getMonth());
        }
        sql.append(" union all ");
        sql.append("select count(1), ifnull(SUM(pay_price), 0) from orders where order_status = 20");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(order_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(order_time) = " + reportSearch.getMonth());
        }
        sql.append(" union all ");
        sql.append("select count(1), ifnull(SUM(apply_money),0) from cash where confirm_status = 2");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(create_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(create_time) = " + reportSearch.getMonth());
        }
        sql.append(" union all ");
        sql.append("select count(1), ifnull(SUM(apply_money),0) from cash where confirm_status != 2 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(create_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(create_time) = " + reportSearch.getMonth());
        }
        Query query = em.createNativeQuery(sql.toString());
        return query.getResultList();
    }

    /**
     * 查询出入帐明细
     *
     * @param reportSearch
     * @return
     */
    private List<Object[]> getInorOutDetailList(ReportSearch reportSearch) {
        StringBuffer sql = new StringBuffer("select cash_id payId, '支出' budgetType, getDic('APPLY_TYPE', apply_type) type, create_time createTime, paying_time payTime, 0 income, apply_money outcome, getDic('CASH_STATUS', confirm_status) payStatus, '善容大宗平台' payCompany, '-' payer, '银行转账' payType,company_name companyName, apply_user_name receiveUser, apply_phone tel, bank_acc_number bankAcc, bank_name bankName, confirm_user_name auditer, paying_user_name teller");
        sql.append(" from cash where 1 = 1 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(create_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(create_time) = " + reportSearch.getMonth());
        }
        sql.append(" union all ");
        sql.append(" select pay_id payId, '收入' budgetType, '订单' type, create_time createTime, pay_time payTime, pay_money income, 0 outcome, '已付款' payStatus, '-' payCompany,pay_member_name payer, getDic('PAY_TYPE', pay_style) payType, '善容大宗平台' companyName, '-' receiveUser, pay_member_phone tel,'-' bankAcc, '-' bankName, '-' auditer, '-' teller");
        sql.append(" from pay_info where 1 = 1");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(create_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(create_time) = " + reportSearch.getMonth());
        }
        Query query = em.createNativeQuery(sql.toString());
        return query.getResultList();
    }

    /**
     * 查询出入帐明细关联订单
     *
     * @param reportSearch
     * @return
     */
    private List<Object[]> getInorOutJoinOrders(ReportSearch reportSearch) {
        StringBuffer sql = new StringBuffer("select  t2.pay_id payId, '收入' budgetType, '订单' type, t1.order_id orderNo, t1.create_time createTime, t1.pay_time payTime, ");
        sql.append(" t1.order_price orderPrice, t1.coupon_price couponPrice, t1.flow_price flowPrice, t1.pay_price actualIn, 0 actualOut, getDic('ORDER_STATUS', t1.order_status) status, t2.pay_member_name payer, ");
        sql.append(" getDic('PAY_TYPE', t2.pay_style) payType, '善容大宗平台' receiveCom, '-' receiver, '-' bankAcc, '-' bankName, '-' auditer, '-' teller, t5.supplier_name,t6.service_center_name ");
        sql.append(" from orders t1 left join pay_info t2 on t1.pay_id = t2.pay_id ");
        sql.append(" left join order_goods t3 on t1.order_id = t3.order_id ");
        sql.append(" left join goods t4 on t4.goods_id = t3.goods_id ");
        sql.append(" left join supplier t5 on t4.supplier_id = t5.supplier_id ");
        sql.append(" left join service_center t6 on t5.service_center_id = t6.service_center_id where 1 = 1 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t1.order_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t1.order_time) = " + reportSearch.getMonth());
        }
        sql.append(" union all ");
        sql.append(" select t1.cash_id payId, '支出' budgetType, getDic('PAY_TYPE', t1.apply_type) type, '-' orderNo, t1.create_time createTime, t1.paying_time payTime, ");
        sql.append(" 0 orderPrice, 0 couponPrice, 0 flowPrice, 0 actualIn, t1.apply_money actualOut, getDic('CASH_STATUS', t1.confirm_status) status, '善容大宗' payer, ");
        sql.append(" '银行转账' payType, t1.company_name receiveCom, t1.apply_user_name receiver, t1.bank_acc_number bankAcc, t1.bank_acc_name bankName, t1.confirm_user_name auditer, t1.paying_user_name teller, t2.supplier_name, t3.service_center_name ");
        sql.append(" from cash t1 ");
        sql.append(" left join supplier t2 on t1.supplier_id = t2.supplier_id ");
        sql.append(" left join service_center t3 on t1.service_center_id = t3.service_center_id where 1 = 1 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t1.create_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t1.create_time) = " + reportSearch.getMonth());
        }
        Query query = em.createNativeQuery(sql.toString());
        return query.getResultList();
    }

    /**
     * 查询出入账目关联商品表
     *
     * @param reportSearch
     * @return
     */
    private List<Object[]> getInorOutJoinGoods(ReportSearch reportSearch) {
        StringBuffer sql = new StringBuffer("select t1.pay_id, t1.order_id, t4.goods_id, t4.goods_name, t4.goods_class_name, t4.goods_class_two_name, t4.goods_class_three_name, t3.standard,t3.goods_price, t3.goods_num,t1.pay_price,getDic('ORDER_STATUS', t1.order_status) status,t5.supplier_name, t6.service_center_name");
        sql.append(" from orders t1 left join pay_info t2 on t1.pay_id = t2.pay_id ");
        sql.append(" left join order_goods t3 on t1.order_id = t3.order_id ");
        sql.append(" left join goods t4 on t3.goods_id = t4.goods_id ");
        sql.append(" left join supplier t5 on t4.supplier_id = t5.supplier_id ");
        sql.append(" left join service_center t6 on t5.service_center_id = t6.service_center_id where 1 = 1 ");
        if (reportSearch.getYear() != -1) {
            sql.append(" and YEAR(t1.order_time) = " + reportSearch.getYear());
        }
        if (reportSearch.getMonth() != -1) {
            sql.append(" and MONTH(t1.order_time) = " + reportSearch.getMonth());
        }
        Query query = em.createNativeQuery(sql.toString());
        return query.getResultList();
    }

    /**
     * 创建报表excel的第一个sheet
     *
     * @param wb
     * @param reportSearch
     */
    private void createSheet1(Workbook wb, ReportSearch reportSearch) {
        Sheet sheet = wb.createSheet(changeSheetName("销售分析报表", reportSearch));
        Row row = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 1, 4);
        //在sheet里增加合并单元格
        sheet.addMergedRegion(cra);
        Cell cell0_0 = row.createCell(0);
        cell0_0.setCellValue("表名");
        Cell cell0_1 = row.createCell(1);
        cell0_1.setCellValue("善融大宗管理平台" + reportSearch.getYear() + "年" + reportSearch.getMonth() + "月销售报表");

        sheet.createRow(1);

        String[] rowNames = {"收入单据：", "收入金额：", "支出单据：", "支出金额："};
        // 查询统计数据
        ReportBean reportBean = (ReportBean) getReport(reportSearch);
        String[] rowKeys = {reportBean.getInReceiptsCount().toString(), reportBean.getInMoney().toString(), reportBean.getOutReceiptsCount().toString(), reportBean.getOutMoney().toString()};
        createRowCell(rowNames, rowKeys, sheet);

        sheet.createRow(6);

        String[] columnNames = {"日期", "收入单据", "收入金额", "支出单据", "支出金额"};
        Row row7 = sheet.createRow(7);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell7_N = row7.createCell(i);
            cell7_N.setCellValue(columnNames[i]);
        }
        List<Object[]> dataList = getBudgeList(reportSearch);
        for (int i = 1; i <= dataList.size(); i++) {
            Row rowN = sheet.createRow(7 + i);
            Object[] data = dataList.get(i - 1);
            for (int j = 0; j < data.length; j++) {
                Cell cellI_J = rowN.createCell(j);
                cellI_J.setCellValue(data[j].toString());
            }
        }
    }

    /**
     * 创建报表excel的第二个sheet
     *
     * @param wb
     * @param reportSearch
     */
    private void createSheet2(Workbook wb, ReportSearch reportSearch) {
        Sheet sheet = wb.createSheet(changeSheetName("商品分析报表", reportSearch));
        Row row = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 1, 4);
        //在sheet里增加合并单元格
        sheet.addMergedRegion(cra);
        Cell cell0_0 = row.createCell(0);
        cell0_0.setCellValue("表名");
        Cell cell0_1 = row.createCell(1);
        cell0_1.setCellValue("善融大宗管理平台" + reportSearch.getYear() + "年" + reportSearch.getMonth() + "月产品报表");

        sheet.createRow(1);

        String[] rowNames = {"收入单据：", "收入金额：", "支出单据：", "支出金额："};
        // 查询统计数据
        ReportBean reportBean = (ReportBean) getReport(reportSearch);
        String[] rowKeys = {reportBean.getInReceiptsCount().toString(), reportBean.getInMoney().toString(), reportBean.getOutReceiptsCount().toString(), reportBean.getOutMoney().toString()};
        createRowCell(rowNames, rowKeys, sheet);

        sheet.createRow(6);

        String[] columnNames = {"商品编码", "商品名称", "一级分类", "二级分类", "三级分类", "产生订单数", "交易订单数量", "出货量", "规格", "金额"};
        Row row7 = sheet.createRow(7);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell7_N = row7.createCell(i);
            cell7_N.setCellValue(columnNames[i]);
        }
        // 添加查询的数据
        List<Object[]> dataList = getProductList(reportSearch);
        for (int i = 1; i <= dataList.size(); i++) {
            Object[] data = dataList.get(i - 1);
            Row rowI = sheet.createRow(7 + i);
            for (int j = 0; j < data.length; j++) {
                if (data[j] == null) {
                    data[j] = "-";
                }
                Cell cellI_J = rowI.createCell(j);
                cellI_J.setCellValue(data[j].toString());
            }
        }
    }

    /**
     * 创建报表excel的第三个sheet
     *
     * @param wb
     * @param reportSearch
     */
    private void createSheet3(Workbook wb, ReportSearch reportSearch) {
        Sheet sheet = wb.createSheet(changeSheetName("出入账目汇总（表0）", reportSearch));
        CellRangeAddress incomeCell = new CellRangeAddress(2, 3, 0, 0);
        CellRangeAddress outcomeCell = new CellRangeAddress(4, 5, 0, 0);
        CellRangeAddress inCountCell = new CellRangeAddress(2, 3, 4, 4);
        CellRangeAddress outCountCell = new CellRangeAddress(4, 5, 4, 4);
        sheet.addMergedRegion(incomeCell);
        sheet.addMergedRegion(outcomeCell);
        sheet.addMergedRegion(inCountCell);
        sheet.addMergedRegion(outCountCell);

        Row row0 = sheet.createRow(0);
        Cell cell0_0 = row0.createCell(0);
        cell0_0.setCellValue("汇总报表");
        Cell cell0_1 = row0.createCell(1);
        cell0_1.setCellValue("状态");

        Row row1 = sheet.createRow(1);
        String[] columnNames = {"收支类型", "状态", "单数", "小计", "合计"};
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell1_N = row1.createCell(i);
            cell1_N.setCellValue(columnNames[i]);
        }
        List<Object[]> keyList = new ArrayList<>();
        InOutSumBean inOutSumBean = assembleSumBean(getInOrOutSum(reportSearch));
        BigDecimal incomeSum = inOutSumBean.getInPayedSum().add(inOutSumBean.getInUnpaySum());
        BigDecimal outcomeSum = inOutSumBean.getOutPayedSum().add(inOutSumBean.getOutUnpaySum());
        Object[] row2Arr = {"收入", "已付款", inOutSumBean.getInPayedCount(), inOutSumBean.getInPayedSum(), incomeSum};
        Object[] row3Arr = {"收入", "未付款", inOutSumBean.getInUnpayCount(), inOutSumBean.getInUnpaySum(), incomeSum};
        Object[] row4Arr = {"支出", "已支付", inOutSumBean.getOutPayedCount(), inOutSumBean.getOutPayedSum(), outcomeSum};
        Object[] row5Arr = {"支出", "未支付", inOutSumBean.getOutUnpayCount(), inOutSumBean.getOutUnpaySum(), outcomeSum};
        keyList.add(row2Arr);
        keyList.add(row3Arr);
        keyList.add(row4Arr);
        keyList.add(row5Arr);
        for (int i = 0; i < keyList.size(); i++) {
            Row rowI = sheet.createRow(2 + i);
            Object[] data = keyList.get(i);
            for (int j = 0; j < data.length; j++) {
                Cell cellI_J = rowI.createCell(j);
                cellI_J.setCellValue(data[j].toString());
            }
        }
    }

    /**
     * 创建报表excel的第四个sheet
     *
     * @param wb
     * @param reportSearch
     */
    private void createSheet4(Workbook wb, ReportSearch reportSearch) {
        String[] columnNames = {"支付单号","收支类型","类型","创建时间","付款时间","实际收入金额","实际支出金额","状态","付款单位","付款人","付款方式","收款单位","收款人","联系电话","收款帐号","开户行","审核人","打款人"};
        ExcelUtil.createSheet(wb, changeSheetName("出入账目（表1）", reportSearch), columnNames, getInorOutDetailList(reportSearch));
    }

    /**
     * 创建报表excel的第五个sheet
     *
     * @param wb
     * @param reportSearch
     */
    private void createSheet5(Workbook wb, ReportSearch reportSearch) {
        String[] columnNames = {"支付单号","收支类型","类型","订单号","创建时间","付款时间","订单金额","优惠券抵用金额","物流费用","实际收入金额","实际支出金额","状态","付款单位","付款方式","收款单位","收款人","收款帐号","开户行","审核人","打款人","关联供应商","关联服务中心"};
        ExcelUtil.createSheet(wb, changeSheetName("出入账目关联订单表（表2）", reportSearch), columnNames, getInorOutJoinOrders(reportSearch));
    }

    /**
     * 创建报表excel的第六个sheet
     *
     * @param wb
     * @param reportSearch
     */
    private void createSheet6(Workbook wb, ReportSearch reportSearch) {
        String[] columnNames = {"支付单号","订单号","商品编号","商品名称","分类1","分类2","分类3","规格","商品单价","商品数量","总价","状态","关联供应商","关联服务中心"};
        ExcelUtil.createSheet(wb, changeSheetName("出入账目关联商品表（表3）", reportSearch), columnNames, getInorOutJoinGoods(reportSearch));
    }

    /**
     * 创建row cell
     *
     * @param rowNames
     * @param rowKeys
     * @param sheet
     */
    private void createRowCell(String[] rowNames,String[] rowKeys, Sheet sheet) {
        for (int i = 0; i < rowNames.length; i++) {
            Row rowN = sheet.createRow(i + 2);
            Cell cellN_0 = rowN.createCell(0);
            cellN_0.setCellValue(rowNames[i]);
            Cell cellN_1 = rowN.createCell(1);
            cellN_1.setCellValue(rowKeys[i]);
        }
    }

    /**
     * 如果报表是查询当前月的数据，则为报表名称及sheet添加截止日期
     *
     * @param sheetName
     * @param reportSearch
     * @return
     */
    private String changeSheetName(String sheetName, ReportSearch reportSearch) {
        DateTime now = DateTime.now();
        int year = reportSearch.getYear();
        int month = reportSearch.getMonth();
        String newName = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        if (year == now.getYear() && month == now.getMonthOfYear()) {
            newName = sheetName + "(截止到" + format.format(now.toDate()) + ")";
        } else {
            newName = sheetName;
        }
        return newName;
    }
}
