package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.entity.RegionInfo;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/31 14:41
 */
public interface RegionService {

    /**
     * 获取省
     *
     * @return
     */
    List<RegionInfo> getRegionProvince();

    /**
     * 获取市
     *
     * @param regionCode
     * @return
     */
    List<RegionInfo> getRegionCity(Integer regionCode);

    /**
     * 获取县、区
     *
     * @param regionCode
     * @return
     */
    List<RegionInfo> getRegionArea(Integer regionCode);
}
