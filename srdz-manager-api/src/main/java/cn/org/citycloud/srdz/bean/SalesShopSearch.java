package cn.org.citycloud.srdz.bean;

import cn.org.citycloud.srdz.bean.page.Page;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/19 16:41
 */
public class SalesShopSearch extends Page {
    private int status = -1;

    private String salesShopName;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSalesShopName() {
        return salesShopName;
    }

    public void setSalesShopName(String salesShopName) {
        this.salesShopName = salesShopName;
    }
}
