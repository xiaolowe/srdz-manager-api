package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.annotation.OperationLog;
import cn.org.citycloud.srdz.bean.SupplierBean;
import cn.org.citycloud.srdz.bean.SupplierSearch;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.ServiceCenter;
import cn.org.citycloud.srdz.entity.Supplier;
import cn.org.citycloud.srdz.entity.SupplierLevel;
import cn.org.citycloud.srdz.entity.SupplierOther;
import cn.org.citycloud.srdz.repository.ServiceCenterDao;
import cn.org.citycloud.srdz.repository.SupplierDao;
import cn.org.citycloud.srdz.repository.SupplierLevelDao;
import cn.org.citycloud.srdz.repository.SupplierOtherDao;
import cn.org.citycloud.srdz.service.SupplierService;
import cn.org.citycloud.srdz.utils.BeanUtil;
import cn.org.citycloud.srdz.utils.SmsUtil;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/15 14:20
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private ServiceCenterDao centerDao;

    @Autowired
    private SupplierLevelDao levelDao;

    @Autowired
    private SupplierOtherDao supplierOtherDao;

    @Override
    public Object getAllSupplier(SupplierSearch supplierSearch) {
        Specification<SupplierOther> spec = new Specification<SupplierOther>() {
            @Override
            public Predicate toPredicate(Root<SupplierOther> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (!StringUtils.isEmpty(supplierSearch.getSupplierName())) {
                    predicate = cb.and(predicate, cb.like(root.get("supplierName"), "%" + supplierSearch.getSupplierName() + "%"));
                }
                predicate = cb.and(predicate, cb.equal(root.get("status"), 2));
                return query.where(predicate).getRestriction();
            }
        };
        return supplierOtherDao.findAll(spec);
    }

    @Override
    public Object getSupplierList(SupplierSearch supplierSearch) {
        int page = supplierSearch.getPageNo();
        int size = supplierSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<Supplier> spec = new Specification<Supplier>() {
            @Override
            public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (supplierSearch.getLevelId() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("supplierLevel").get("supplierLevelId"), supplierSearch.getLevelId()));
                }
                if (supplierSearch.getStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("status"), supplierSearch.getStatus()));
                }
                if (supplierSearch.getStatus() == -1) {
                    predicate = cb.and(predicate, cb.notEqual(root.get("status"), 0));
                }
                if (!StringUtils.isEmpty(supplierSearch.getSupplierName())) {
                    predicate = cb.and(predicate, cb.like(root.get("supplierName"), "%" + supplierSearch.getSupplierName() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return supplierDao.findAll(spec, pageable);
    }

    @Override
    public Object getSupplierDetail(int supplierId) {
        return supplierDao.findOne(supplierId);
    }

    private Supplier auditSupplierAccount(int supplierId, int status) {
        Supplier result = supplierDao.findOne(supplierId);
        result.setStatus(status);
        result.setUpdateTime(new Date());
        supplierDao.save(result);
        return result;
    }

    @OperationLog(operation = "驳回供应商申请", busCode = 1)
    @Override
    public Object rejectedSupplier(int supplierId, String rejectedReason) {
        Supplier result = auditSupplierAccount(supplierId, 3);
        // 发送短信
        Map<String, String> data = new HashMap<>();
        data.put("reason", rejectedReason);
        SmsUtil.sendSms(result.getPhone(), SmsUtil.getMessageTpl(Constants.SMS_REJECTED_SUPPLIER, data));
        return result;
    }

    @OperationLog(operation = "通过供应商申请", busCode = 1)
    @Override
    public Object passedSupplier(int supplierId) {
        Supplier result = auditSupplierAccount(supplierId, 2);
        return result;
    }

    @OperationLog(operation = "添加供应商", busCode = 1)
    @Override
    public Object addSupplier(SupplierBean supplierBean) {
        Supplier result = new Supplier();
        BeanUtils.copyProperties(supplierBean, result);
        ServiceCenter center = centerDao.findOne(supplierBean.getServiceCenterId());
        SupplierLevel supplierLevel = levelDao.findOne(supplierBean.getSupplierLevelId());
        result.setCreateTime(new Date());
        result.setUpdateTime(new Date());
        result.setServiceCenter(center);
        result.setSupplierLevel(supplierLevel);
        supplierDao.save(result);
        return result;
    }

    @OperationLog(operation = "修改供应商", busCode = 1)
    @Override
    public Object updateSupplier(SupplierBean supplierBean, int supplierId) {
        Supplier supplier = supplierDao.findOne(supplierId);
        // 用于日志记录，获取修改前的bean，aop拦截获取修改前的bean跟修改后的bean进行日志记录
        Supplier result = BeanUtil.getDeepCopy(supplier);
        BeanUtils.copyProperties(supplierBean, supplier);
        ServiceCenter center = centerDao.findOne(supplierBean.getServiceCenterId());
        SupplierLevel supplierLevel = levelDao.findOne(supplierBean.getSupplierLevelId());
        supplier.setUpdateTime(new Date());
        supplier.setServiceCenter(center);
        supplier.setSupplierLevel(supplierLevel);
        supplierDao.save(supplier);
        return result;
    }
}
