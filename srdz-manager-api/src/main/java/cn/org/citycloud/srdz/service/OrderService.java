package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.OrderSearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 订单管理service接口.
 *
 * @author demon
 * @Date 2016/4/25 10:35
 */
public interface OrderService {

    /**
     * 获取订单列表
     *
     * @param orderSearch
     * @return
     */
    public Object getOrderList(OrderSearch orderSearch);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    public Object getOrderDetail(int orderId);

    /**
     * 下载报表
     *
     * @param orderSearch
     * @param os
     * @return
     */
    public void downOrderExcel(OrderSearch orderSearch, ByteArrayOutputStream os) throws IOException;

}
