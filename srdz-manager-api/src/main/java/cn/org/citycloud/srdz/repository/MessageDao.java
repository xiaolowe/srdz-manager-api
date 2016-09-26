package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/22 9:18
 */
public interface MessageDao extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {
}
