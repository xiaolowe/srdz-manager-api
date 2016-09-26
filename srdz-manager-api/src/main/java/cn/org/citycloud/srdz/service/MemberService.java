package cn.org.citycloud.srdz.service;

import cn.org.citycloud.srdz.bean.MemberLevelBean;
import cn.org.citycloud.srdz.bean.MemberSearch;

import java.lang.reflect.InvocationTargetException;

/**
 * 会员管理service接口.
 *
 * @author demon
 * @Date 2016/4/20 9:17
 */
public interface MemberService {

    /**
     * 获取会员列表
     *
     * @param memberSearch
     * @return
     */
    public Object getMemberList(MemberSearch memberSearch) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * 获取会员详情
     *
     * @param memberId
     * @return
     */
    public Object getMemberDetail(int memberId);

    /**
     * 启用会员
     *
     * @param memberId
     * @return
     */
    public Object enableMember(int memberId);

    /**
     * 禁用会员
     *
     * @param memberId
     * @return
     */
    public Object disableMember(int memberId);

    /**
     * 获取会员等级列表
     *
     * @return
     */
    public Object getMemberLevelList();

    /**
     * 添加会员等级
     *
     * @param memberLevelBean
     * @return
     */
    public Object addMemberLevel(MemberLevelBean memberLevelBean);

    /**
     * 修改会员等级
     *
     * @param memberLevelBean
     * @param memberLevelId
     * @return
     */
    public Object updateMemberLevel(MemberLevelBean memberLevelBean, int memberLevelId);
}
