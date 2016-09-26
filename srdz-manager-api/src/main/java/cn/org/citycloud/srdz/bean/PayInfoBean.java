package cn.org.citycloud.srdz.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/23 16:54
 */
public class PayInfoBean {
    // payId", "payTime", "payMemberName", "payMemberPhone", "payMemberName", "companyName", "payMoney", "payStyle", "payStatus
    private int payId;

    private Date payTime;

    private String payMemberName;

    private String payMemberPhone;

    private String companyName;

    private BigDecimal payMoney;

    private String payStyle;

    private int payStatus;

    private String payStatusZh;

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayMemberName() {
        return payMemberName;
    }

    public void setPayMemberName(String payMemberName) {
        this.payMemberName = payMemberName;
    }

    public String getPayMemberPhone() {
        return payMemberPhone;
    }

    public void setPayMemberPhone(String payMemberPhone) {
        this.payMemberPhone = payMemberPhone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusZh() {
        return payStatusZh;
    }

    public void setPayStatusZh(String payStatusZh) {
        this.payStatusZh = payStatusZh;
    }
}
