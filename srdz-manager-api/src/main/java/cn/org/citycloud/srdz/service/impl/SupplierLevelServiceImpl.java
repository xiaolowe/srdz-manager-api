package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.annotation.OperationLog;
import cn.org.citycloud.srdz.bean.SupplierLevelBean;
import cn.org.citycloud.srdz.entity.SupplierLevel;
import cn.org.citycloud.srdz.repository.SupplierLevelDao;
import cn.org.citycloud.srdz.service.SupplierLevelService;
import cn.org.citycloud.srdz.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/15 17:01
 */
@Service
public class SupplierLevelServiceImpl implements SupplierLevelService {

    @Autowired
    private SupplierLevelDao supplierLevelDao;

    @Override
    public Object getSupplierLevelList() {
        return supplierLevelDao.findAll();
    }

    @Override
    public Object getSupplierLevelDetail(int levelId) {
        return supplierLevelDao.findOne(levelId);
    }

    @OperationLog(operation = "添加供应商等级", busCode = 2)
    @Override
    public Object addSupplierLevel(SupplierLevelBean supplierLevelBean) {
        SupplierLevel result = new SupplierLevel();
        BeanUtils.copyProperties(supplierLevelBean, result);
        supplierLevelDao.save(result);
        return result;
    }

    @OperationLog(operation = "修改供应商等级", busCode = 2)
    @Override
    public Object updateSupplierLevel(SupplierLevelBean supplierLevelBean, int supplierlevelId) {
        SupplierLevel supplierLevel = supplierLevelDao.findOne(supplierlevelId);
        // 用于日志记录，获取修改前的bean，aop拦截获取修改前的bean跟修改后的bean进行日志记录
        SupplierLevel result = BeanUtil.getDeepCopy(supplierLevel);
        BeanUtils.copyProperties(supplierLevelBean, supplierLevel);
        supplierLevelDao.save(supplierLevel);
        return result;
    }
}
