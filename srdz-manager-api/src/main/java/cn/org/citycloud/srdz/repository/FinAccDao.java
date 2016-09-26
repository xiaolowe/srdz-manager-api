package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.FinAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/13 9:31
 */
public interface FinAccDao extends JpaRepository<FinAcc, Integer>, JpaSpecificationExecutor<FinAcc> {
    List<FinAcc> findByAccountNoAndAccountType(int accountNo, int accountType);
}
