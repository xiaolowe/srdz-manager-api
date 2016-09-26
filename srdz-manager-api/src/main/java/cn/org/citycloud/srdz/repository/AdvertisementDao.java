package cn.org.citycloud.srdz.repository;

import cn.org.citycloud.srdz.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * 广告管理楼层banner dao接口.
 *
 * @author demon
 * @Date 2016/4/25 14:19
 */
public interface AdvertisementDao extends JpaRepository<Advertisement, Integer>, JpaSpecificationExecutor<Advertisement> {
}
