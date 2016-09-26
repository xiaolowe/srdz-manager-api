package cn.org.citycloud.srdz.handler;

import cn.org.citycloud.srdz.annotation.OperationLog;
import cn.org.citycloud.srdz.bean.BeanCompareResultBean;
import cn.org.citycloud.srdz.bean.CashBean;
import cn.org.citycloud.srdz.bean.UserToken;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.*;
import cn.org.citycloud.srdz.repository.OperateLogDao;
import cn.org.citycloud.srdz.utils.BeanUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 日志注解处理类.
 *
 * @author demon
 * @Date 2016/5/12 15:28
 */
@Aspect
@Component
public class OperationLogHandler {

    @Autowired
    private OperateLogDao operateLogDao;

    @AfterReturning(pointcut = "@annotation(operationLog)", returning = "result")
    public void handle(JoinPoint joinPoint, OperationLog operationLog, Object result) throws Throwable {
        Object[] args = joinPoint.getArgs(); // 参数
        Object returnObj = result; // 返回结果

        OperateLog log = new OperateLog();
        log.setBusCode(operationLog.busCode());
        // 判断参数的长度，一共有两种长度，1和2，长度为1分为添加与审核的操作(通过判断参数类型可以区分)
        // 长度为2时，都是更新的操作(驳回之类的)
        if (args.length == 1) {
            if (args[0] instanceof Integer) {
                log.setBusId((Integer)args[0]);
                log.setOperation(operationLog.operation());
            } else { // 添加操作
                log.setBusId(getBusId(returnObj));
                log.setOperation(operationLog.operation());
            }
        } else if (args.length == 2){
            for (Object obj : args) {
                if (obj instanceof Integer) {
                    log.setBusId((Integer) obj);
                } else {
                    if (obj instanceof String || obj instanceof UserToken) {
                        log.setOperation(operationLog.operation());
                    } else if (obj instanceof CashBean) {
                        log.setBusId(((CashBean) obj).getCashId());
                    } else {
                        Map<String, BeanCompareResultBean> updatedFields = BeanUtil.compareObj(returnObj, obj);
                        log.setOperation(operationLog.operation() + "，修改内容为：" + updatedFields.keySet());
                    }
                }
            }
        } else {
            for (Object obj : args) {
                if (obj instanceof Integer) {
                    log.setBusId((Integer) obj);
                }
                log.setOperation(operationLog.operation());
            }
        }
        log.setCreateTime(new Date());
        log.setOperator(Constants.USER_TOKEN.getUserName());
        operateLogDao.save(log);
    }

    /**
     * 获取业务数据id
     *
     * @param obj
     * @return
     */
    public int getBusId(Object obj) {
        int id = 0;
        if (obj instanceof Supplier) {
            id = ((Supplier) obj).getSupplierId();
        } else if (obj instanceof  SupplierLevel) {
            id = ((SupplierLevel) obj).getSupplierLevelId();
        } else if (obj instanceof ServiceCenter) {
            id = ((ServiceCenter) obj).getServiceCenterId();
        } else if (obj instanceof ServiceCenterLevel) {
            id = ((ServiceCenterLevel) obj).getServiceCenterLevelId();
        } else if (obj instanceof SalesMember) {
            id = ((SalesMember) obj).getMemberId();
        } else if (obj instanceof Member) {
            id = ((Member) obj).getMemberId();
        } else if (obj instanceof Cash) {
            id = ((Cash) obj).getCashId();
        }
        return id;
    }
}
