package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.annotation.OperationLog;
import cn.org.citycloud.srdz.bean.AfterSaleBean;
import cn.org.citycloud.srdz.bean.AfterSaleSearch;
import cn.org.citycloud.srdz.bean.ServiceCenterBean;
import cn.org.citycloud.srdz.bean.ServiceCenterSearch;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.AfterSale;
import cn.org.citycloud.srdz.entity.ServiceCenter;
import cn.org.citycloud.srdz.entity.ServiceCenterLevel;
import cn.org.citycloud.srdz.entity.ServiceCenterUser;
import cn.org.citycloud.srdz.exception.BusinessErrorException;
import cn.org.citycloud.srdz.repository.AfterSaleDao;
import cn.org.citycloud.srdz.repository.ServiceCenterDao;
import cn.org.citycloud.srdz.repository.ServiceCenterUserDao;
import cn.org.citycloud.srdz.service.ServiceCenterService;
import cn.org.citycloud.srdz.utils.BeanUtil;
import cn.org.citycloud.srdz.utils.SmsUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.*;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/18 13:08
 */
@Service
public class ServiceCenterServiceImpl implements ServiceCenterService {

    @Autowired
    private ServiceCenterDao serviceCenterDao;

    @Autowired
    private AfterSaleDao afterSaleDao;

    @Autowired
    private ServiceCenterUserDao userDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Object getServiceCenterList(ServiceCenterSearch serviceCenterSearch) {
        int page = serviceCenterSearch.getPageNo();
        int size = serviceCenterSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<ServiceCenter> spec = new Specification<ServiceCenter>() {
            @Override
            public Predicate toPredicate(Root<ServiceCenter> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (serviceCenterSearch.getServiceCenterLevelId() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("serviceCenterLevel").get("serviceCenterLevelId"), serviceCenterSearch.getServiceCenterLevelId()));
                }
                if (serviceCenterSearch.getStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("status"), serviceCenterSearch.getStatus()));
                }
                if (!StringUtils.isEmpty(serviceCenterSearch.getServiceCenterName())) {
                    predicate = cb.and(predicate, cb.like(root.get("serviceCenterName"), "%" + serviceCenterSearch.getServiceCenterName() + "%"));
                }

                return query.where(predicate).getRestriction();
            }
        };
        return serviceCenterDao.findAll(spec, pageable);
    }

    @Override
    public Object getServiceCenterDetail(int serviceCenterId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ServiceCenter serviceCenter = serviceCenterDao.findOne(serviceCenterId);
        Map<String, Object> resultMap = BeanUtil.beanToMap(serviceCenter, ServiceCenter.class);
        // 如果服务中心的类型为合伙人
        if (serviceCenter.getServiceCenterType() == 1) {
            String sql = "select count(1) from supplier where service_center_id = " + serviceCenterId;
            Query query = em.createNativeQuery(sql);
            BigInteger obj = (BigInteger) query.getSingleResult();
            resultMap.put("relateSupplierNum", obj.intValue());

        }
        return resultMap;
    }

    @Transactional
    @OperationLog(operation = "添加服务中心", busCode = 3)
    @Override
    public Object addServiceCenter(ServiceCenterBean serviceCenterBean) throws BusinessErrorException {
        // 验证号码是否重复
        checkPhoneExist(serviceCenterBean.getContactPhone(), false, 0);
        ServiceCenter result = new ServiceCenter();
        BeanUtils.copyProperties(serviceCenterBean, result);
        ServiceCenterLevel serviceCenterLevel = new ServiceCenterLevel();
        serviceCenterLevel.setServiceCenterLevelId(serviceCenterBean.getServiceCenterLevelId());
        result.setCreateTime(new Date());
        result.setUpdateTime(new Date());
        result.setServiceCenterLevel(serviceCenterLevel);
        serviceCenterDao.save(result);
        // 新建服务中心用户
        String newPwd = SmsUtil.getRandomStr(false, 6);
        ServiceCenterUser user = new ServiceCenterUser();
        user.setUserName(result.getContactPhone());
        user.setUserPwd(newPwd);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setServiceCenterId(result.getServiceCenterId());
        user.setPhone(result.getContactPhone());
        user.setUserTruename(result.getContactName());
        user.setUserStatus(1);
        userDao.save(user);
        // 发送短信
        Map<String, String> data = new HashMap<>();
        data.put("userName", result.getContactPhone());
        data.put("pwd", newPwd);
        SmsUtil.sendSms(serviceCenterBean.getContactPhone(), SmsUtil.getMessageTpl(Constants.SMS_NEW_SERVICE_CENTER, data));
        return result;
    }

    @OperationLog(operation = "修改服务中心", busCode = 3)
    @Override
    public Object updateServiceCenter(ServiceCenterBean serviceCenterBean, int serviceCenterId) throws BusinessErrorException {
        // 验证号码是否重复
        checkPhoneExist(serviceCenterBean.getContactPhone(), true, serviceCenterId);
        ServiceCenter serviceCenter = serviceCenterDao.findOne(serviceCenterId);
        // 用于日志记录，获取修改前的bean，aop拦截获取修改前的bean跟修改后的bean进行日志记录
        ServiceCenter result = BeanUtil.getDeepCopy(serviceCenter);
        BeanUtils.copyProperties(serviceCenterBean, serviceCenter);
        ServiceCenterLevel serviceCenterLevel = new ServiceCenterLevel();
        if (serviceCenterBean.getServiceCenterLevelId() != -1) {
            serviceCenterLevel.setServiceCenterLevelId(serviceCenterBean.getServiceCenterLevelId());
            serviceCenter.setServiceCenterLevel(serviceCenterLevel);
        }
        serviceCenter.setUpdateTime(new Date());
        serviceCenterDao.save(serviceCenter);
        return result;
    }

    @Override
    public Object getAfterSaleList(AfterSaleSearch afterSaleSearch) {
        int page = afterSaleSearch.getPageNo();
        int size = afterSaleSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<AfterSale> spec = new Specification<AfterSale>() {
            @Override
            public Predicate toPredicate(Root<AfterSale> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (!StringUtils.isEmpty(afterSaleSearch.getMemberName())) {
                    predicate = cb.and(predicate, cb.like(root.get("memberName"), "%" + afterSaleSearch.getMemberName() + "%"));
                }
                if (!StringUtils.isEmpty(afterSaleSearch.getSupplierName())) {
                    predicate = cb.and(predicate, cb.like(root.get("supplier").get("supplierName"), "%" + afterSaleSearch.getSupplierName() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return afterSaleDao.findAll(spec, pageable);
    }

    @Override
    public Object getAfterSaleDetail(int afterSaleId) {
        return afterSaleDao.findOne(afterSaleId);
    }

    @Override
    public Object handledAfterSale(AfterSaleBean afterSaleBean, int afterSaleId) {
        AfterSale afterSale = afterSaleDao.findOne(afterSaleId);
        afterSale.setResult(afterSaleBean.getResult());
        afterSale.setUpdateDate(new Date());
        afterSale.setStatus(1);
        afterSaleDao.save(afterSale);
        return afterSale;
    }

    /**
     * 添加服务中心时，判断联系电话是否已经存在
     *
     * @param contactPhone
     * @param isUpdate
     * @param centerId
     * @return
     */
    private void checkPhoneExist(String contactPhone, boolean isUpdate, int centerId) throws BusinessErrorException {
        List<ServiceCenter> serviceCenterList = new ArrayList<>();
        if (!isUpdate) {
            serviceCenterList = serviceCenterDao.findByContactPhone(contactPhone);
        } else {
            Specification<ServiceCenter> spec = (root, query, cb) -> {
                Predicate predicate = cb.conjunction();
                predicate = cb.and(predicate, cb.equal(root.get("contactPhone"), contactPhone));
                predicate = cb.and(predicate, cb.notEqual(root.get("contactPhone"), serviceCenterDao.findOne(centerId).getContactPhone()));
                return query.where(predicate).getRestriction();
            };
            serviceCenterList = serviceCenterDao.findAll(spec);
        }
        if (serviceCenterList.size() > 0) {
            throw new BusinessErrorException("016", "联系电话已经存在");
        }
    }

    @Override
    public void resetPwd(int serviceId) {
        ServiceCenterUser serviceCenterUser = userDao.findByServiceCenterId(serviceId);
        serviceCenterUser.setUserPwd("888888");
        serviceCenterUser.setUpdateTime(new Date());
        userDao.save(serviceCenterUser);
    }
}
