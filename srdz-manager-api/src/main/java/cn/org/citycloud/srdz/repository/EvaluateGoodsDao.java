package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.EvaluateGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 15:26
 */
public interface EvaluateGoodsDao extends JpaRepository<EvaluateGood, Integer>, JpaSpecificationExecutor<EvaluateGood> {
}
