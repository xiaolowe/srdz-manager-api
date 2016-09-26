package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.annotation.OperationLog;
import cn.org.citycloud.srdz.bean.SalesMemberBean;
import cn.org.citycloud.srdz.bean.SalesMemberSearch;
import cn.org.citycloud.srdz.bean.SalesShopBean;
import cn.org.citycloud.srdz.bean.SalesShopSearch;
import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.SalesMember;
import cn.org.citycloud.srdz.entity.SalesShop;
import cn.org.citycloud.srdz.repository.SalesMemberDao;
import cn.org.citycloud.srdz.repository.SalesShopDao;
import cn.org.citycloud.srdz.service.SalesService;
import cn.org.citycloud.srdz.utils.BeanUtil;
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

/**
 * 分销管理service接口.
 *
 * @author demon
 * @Date 2016/4/19 16:46
 */
@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesMemberDao salesMemberDao;

    @Autowired
    private SalesShopDao salesShopDao;

    @Override
    public Object getSalesMemberList(SalesMemberSearch salesMemberSearch) {
        int page = salesMemberSearch.getPageNo();
        int size = salesMemberSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<SalesMember> spec = new Specification<SalesMember>() {
            @Override
            public Predicate toPredicate(Root<SalesMember> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (salesMemberSearch.getStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("status"), salesMemberSearch.getStatus()));
                }

                if (!StringUtils.isEmpty(salesMemberSearch.getSalesMemberName())) {
                    predicate = cb.and(predicate, cb.like(root.get("salesMemberName"), "%" + salesMemberSearch.getSalesMemberName() + "%"));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return salesMemberDao.findAll(spec, pageable);
    }

    @Override
    public Object getSalesMemberDetail(int salesMemberId) {
        return salesMemberDao.findOne(salesMemberId);
    }

    @OperationLog(operation = "修改分销会员", busCode = 5)
    @Override
    public Object updateSalesMember(SalesMemberBean salesMemberBean, int salesMemberId) {
        SalesMember salesMember = salesMemberDao.findOne(salesMemberId);
        // 用于日志记录，获取修改前的bean，aop拦截获取修改前的bean跟修改后的bean进行日志记录
        SalesMember result = BeanUtil.getDeepCopy(salesMember);
        BeanUtils.copyProperties(salesMemberBean, salesMember);
        salesMember.setUpdateTime(new Date());
        salesMemberDao.save(salesMember);
        return result;
    }

    @OperationLog(operation = "通过分销会员申请", busCode = 5)
    @Override
    public Object passedSalesMember(int salesMemberId) {
        return auditSalesMember(salesMemberId, 1);
    }

    @OperationLog(operation = "驳回分销会员申请", busCode = 5)
    @Override
    public Object rejectedSalesMember(int salesMemberId, String reason) {
        // TODO 发送邮件至相应的账号上 改为发站内信
        SalesMember result = salesMemberDao.findOne(salesMemberId);
        result.setStatus(2);
        result.setUpdateTime(new Date());
        salesMemberDao.save(result);
        return result;
    }

    @Override
    public Object getSalesShopList(SalesShopSearch salesShopSearch) {
        int page = salesShopSearch.getPageNo();
        int size = salesShopSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<SalesShop> spec = new Specification<SalesShop>() {
            @Override
            public Predicate toPredicate(Root<SalesShop> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (salesShopSearch.getStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("status"), salesShopSearch.getStatus()));
                }
                if (!StringUtils.isEmpty(salesShopSearch.getSalesShopName())) {
                    predicate = cb.and(predicate, cb.like(root.get("salesShopName"), salesShopSearch.getSalesShopName()));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return salesShopDao.findAll(spec, pageable);
    }

    @Override
    public Object getSalesShopDetail(int salesShopId) {
        return salesShopDao.findOne(salesShopId);
    }

    @Override
    public Object updateSalesShop(SalesShopBean salesShopBean, int salesShopId) {
        SalesShop salesShop = salesShopDao.findOne(salesShopId);
        BeanUtils.copyProperties(salesShopBean, salesShop);
        salesShop.setUpdateTime(new Date());
        salesShopDao.save(salesShop);
        return salesShop;
    }

    private Object auditSalesMember(int salesMemberId, int status) {
        SalesMember result = salesMemberDao.findOne(salesMemberId);
        result.setStatus(status);
        result.setUpdateTime(new Date());
        salesMemberDao.save(result);
        return result;
    }

    @Override
    public Object changeStatus(int memberId, int status) {
        return auditSalesMember(memberId, status);
    }
}
