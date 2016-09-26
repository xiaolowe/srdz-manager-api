package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 财务支出dao接口.
 *
 * @author demon
 * @Date 2016/4/21 10:50
 */
public interface CashDao extends JpaRepository<Cash, Integer>, JpaSpecificationExecutor<Cash> {
}
