package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 17:19
 */
public interface NoticeDao extends JpaRepository<Notice, Integer>, JpaSpecificationExecutor<Notice> {
}
