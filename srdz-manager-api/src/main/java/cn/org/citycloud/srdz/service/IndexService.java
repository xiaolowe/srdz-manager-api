package cn.org.citycloud.srdz.service;

import java.math.BigDecimal;

/**
 * 首页service接口（数据展示）.
 *
 * @author demon
 * @Date 2016/4/26 14:27
 */
public interface IndexService {

    /**
     * 获取首页展示的统计数据
     *
     * @param roleCode
     * @return
     */
    public Object getStatisticsData(int roleCode);

    /**
     * 今日交易额
     *
     * @return
     */
    public BigDecimal getTodayTrades();

    /**
     * 今日订单数
     *
     * @return
     */
    public Long getTodayOrders();

    /**
     * 供应商账号申请审核数
     *
     * @return
     */
    public Long getSupplierAudit();

    /**
     * 分销商账号申请审核数
     *
     * @return
     */
    public Long getSalesMemberAudit();

    /**
     * 提款待审核数量
     *
     * @return
     */
    public Long getCashAudit();

    /**
     * 待打款数
     *
     * @return
     */
    public Long getCashEnsure();

    /**
     * 获取图表数据
     *
     * @return
     */
    public Object getChartsData();
}
