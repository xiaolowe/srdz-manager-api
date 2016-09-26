package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/18 14:18
 */
public class ServiceCenterLevelBean {

    @ApiModelProperty(value="服务中心等级",required=true)
    @NotBlank(message="供应商等级名称不能为空")
    private String serviceCenterLevelName;

    @ApiModelProperty(value="服务中心分成比例",required=true)
    @Min(value = 0, message = "服务中心比例必须大于等于0")
    @Max(value = 100, message = "服务中心比例必须小于等于100")
    private BigDecimal serviceCenterRates;

    public String getServiceCenterLevelName() {
        return serviceCenterLevelName;
    }

    public void setServiceCenterLevelName(String serviceCenterLevelName) {
        this.serviceCenterLevelName = serviceCenterLevelName;
    }

    public BigDecimal getServiceCenterRates() {
        return serviceCenterRates;
    }

    public void setServiceCenterRates(BigDecimal serviceCenterRates) {
        this.serviceCenterRates = serviceCenterRates;
    }
}
