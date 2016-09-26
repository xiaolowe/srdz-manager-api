package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.RegionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/31 14:39
 */
public interface RegionInfoDao extends JpaRepository<RegionInfo, Integer>, JpaSpecificationExecutor<RegionInfo> {
    /**
     * 按照行政等级查询region
     *
     * @param regionlevel
     * @return
     */
    List<RegionInfo> findByRegionLevel(int regionlevel);

    /**
     * 根据regionCode和regionLevel查询region
     *
     * @param regioncode
     * @param regionlevel
     * @return
     */
    @Query(value="select * from region_info a where a.region_code like ?1% and a.region_level = ?2", nativeQuery = true)
    List<RegionInfo> findCityOrAreaRegionCode(int regioncode, int regionlevel);
}
