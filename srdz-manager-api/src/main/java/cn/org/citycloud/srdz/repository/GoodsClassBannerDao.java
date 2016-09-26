package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.GoodsClassBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/18 15:09
 */
public interface GoodsClassBannerDao extends JpaRepository<GoodsClassBanner, Integer>, JpaSpecificationExecutor<GoodsClassBanner> {

    public List<GoodsClassBanner> findByGoodsClassId(int goodsClassId);
}
