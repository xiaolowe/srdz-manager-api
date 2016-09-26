package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/20 14:23
 */
public class ActivityBean {
    @ApiModelProperty(value="活动描述",required=true)
    @NotBlank(message="活动描述不能为空")
    private String activityDesc;

    @ApiModelProperty(value="活动名称",required=true)
    @NotBlank(message="活动名称不能为空")
    private String activityName;

    @ApiModelProperty(value="活动banner",required=true)
    @NotBlank(message="活动banner不能为空")
    private String bannerImage;

    @ApiModelProperty(value="结束时间，格式yyyy-MM-dd HH:MM:ss",required=true)
    @NotNull(message = "活动结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value="开始时间，格式yyyy-MM-dd HH:MM:ss",required=true)
    @NotNull(message = "活动开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value="状态（0 禁用   1 启用）",required=true)
    private int status;

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
