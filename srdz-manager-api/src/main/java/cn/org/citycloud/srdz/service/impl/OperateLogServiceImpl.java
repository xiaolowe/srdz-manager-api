package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.bean.OperateLogSearch;
import cn.org.citycloud.srdz.entity.OperateLog;
import cn.org.citycloud.srdz.repository.OperateLogDao;
import cn.org.citycloud.srdz.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/14 17:05
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogDao logDao;

    @Override
    public Object getLogList(OperateLogSearch search) {
        Specification<OperateLog> spec = new Specification<OperateLog>() {
            @Override
            public Predicate toPredicate(Root<OperateLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (search.getBusCode() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("busCode"), search.getBusCode()));
                }
                if (search.getBusId() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("busId"), search.getBusId()));
                }
                return query.where(predicate).getRestriction();
            }
        };
        return logDao.findAll(spec);
    }
}
