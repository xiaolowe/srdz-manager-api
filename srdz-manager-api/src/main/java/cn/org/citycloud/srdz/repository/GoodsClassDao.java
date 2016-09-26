package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.bean.GoodsClassDto;
import cn.org.citycloud.srdz.entity.GoodsClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/27 10:05
 */
public interface GoodsClassDao extends JpaRepository<GoodsClass, Integer>, JpaSpecificationExecutor<GoodsClass> {
    /**
     * 获取楼层广告list
     *
     * @return
     */
    @Query(value = "select new cn.org.citycloud.srdz.bean.GoodsClassDto(gc.goodsClassId, gc.goodsClassName, (select count(1) from Goods g where g.goodsClassId = gc.goodsClassId and g.goodsStatus = 3)," +
            " (select gb.bannerImage from GoodsClassBanner gb where gb.goodsClassId = gc.goodsClassId))" +
            " from GoodsClass gc where gc.parentId = 0")
    public List<GoodsClassDto> getGoodsClassList();

    public List<GoodsClass> findByParentId(int parentId);
}
