package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/20 14:33
 */
public class PlatformCouponBean {

    @ApiModelProperty(value="活动Id",required=true)
    private int activityId;

    @ApiModelProperty(value="满减条件",required=true)
    @Min(value = 0, message = "满减条件必须大于等于0")
    private BigDecimal couponCondition;

    @ApiModelProperty(value="优惠金额",required=true)
    @Min(value = 0, message = "优惠金额必须大于等于0")
    private BigDecimal couponMoney;

    @ApiModelProperty(value="优惠券名称",required=true)
    @NotBlank(message="优惠券名称不能为空")
    private String couponName;

    @ApiModelProperty(value="每日限领",required=true)
//    @Min(value = 1, message = "每日限领必须大于等于1")
    private int everydayGet;

    @ApiModelProperty(value="每人限领",required=true)
    @Min(value = 1, message = "每人限领必须大于等于1")
    private int limitGet;

    @ApiModelProperty(value="优惠券数量",required=true)
    @Min(value = 1, message = "优惠券数量必须大于等于1")
    private int couponNumber;

    @ApiModelProperty(value="生效时间，日期格式yyyy-MM-dd HH:MM:ss",required=true)
    @NotNull(message = "生效时间不能为空")
    private Date effectiveTime;

    @ApiModelProperty(value="过期时间，日期格式yyyy-MM-dd HH:MM:ss",required=true)
    @NotNull(message = "过期时间不能为空")
    private Date expirationTime;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public BigDecimal getCouponCondition() {
        return couponCondition;
    }

    public void setCouponCondition(BigDecimal couponCondition) {
        this.couponCondition = couponCondition;
    }

    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(int couponNumber) {
        this.couponNumber = couponNumber;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
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
}
