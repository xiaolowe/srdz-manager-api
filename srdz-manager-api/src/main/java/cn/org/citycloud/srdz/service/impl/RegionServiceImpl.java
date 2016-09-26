package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.entity.RegionInfo;
import cn.org.citycloud.srdz.repository.RegionInfoDao;
import cn.org.citycloud.srdz.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/31 14:43
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionInfoDao regionInfoDao;

    @Override
    public List<RegionInfo> getRegionProvince() {
        return regionInfoDao.findByRegionLevel(1);		//省默认类型是 1
    }

    @Override
    public List<RegionInfo> getRegionCity(Integer regionCode) {
        return regionInfoDao.findCityOrAreaRegionCode(regionCode, 2);
    }

    @Override
    public List<RegionInfo> getRegionArea(Integer regionCode) {
        return regionInfoDao.findCityOrAreaRegionCode(regionCode, 3);
    }
}
