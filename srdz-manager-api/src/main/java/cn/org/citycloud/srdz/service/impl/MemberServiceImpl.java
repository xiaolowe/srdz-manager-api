package cn.org.citycloud.srdz.service.impl;

import cn.org.citycloud.srdz.annotation.OperationLog;
import cn.org.citycloud.srdz.bean.MemberLevelBean;
import cn.org.citycloud.srdz.bean.MemberSearch;
import cn.org.citycloud.srdz.entity.Member;
import cn.org.citycloud.srdz.entity.MemberLevel;
import cn.org.citycloud.srdz.repository.MemberDao;
import cn.org.citycloud.srdz.repository.MemberLevelDao;
import cn.org.citycloud.srdz.service.MemberService;
import cn.org.citycloud.srdz.utils.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会员管理service接口实现.
 *
 * @author demon
 * @Date 2016/4/20 9:41
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberLevelDao memberLevelDao;

    @Override
    public Object getMemberList(MemberSearch memberSearch) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int page = memberSearch.getPageNo();
        int size = memberSearch.getPageSize();
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page - 1, size, sort);
        Specification<Member> spec = new Specification<Member>() {
            @Override
            public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if(memberSearch.getMemberStatus() != -1) {
                    predicate = cb.and(predicate, cb.equal(root.get("memberStatus"), memberSearch.getMemberStatus()));
                }
                if(!StringUtils.isEmpty(memberSearch.getMemberName())) {
                    predicate = cb.and(predicate, cb.like(root.get("memberName"), "%" + memberSearch.getMemberName() + "%"));;
                }
                return query.where(predicate).getRestriction();
            }
        };
        Page<Member> resultPage = memberDao.findAll(spec, pageable);
        List<Member> resultList = resultPage.getContent();
        String[] attrs = {"memberId", "memberTime", "memberName", "memberPhone", "memberGrowth", "memberStatus"};
        List<Map<String, Object>> mapList = BeanUtil.beanListToMapList(resultList, Member.class, attrs);
        List<MemberLevel> memberLevels = memberLevelDao.findAll();
        for (MemberLevel memberLevel : memberLevels) {
            for (Map<String, Object> member : mapList) {
                if ((int)member.get("memberGrowth") >= memberLevel.getGrowth()) {
                    member.put("memberLevel", memberLevel.getMemberLevel());
                }
            }
        }
        Page<Map<String, Object>> transferPage = new PageImpl<>(mapList, pageable, resultPage.getTotalElements());
        return transferPage;
    }

    @Override
    public Object getMemberDetail(int memberId) {
        return memberDao.findOne(memberId);
    }

    @Override
    public Object getMemberLevelList() {
        return memberLevelDao.findAll();
    }

    @OperationLog(operation = "添加会员等级", busCode = 6)
    @Override
    public Object addMemberLevel(MemberLevelBean memberLevelBean) {
        MemberLevel result = new MemberLevel();
        BeanUtils.copyProperties(memberLevelBean, result);
        memberLevelDao.save(result);
        return result;
    }

    @OperationLog(operation = "修改会员等级", busCode = 6)
    @Override
    public Object updateMemberLevel(MemberLevelBean memberLevelBean, int memberLevelId) {
        MemberLevel memberLevel = memberLevelDao.findOne(memberLevelId);
        // 用于日志记录，获取修改前的bean，aop拦截获取修改前的bean跟修改后的bean进行日志记录
        MemberLevel result = BeanUtil.getDeepCopy(memberLevel);
        BeanUtils.copyProperties(memberLevelBean, memberLevel);
        memberLevelDao.save(memberLevel);
        return result;
    }

    @Override
    public Object enableMember(int memberId) {
        Member member = memberDao.findOne(memberId);
        member.setMemberStatus(1);
        member.setUpdateDate(new Date());
        return memberDao.save(member);
    }

    @Override
    public Object disableMember(int memberId) {
        Member member = memberDao.findOne(memberId);
        member.setMemberStatus(0);
        member.setUpdateDate(new Date());
        return memberDao.save(member);
    }
}
