package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/15 17:02
 */
public class SupplierLevelBean {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="平台分成比例",required=true)
    @NotNull(message="平台分成比例不能为空")
    @Min(value = 0, message = "平台分成比例必须大于等于0")
    @Max(value = 100, message = "平台分成比例必须小于等于100")
    private BigDecimal platformRates;

    @ApiModelProperty(value="供应商等级",required=true)
    @NotBlank(message="供应商等级名称不能为空")
    private String supplierLevelName;


    public BigDecimal getPlatformRates() {
        return this.platformRates;
    }

    public void setPlatformRates(BigDecimal platformRates) {
        this.platformRates = platformRates;
    }

    public String getSupplierLevelName() {
        return this.supplierLevelName;
    }

    public void setSupplierLevelName(String supplierLevelName) {
        this.supplierLevelName = supplierLevelName;
    }

}
