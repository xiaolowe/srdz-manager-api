package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.annotation.OperationLog;
import cn.org.citycloud.srdz.bean.ServiceCenterLevelBean;
import cn.org.citycloud.srdz.entity.ServiceCenterLevel;
import cn.org.citycloud.srdz.repository.ServiceCenterLevelDao;
import cn.org.citycloud.srdz.service.ServiceCenterLevelService;
import cn.org.citycloud.srdz.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务中心service实现.
 *
 * @author demon
 * @Date 2016/4/18 14:21
 */
@Service
public class ServiceCenterLeveServiceImpl implements ServiceCenterLevelService {

    @Autowired
    private ServiceCenterLevelDao serviceCenterLevelDao;

    @Override
    public Object getServiceCenterLevelList() {
        return serviceCenterLevelDao.findAll();
    }

    @Override
    public Object getServiceCenterLevelDetail(int levelId) {
        return serviceCenterLevelDao.findOne(levelId);
    }

    @OperationLog(operation = "添加服务中心等级", busCode = 4)
    @Override
    public Object addServiceCenterLevel(ServiceCenterLevelBean serviceCenterLevelBean) {
        ServiceCenterLevel result = new ServiceCenterLevel();
        BeanUtils.copyProperties(serviceCenterLevelBean, result);
        serviceCenterLevelDao.save(result);
        return result;
    }

    @OperationLog(operation = "修改服务中心等级", busCode = 4)
    @Override
    public Object updateServiceCenterLevel(ServiceCenterLevelBean serviceCenterLevelBean, int serviceCenterlevelId) {
        ServiceCenterLevel serviceCenterLevel = serviceCenterLevelDao.findOne(serviceCenterlevelId);
        // 用于日志记录，获取修改前的bean，aop拦截获取修改前的bean跟修改后的bean进行日志记录
        ServiceCenterLevel result = BeanUtil.getDeepCopy(serviceCenterLevel);
        BeanUtil.copyProperties(serviceCenterLevel, result);
        BeanUtils.copyProperties(serviceCenterLevelBean, serviceCenterLevel);
        serviceCenterLevelDao.save(serviceCenterLevel);
        return result;
    }
}
