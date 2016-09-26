package cn.org.citycloud.srdz.bean;

import cn.org.citycloud.srdz.bean.page.Page;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/18 14:37
 */
public class ServiceCenterSearch extends Page {

    private String serviceCenterName;

    private int status = -1;

    private int serviceCenterLevelId = -1;

    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public void setServiceCenterName(String serviceCenterName) {
        this.serviceCenterName = serviceCenterName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getServiceCenterLevelId() {
        return serviceCenterLevelId;
    }

    public void setServiceCenterLevelId(int serviceCenterLevelId) {
        this.serviceCenterLevelId = serviceCenterLevelId;
    }
}
