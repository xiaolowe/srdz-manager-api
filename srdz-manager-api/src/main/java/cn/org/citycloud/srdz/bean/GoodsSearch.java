package cn.org.citycloud.srdz.bean;

import cn.org.citycloud.srdz.bean.page.Page;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/20 16:33
 */
public class GoodsSearch extends Page {
    private int goodsClassId = -1;

    private int goodsVerify = -1;

    private int goodsStatus = -1;

    private String goodsName;

    private String supplierName;

    public int getGoodsClassId() {
        return goodsClassId;
    }

    public void setGoodsClassId(int goodsClassId) {
        this.goodsClassId = goodsClassId;
    }

    public int getGoodsVerify() {
        return goodsVerify;
    }

    public void setGoodsVerify(int goodsVerify) {
        this.goodsVerify = goodsVerify;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
