package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.OperateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/13 9:55
 */
public interface OperateLogDao extends JpaRepository<OperateLog, Integer>, JpaSpecificationExecutor<OperateLog> {
}
