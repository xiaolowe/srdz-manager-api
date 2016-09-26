package cn.org.citycloud.srdz.task;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.Cash;
import cn.org.citycloud.srdz.repository.CashDao;
import cn.org.citycloud.srdz.utils.DateUtils;
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
 * 退款申请流转，条件：供应商5日后没有处理则流转至平台财务.
 *
 * @author demon
 * @Date 2016/5/5 16:25
 */
public class RefundApplyTransfer {

    private final static Logger logger = LoggerFactory.getLogger(RefundApplyTransfer.class);

    @Autowired
    private CashDao cashDao;

    public void transferApply() {
        logger.info("---------------退款申请流转定时任务开始执行");
        Iterator<Cash> iterator = getUnhandleList().iterator();
        while (iterator.hasNext()) {
            Cash cash = iterator.next();
            int daysDiff = DateUtils.dayDiffFromToday(cash.getApplyTime());
            if (daysDiff > 5) {
                cash.setConfirmStatus(Constants.CASH_UNAUDIT);
                cash.setUpdateTime(new Date());
                cashDao.save(cash);
                logger.info("退款申请流转，退款申请编号:{}", cash.getCashId());
            }
        }
        logger.info("---------------退款申请流转定时任务执行结束");
    }

    /**
     * 获取供应商没有处理的退款申请
     *
     * @return
     */
    private List<Cash> getUnhandleList() {
        Specification<Cash> spec = new Specification<Cash>() {
            @Override
            public Predicate toPredicate(Root<Cash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("confirmStatus"), Constants.CASH_SUPPLIER_UNAUDIT));
                predicate = cb.and(predicate, cb.equal(root.get("applyType"), Constants.DRAW_MONEY));
                return query.where(predicate).getRestriction();
            }
        };
        return cashDao.findAll(spec);
    }
}
