package cn.org.citycloud.srdz.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/5/10 8:58
 */
public class InOutSumBean {
    private BigInteger inUnpayCount;

    private BigDecimal inUnpaySum;

    private BigInteger inPayedCount;

    private BigDecimal inPayedSum;

    private BigInteger outUnpayCount;

    private BigDecimal outUnpaySum;

    private BigInteger outPayedCount;

    private BigDecimal outPayedSum;

    public BigInteger getInUnpayCount() {
        return inUnpayCount;
    }

    public void setInUnpayCount(BigInteger inUnpayCount) {
        this.inUnpayCount = inUnpayCount;
    }

    public BigDecimal getInUnpaySum() {
        return inUnpaySum;
    }

    public void setInUnpaySum(BigDecimal inUnpaySum) {
        this.inUnpaySum = inUnpaySum;
    }

    public BigInteger getInPayedCount() {
        return inPayedCount;
    }

    public void setInPayedCount(BigInteger inPayedCount) {
        this.inPayedCount = inPayedCount;
    }

    public BigDecimal getInPayedSum() {
        return inPayedSum;
    }

    public void setInPayedSum(BigDecimal inPayedSum) {
        this.inPayedSum = inPayedSum;
    }

    public BigInteger getOutUnpayCount() {
        return outUnpayCount;
    }

    public void setOutUnpayCount(BigInteger outUnpayCount) {
        this.outUnpayCount = outUnpayCount;
    }

    public BigDecimal getOutUnpaySum() {
        return outUnpaySum;
    }

    public void setOutUnpaySum(BigDecimal outUnpaySum) {
        this.outUnpaySum = outUnpaySum;
    }

    public BigInteger getOutPayedCount() {
        return outPayedCount;
    }

    public void setOutPayedCount(BigInteger outPayedCount) {
        this.outPayedCount = outPayedCount;
    }

    public BigDecimal getOutPayedSum() {
        return outPayedSum;
    }

    public void setOutPayedSum(BigDecimal outPayedSum) {
        this.outPayedSum = outPayedSum;
    }
}
