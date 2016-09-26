package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务收入dao接口.
 *
 * @author demon
 * @Date 2016/4/21 10:51
 */
public interface PayInfoDao extends JpaRepository<PayInfo, Integer>, JpaSpecificationExecutor<PayInfo> {
    /**
     * 根据日期获取收入总额
     *
     * @param start
     * @param end
     * @return
     */
    @Query(value = "select sum(a.payMoney) from PayInfo a where a.payTime >= ?1 and a.payTime <= ?2")
    BigDecimal todayTrade(Date start, Date end);
}
