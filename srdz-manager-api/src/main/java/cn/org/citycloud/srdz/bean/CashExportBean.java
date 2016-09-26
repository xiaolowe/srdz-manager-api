package cn.org.citycloud.srdz.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用于报表导出的bean.
 *
 * @author demon
 * @Date 2016/5/24 9:08
 */
public class CashExportBean {
    // cashId", "applyTime", "applyType", "applyUserName", "applyPhone", "companyName", "applyMoney", "confirmStatus"
    private int cashId;

    private Date applyTime;

    private int applyType;

    private String applyUserName;

    private String applyPhone;

    private String companyName;

    private BigDecimal applyMoney;

    private int confirmStatus;

    private String applyTypeZh;

    private String confirmStatusZh;

    public int getCashId() {
        return cashId;
    }

    public void setCashId(int cashId) {
        this.cashId = cashId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public int getApplyType() {
        return applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyPhone() {
        return applyPhone;
    }

    public void setApplyPhone(String applyPhone) {
        this.applyPhone = applyPhone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(BigDecimal applyMoney) {
        this.applyMoney = applyMoney;
    }

    public int getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(int confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getApplyTypeZh() {
        return applyTypeZh;
    }

    public void setApplyTypeZh(String applyTypeZh) {
        this.applyTypeZh = applyTypeZh;
    }

    public String getConfirmStatusZh() {
        return confirmStatusZh;
    }

    public void setConfirmStatusZh(String confirmStatusZh) {
        this.confirmStatusZh = confirmStatusZh;
    }
}
