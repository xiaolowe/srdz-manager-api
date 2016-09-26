package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 内容管理dao接口.
 *
 * @author demon
 * @Date 2016/4/19 13:46
 */
public interface ContentDao extends JpaRepository<Content, Integer>, JpaSpecificationExecutor<Content> {
}
