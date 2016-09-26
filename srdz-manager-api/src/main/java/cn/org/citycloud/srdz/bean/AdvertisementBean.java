package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 14:22
 */
public class AdvertisementBean {

    @ApiModelProperty(value = "广告名称", required = true)
    @NotBlank(message = "广告名称不能为空")
    private String advertisementName;

    @ApiModelProperty(value = "广告链接地址", required = true)
    @NotBlank(message = "广告链接地址不能为空")
    private String linkUrl;

    @ApiModelProperty(value = "开始时间", required = true)
    private Date startTime;

    @ApiModelProperty(value = "结束时间", required = true)
    private Date endTime;

    @ApiModelProperty(value = "状态（0 正常   1 关闭）", required = true)
    private int status;

    @ApiModelProperty(value = "广告Banner", required = true)
    @NotBlank(message = "广告Banner不能为空")
    private String bannerImage;

    public String getAdvertisementName() {
        return advertisementName;
    }

    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
