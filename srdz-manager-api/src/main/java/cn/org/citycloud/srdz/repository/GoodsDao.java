package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 15:18
 */
public interface GoodsDao extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {
}
