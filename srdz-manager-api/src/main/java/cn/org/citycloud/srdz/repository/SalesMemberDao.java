package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.SalesMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 分销管理dao接口.
 *
 * @author demon
 * @Date 2016/4/19 16:00
 */
public interface SalesMemberDao extends JpaRepository<SalesMember, Integer>, JpaSpecificationExecutor<SalesMember> {
}
