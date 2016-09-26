package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the member database table.
 */
@Entity
@NamedQuery(name = "Member.findAll", query = "SELECT m FROM Member m")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int memberId;

    @Column(name = "member_name")
    private String memberName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "member_avatar")
    private String memberAvatar;

    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "member_login_ip")
    private String memberLoginIp;

    @Column(name = "member_login_num")
    private int memberLoginNum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "member_login_time")
    private Date memberLoginTime;

    @Column(name = "member_old_login_ip")
    private String memberOldLoginIp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "member_old_login_time")
    private Date memberOldLoginTime;

    @Column(name = "member_phone")
    private String memberPhone;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "member_sex")
    private int memberSex;

    @Column(name = "member_status")
    private int memberStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "member_time")
    private Date memberTime;

    @Column(name = "member_truename")
    private String memberTruename;

    @Column(name = "region_area_name")
    private String regionAreaName;

    @Column(name = "region_city")
    private int regionCity;

    @Column(name = "region_city_name")
    private String regionCityName;

    @Column(name = "region_prov")
    private int regionProv;

    @Column(name = "region_prov_name")
    private String regionProvName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "member_level_id")
    private int memberLevelId;

    @Column(name = "member_growth")
    private int memberGrowth;

    public Member() {
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberLoginIp() {
        return memberLoginIp;
    }

    public void setMemberLoginIp(String memberLoginIp) {
        this.memberLoginIp = memberLoginIp;
    }

    public int getMemberLoginNum() {
        return memberLoginNum;
    }

    public void setMemberLoginNum(int memberLoginNum) {
        this.memberLoginNum = memberLoginNum;
    }

    public Date getMemberLoginTime() {
        return memberLoginTime;
    }

    public void setMemberLoginTime(Date memberLoginTime) {
        this.memberLoginTime = memberLoginTime;
    }

    public String getMemberOldLoginIp() {
        return memberOldLoginIp;
    }

    public void setMemberOldLoginIp(String memberOldLoginIp) {
        this.memberOldLoginIp = memberOldLoginIp;
    }

    public Date getMemberOldLoginTime() {
        return memberOldLoginTime;
    }

    public void setMemberOldLoginTime(Date memberOldLoginTime) {
        this.memberOldLoginTime = memberOldLoginTime;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public int getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(int memberSex) {
        this.memberSex = memberSex;
    }

    public int getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(int memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Date getMemberTime() {
        return memberTime;
    }

    public void setMemberTime(Date memberTime) {
        this.memberTime = memberTime;
    }

    public String getMemberTruename() {
        return memberTruename;
    }

    public void setMemberTruename(String memberTruename) {
        this.memberTruename = memberTruename;
    }

    public String getRegionAreaName() {
        return regionAreaName;
    }

    public void setRegionAreaName(String regionAreaName) {
        this.regionAreaName = regionAreaName;
    }

    public int getRegionCity() {
        return regionCity;
    }

    public void setRegionCity(int regionCity) {
        this.regionCity = regionCity;
    }

    public String getRegionCityName() {
        return regionCityName;
    }

    public void setRegionCityName(String regionCityName) {
        this.regionCityName = regionCityName;
    }

    public int getRegionProv() {
        return regionProv;
    }

    public void setRegionProv(int regionProv) {
        this.regionProv = regionProv;
    }

    public String getRegionProvName() {
        return regionProvName;
    }

    public void setRegionProvName(String regionProvName) {
        this.regionProvName = regionProvName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(int memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public int getMemberGrowth() {
        return memberGrowth;
    }

    public void setMemberGrowth(int memberGrowth) {
        this.memberGrowth = memberGrowth;
    }
}