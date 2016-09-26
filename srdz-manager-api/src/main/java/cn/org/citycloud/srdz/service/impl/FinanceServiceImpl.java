package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.annotation.OperationLog;
import cn.org.citycloud.srdz.bean.*;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.*;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.repository.*;
import cn.org.citycloud.srdz.service.FinanceService;
import cn.org.citycloud.srdz.utils.BeanUtil;
import cn.org.citycloud.srdz.utils.DateUtils;
import cn.org.citycloud.srdz.utils.ExcelUtil;
import cn.org.citycloud.srdz.utils.SiteMsgUtils;
import cn.org.citycloud.srdz.websocket.WebSocketMsgsHandler;
import com.google.common.collect.ImmutableMap;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 财务管理service接口.
 *
 * @author demon
 * @Date 2016/4/21 11:23
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private CashDao cashDao;

    @Autowired
    private PayInfoDao payInfoDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private FinAccDao finAccDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private WebSocketMsgsHandler msgsHandler;

    private Logger logger = LoggerFactory.getLogger(FinanceServiceImpl.class);

    @Override
    public Object getCashList(CashSearch cashSearch) {
        int page = cashSearch.getPageNo();
        int size = cashSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "applyTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        return cashDao.findAll(getCashSpec(cashSearch), pageable);
    }

    @Override
    public Object getCashDetail(int cashId) {
        return cashDao.findOne(cashId);
    }

    @OperationLog(operation = "通过提款申请", busCode = 7)
    @Override
    public Object passedCash(int cashId, UserToken userToken) throws BusinessErrorException {
        Cash result = cashDao.findOne(cashId);
        if (result.getConfirmStatus() == Constants.CASH_AUDIT_FALSE) {
            throw new BusinessErrorException("020", "该申请已被批准");
        }
        if (result.getApplyType() == Constants.REFUND) {
            Order order = orderDao.findOne(result.getOrderId());
            order.setUpdateTime(new Date());
            // 修改订单退款状态为退款中
            order.setBackOrderStatus(Constants.ORDER_REFUNDING);
            orderDao.save(order);
        }
        result.setConfirmStatus(Constants.CASH_AUDIT_SUCCESS);
        result.setUpdateTime(new Date());
        result.setConfirmUserId(userToken.getUserId());
        result.setConfirmUserName(userToken.getUserName());
        result.setConfirmTime(new Date());
        cashDao.save(result);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @OperationLog(operation = "驳回提款申请", busCode = 7)
    @Override
    public Object rejectedCash(int cashId, String reason, UserToken userToken) throws IOException, BusinessErrorException {
        Cash result = cashDao.findOne(cashId);
        if (result.getConfirmStatus() == Constants.CASH_AUDIT_FALSE) {
            throw new BusinessErrorException("019", "该申请已被驳回");
        }
        if (result.getApplyType() == Constants.REFUND) {
            Order order = orderDao.findOne(result.getOrderId());
            order.setUpdateTime(new Date());
            // 修改订单退款状态为拒绝退款
            order.setBackOrderStatus(Constants.ORDER_REFUND_REJECTED);
            orderDao.save(order);
        }
        // 修改fin_acc表的余额
        if (result.getApplyType() == Constants.DRAW_MONEY) {
            returnMoney(result.getAccountType(), getAccountNo(result), result.getApplyMoney());
            logger.info("申请(id:{})被驳回，账户：{}，账户类型：{}", cashId, getAccountNo(result), result.getAccountType());
        }
        result.setConfirmStatus(Constants.CASH_AUDIT_FALSE);
        result.setUpdateTime(new Date());
        result.setConfirmUserId(userToken.getUserId());
        result.setConfirmUserName(userToken.getUserName());
        result.setConfirmTime(new Date());
        cashDao.save(result);
        // TODO 发送站内信给相应的申请人
        Message message = SiteMsgUtils.getMessage(result.getApplyUserName(), result.getApplyUserId(), cashId, Constants.CASH_REJECTED);
        message.setPlatform(SiteMsgUtils.getRsver(result.getApplyType()));
        messageDao.save(message);
        msgsHandler.sendMessageToUser(message.getReceiverId(), new TextMessage(message.getMessageContent()));
        return result;
    }

    @OperationLog(operation = "确认打款", busCode = 7)
    @Override
    public Object ensureCash(CashBean cashBean, UserToken userToken) throws IOException {
        Cash result = cashDao.findOne(cashBean.getCashId());
        if (result.getApplyType() == Constants.REFUND) {
            Order order = orderDao.findOne(result.getOrderId());
            order.setUpdateTime(new Date());
            // 修改订单退款状态为已退款
            order.setBackOrderStatus(Constants.ORDER_REFUNDED);
            orderDao.save(order);
        }
        result.setConfirmStatus(Constants.CASH_ALREADY_PAY);
        result.setPayingCertificate(cashBean.getPayingCertificate());
        result.setPayingUserId(userToken.getUserId());
        result.setPayingUserName(userToken.getUserName());
        result.setPayingTime(new Date());
        cashDao.save(result);
        // TODO 发送站内信给相应的申请人
        Message message = SiteMsgUtils.getMessage(result.getApplyUserName(), result.getApplyUserId(), cashBean.getCashId(), Constants.CASH_SURE);
        message.setPlatform(SiteMsgUtils.getRsver(result.getApplyType()));
        messageDao.save(message);
        msgsHandler.sendMessageToUser(message.getReceiverId(), new TextMessage(message.getMessageContent()));
        return result;
    }

    @Override
    public Object getPayInfoList(PayInfoSearch payInfoSearch) {
        int page = payInfoSearch.getPageNo();
        int size = payInfoSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "payTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        return payInfoDao.findAll(getPayInfoSpec(payInfoSearch), pageable);
    }

    @Override
    public Object getPayInfoDetail(int payId) {
        return payInfoDao.findOne(payId);
    }

    @Override
    public Object getHisCashList(int applyerId) {
        Specification<Cash> spec = new Specification<Cash>() {
            @Override
            public Predicate toPredicate(Root<Cash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("applyUserId"), applyerId));
                return query.where(predicate).getRestriction();
            }
        };
        return cashDao.findAll(spec);
    }

    @Override
    public void downCashExcel(CashSearch cashSearch, ByteArrayOutputStream os) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        String[] columnNames = {"支付单号", "申请时间", "支出类型", "申请人", "联系电话", "申请单位", "申请金额", "账务状态"};
        String[] keys = {"cashId", "applyTime", "applyTypeZh", "applyUserName", "applyPhone", "companyName", "applyMoney", "confirmStatusZh"};
        List<Cash> list = cashDao.findAll(getCashSpec(cashSearch));
        List<Map<String, Object>> excelRecord = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "支出报表");
        excelRecord.add(map);
        // '申请类型(1：平台；2：供应商  3：服务中心  4 ：分销商)',
        ImmutableMap<Integer, String> applyTypeMap = ImmutableMap.of(1, "平台提款", 2, "供应商提款", 3, "服务中心提款", 4, "分销商提款", 5, "退款");
        // 审核状态 ( 0 未审核  1 审核通过  2 已打款  3 已驳回 4 供应商未审核 5 供应商审核通过 6 供应商已驳回'
        ImmutableMap<Integer, String> statusMap = ImmutableMap.<Integer, String>builder().put(0, "未审核").put(1, "审核通过").
                put(2, "已打款").
                put(3, "已驳回").
                put(4, "供应商未审核").
                put(5, "供应商审核通过").
                put(6, "供应商审核已驳回").build();
        for (Cash cash : list) {
            CashExportBean cashExportBean = new CashExportBean();
            BeanUtils.copyProperties(cash, cashExportBean);
            if (cash.getApplyType() == Constants.REFUND) {
                cashExportBean.setApplyTypeZh(applyTypeMap.get(5));
            } else {
                cashExportBean.setApplyTypeZh(applyTypeMap.get(cash.getAccountType()));
            }
            cashExportBean.setConfirmStatusZh(statusMap.get(cashExportBean.getConfirmStatus()));
            excelRecord.add(BeanUtil.beanToMap(cashExportBean, CashExportBean.class, keys));
        }
        Workbook wb = ExcelUtil.createWorkBook(excelRecord, keys, columnNames);
        wb.write(os);
    }

    @Override
    public void downPayInfoExcel(PayInfoSearch payInfoSearch, ByteArrayOutputStream os) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        String[] columnNames = {"支付单号", "支付时间", "支付人", "联系电话", "付款单位", "收款单位", "支付金额", "支付方式", "支付状态"};
        String[] keys = {"payId", "payTime", "payMemberName", "payMemberPhone", "payMemberName", "companyName", "payMoney", "payStyle", "payStatusZh"};
        List<PayInfo> list = payInfoDao.findAll(getPayInfoSpec(payInfoSearch));
        List<Map<String, Object>> excelRecord = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "收入报表");
        excelRecord.add(map);
        ImmutableMap<String, String> payTypeMap = ImmutableMap.of("1", "银联", "2", "支付宝", "3", "微信");
        ImmutableMap<Integer, String> payStatusMap = ImmutableMap.of(1, "已支付", 0, "未支付");
        for (PayInfo payInfo : list) {
            PayInfoBean payInfoBean = new PayInfoBean();
            BeanUtils.copyProperties(payInfo, payInfoBean);
            payInfoBean.setPayStatusZh(payStatusMap.get(payInfoBean.getPayStatus()));
            payInfoBean.setPayStyle(payTypeMap.get(payInfoBean.getPayStyle()));
            excelRecord.add(BeanUtil.beanToMap(payInfoBean, PayInfoBean.class, keys));
        }
        Workbook wb = ExcelUtil.createWorkBook(excelRecord, keys, columnNames);
        wb.write(os);
    }

    /**
     * 组装cash表动态查询条件
     *
     * @param cashSearch
     * @return
     */
    private Specification<Cash> getCashSpec(CashSearch cashSearch) {
        Specification<Cash> spec = new Specification<Cash>() {
            @Override
            public Predicate toPredicate(Root<Cash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (cashSearch.getRoleCode() == Constants.ROLE_TELLER) {
                    predicate = cb.and(predicate, cb.in(root.get("confirmStatus")).value(Arrays.asList(Constants.CASH_AUDIT_SUCCESS, Constants.CASH_ALREADY_PAY)));
                }

                if (!StringUtils.isEmpty(cashSearch.getCashId())) {
                    predicate = cb.and(predicate, cb.like(root.get("cashId").as(String.class), "%" + cashSearch.getCashId() + "%"));
                }
                if (!StringUtils.isEmpty(cashSearch.getCompanyName())) {
                    predicate = cb.and(predicate, cb.like(root.get("companyName"), "%" + cashSearch.getCompanyName() + "%"));
                }
                if (cashSearch.getConfirmStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("confirmStatus"), cashSearch.getConfirmStatus()));
                }
                if (!StringUtils.isEmpty(cashSearch.getStime())) {
                    String stime = cashSearch.getStime() + " 00:00:00";
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createTime"), DateUtils.parse(stime, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(cashSearch.getEtime())) {
                    String etime = cashSearch.getEtime() + " 23:59:59";
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createTime"), DateUtils.parse(etime, DateUtils.DATE_PATTERN_1)));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return spec;
    }

    /**
     * 组装payInfo动态查询条件
     *
     * @param payInfoSearch
     * @return
     */
    private Specification<PayInfo> getPayInfoSpec(PayInfoSearch payInfoSearch) {
        Specification<PayInfo> spec = new Specification<PayInfo>() {
            @Override
            public Predicate toPredicate(Root<PayInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (!StringUtils.isEmpty(payInfoSearch.getStime())) {
                    String stime = payInfoSearch.getStime() + " 00:00:00";
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createTime"), DateUtils.parse(stime, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(payInfoSearch.getEtime())) {
                    String etime = payInfoSearch.getEtime() + " 23:59:59";
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createTime"), DateUtils.parse(etime, DateUtils.DATE_PATTERN_1)));
                }
                if (!StringUtils.isEmpty(payInfoSearch.getPayStyle())) {
                    predicate = cb.and(predicate, cb.equal(root.get("payStyle"), payInfoSearch.getPayStyle()));
                }
                if (payInfoSearch.getPayId() != -1) {
                    predicate = cb.and(predicate, cb.like(root.get("payId").as(String.class), "%" + payInfoSearch.getPayId() + "%"));
                }
                if (!StringUtils.isEmpty(payInfoSearch.getCompanyName())) {
                    predicate = cb.and(predicate, cb.like(root.get("companyName"), "%" + payInfoSearch.getCompanyName() + "%"));
                }
                if (!StringUtils.isEmpty(payInfoSearch.getPayMemberName())) {
                    predicate = cb.and(predicate, cb.like(root.get("payMemberName"), "%" + payInfoSearch.getPayMemberName() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return spec;
    }

    /**
     * 退还相应金额至提款账户
     *
     * @param busType
     * @param busId
     * @param applyMoney
     * @throws BusinessErrorException
     */
    private void returnMoney(int busType, int busId, BigDecimal applyMoney) throws BusinessErrorException {
        List<FinAcc> finAccs = finAccDao.findByAccountNoAndAccountType(busId, busType);
        if (finAccs == null || finAccs.size() == 0) {
            throw new BusinessErrorException("018", "退款失败，找不到相应的账号");
        } else {
            FinAcc finAcc = finAccs.get(0);
            BigDecimal balance = finAcc.getAccountBal();
            logger.info("修改账户的金额，返还供应商/服务中心后台减去的余额：{}, 减去供应商后台/服务中心增加的支出：{}", applyMoney, applyMoney);
            finAcc.setAccountBal(balance.add(applyMoney));
            finAcc.setAccountPay(finAcc.getAccountPay().subtract(applyMoney));
            finAcc.setUpdateTime(new Date());
            finAccDao.save(finAcc);
        }
    }

    /**
     * 获取提款id
     *
     * @param cash
     * @return
     */
    private int getAccountNo(Cash cash) {
        switch (cash.getAccountType()) {
            case Constants.CASH_TYPE_SUPPLIER:
                return cash.getSupplierId();
            case Constants.CASH_TYPE_SERVICE:
                return cash.getServiceCenterId();
            case Constants.CASH_TYPE_SALES:
                return cash.getSalesMemberId();
            default:
                return 0;
        }
    }
}
