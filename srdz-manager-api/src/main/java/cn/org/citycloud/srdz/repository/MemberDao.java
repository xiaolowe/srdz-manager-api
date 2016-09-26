package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 会员管理dao接口.
 *
 * @author demon
 * @Date 2016/4/20 9:12
 */
public interface MemberDao extends JpaRepository<Member, Integer>, JpaSpecificationExecutor<Member> {
}
