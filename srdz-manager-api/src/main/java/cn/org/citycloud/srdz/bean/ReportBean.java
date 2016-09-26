package cn.org.citycloud.srdz.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/22 14:44
 */
public class ReportBean {
    private Date date;

    private BigInteger inReceiptsCount;

    private BigDecimal inMoney;

    private BigInteger outReceiptsCount;

    private BigDecimal outMoney;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigInteger getInReceiptsCount() {
        return inReceiptsCount;
    }

    public void setInReceiptsCount(BigInteger inReceiptsCount) {
        this.inReceiptsCount = inReceiptsCount;
    }

    public BigDecimal getInMoney() {
        return inMoney;
    }

    public void setInMoney(BigDecimal inMoney) {
        this.inMoney = inMoney;
    }

    public BigInteger getOutReceiptsCount() {
        return outReceiptsCount;
    }

    public void setOutReceiptsCount(BigInteger outReceiptsCount) {
        this.outReceiptsCount = outReceiptsCount;
    }

    public BigDecimal getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(BigDecimal outMoney) {
        this.outMoney = outMoney;
    }
}
