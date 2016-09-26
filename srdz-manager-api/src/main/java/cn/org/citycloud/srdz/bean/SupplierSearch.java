package cn.org.citycloud.srdz.bean;

import cn.org.citycloud.srdz.bean.page.Page;

/**
 * 供应商列表查询类.
 *
 * @author Administrator
 * @Date 2016/4/15 14:13
 */
public class SupplierSearch extends Page {

    private int status = -1;

    private String supplierName;

    private int levelId = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }
}
