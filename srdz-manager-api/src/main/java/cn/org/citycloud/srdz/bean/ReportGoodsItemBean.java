package cn.org.citycloud.srdz.bean;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/22 14:45
 */
public class ReportGoodsItemBean {
    private int goodsId;

    private String goodsName;

    private BigInteger produceOrderCount;

    private BigInteger tradeOrderCount;

    private int outStockCount;

    private String standard;

    private BigDecimal paySum;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigInteger getProduceOrderCount() {
        return produceOrderCount;
    }

    public void setProduceOrderCount(BigInteger produceOrderCount) {
        this.produceOrderCount = produceOrderCount;
    }

    public BigInteger getTradeOrderCount() {
        return tradeOrderCount;
    }

    public void setTradeOrderCount(BigInteger tradeOrderCount) {
        this.tradeOrderCount = tradeOrderCount;
    }

    public int getOutStockCount() {
        return outStockCount;
    }

    public void setOutStockCount(int outStockCount) {
        this.outStockCount = outStockCount;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public BigDecimal getPaySum() {
        return paySum;
    }

    public void setPaySum(BigDecimal paySum) {
        this.paySum = paySum;
    }
}
