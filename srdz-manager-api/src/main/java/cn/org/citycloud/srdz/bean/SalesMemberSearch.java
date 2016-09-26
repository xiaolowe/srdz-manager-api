package cn.org.citycloud.srdz.bean;

import cn.org.citycloud.srdz.bean.page.Page;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/19 16:02
 */
public class SalesMemberSearch extends Page {
    private int status = -1;

    private String salesMemberName;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSalesMemberName() {
        return salesMemberName;
    }

    public void setSalesMemberName(String salesMemberName) {
        this.salesMemberName = salesMemberName;
    }
}
