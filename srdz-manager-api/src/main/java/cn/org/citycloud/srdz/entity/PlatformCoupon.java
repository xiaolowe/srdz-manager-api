package cn.org.citycloud.srdz.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;


/**
 * The persistent class for the platform_coupon database table.
 */
@Entity
@Table(name = "platform_coupon")
@NamedQuery(name = "PlatformCoupon.findAll", query = "SELECT p FROM PlatformCoupon p")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class PlatformCoupon implements Serializable, Comparable<PlatformCoupon> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private int couponId;

    @Column(name = "coupon_condition")
    private BigDecimal couponCondition;

    @Column(name = "coupon_money")
    private BigDecimal couponMoney;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_number")
    private int couponNumber;

    @Column(name = "coupon_status")
    private int couponStatus;

    @Column(name = "coupon_symbol")
    private String couponSymbol;

    @Column(name = "everyday_get")
    private int everydayGet;

    @Column(name = "limit_get")
    private int limitGet;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effective_time")
    private Date effectiveTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_time")
    private Date expirationTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "send_count")
    private int sendCount;

    @Column(name = "userd_count")
    private int userdCount;

    @Column(name = "activity_id")
    private int activityId;

    public PlatformCoupon() {
    }

    public int getCouponId() {
        return this.couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public BigDecimal getCouponCondition() {
        return this.couponCondition;
    }

    public void setCouponCondition(BigDecimal couponCondition) {
        this.couponCondition = couponCondition;
    }

    public BigDecimal getCouponMoney() {
        return this.couponMoney;
    }

    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCouponName() {
        return this.couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponNumber() {
        return this.couponNumber;
    }

    public void setCouponNumber(int couponNumber) {
        this.couponNumber = couponNumber;
    }

    public int getCouponStatus() {
        return this.couponStatus;
    }

    public void setCouponStatus(int couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponSymbol() {
        return this.couponSymbol;
    }

    public void setCouponSymbol(String couponSymbol) {
        this.couponSymbol = couponSymbol;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEffectiveTime() {
        return this.effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public int getUserdCount() {
        return userdCount;
    }

    public void setUserdCount(int userdCount) {
        this.userdCount = userdCount;
    }

    public int getEverydayGet() {
        return everydayGet;
    }

    public void setEverydayGet(int everydayGet) {
        this.everydayGet = everydayGet;
    }

    public int getLimitGet() {
        return limitGet;
    }

    public void setLimitGet(int limitGet) {
        this.limitGet = limitGet;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Override
    public int compareTo(PlatformCoupon o) {
        return o.getCreateDate().compareTo(getCreateDate());
    }
}