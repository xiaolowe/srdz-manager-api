package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.CashBean;
import cn.org.citycloud.srdz.bean.CashSearch;
import cn.org.citycloud.srdz.bean.PayInfoSearch;
import cn.org.citycloud.srdz.bean.UserToken;
import cn.org.citycloud.srdz.exception.BusinessErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 财务管理service接口.
 *
 * @author demon
 * @Date 2016/4/21 10:53
 */
public interface FinanceService {

    /**
     * 获取支出列表
     *
     * @param cashSearch
     * @return
     */
    public Object getCashList(CashSearch cashSearch);

    /**
     * 获取支出详细
     *
     * @param cashId
     * @return
     */
    public Object getCashDetail(int cashId);

    /**
     * 财务打款审核通过
     *
     * @param cashId
     * @return
     */
    public Object passedCash(int cashId, UserToken userToken) throws BusinessErrorException;

    /**
     * 财务打款审核打款驳回
     *
     * @param cashId
     * @param reason
     * @return
     */
    public Object rejectedCash(int cashId, String reason, UserToken userToken) throws IOException, BusinessErrorException;

    /**
     * 财务出纳打款确认
     *
     * @param cashBean
     * @return
     */
    public Object ensureCash(CashBean cashBean, UserToken userToken) throws IOException;

    /**
     * 获取收入列表
     *
     * @param payInfoSearch
     * @return
     */
    public Object getPayInfoList(PayInfoSearch payInfoSearch);

    /**
     * 获取收入详情
     *
     * @param payId
     * @return
     */
    public Object getPayInfoDetail(int payId);

    /**
     * 查询历史打款记录
     *
     * @param applyerId 申请人id
     * @return
     */
    public Object getHisCashList(int applyerId);

    /**
     * 下载支出报表
     *
     * @param cashSearch
     * @param os
     */
    public void downCashExcel(CashSearch cashSearch, ByteArrayOutputStream os) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException;

    /**
     * 下载收入报表
     *
     * @param payInfoSearch
     * @param os
     */
    public void downPayInfoExcel(PayInfoSearch payInfoSearch, ByteArrayOutputStream os) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException;
}
