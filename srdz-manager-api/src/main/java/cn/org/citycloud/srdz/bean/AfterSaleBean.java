package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/29 14:31
 */
public class AfterSaleBean {
    @ApiModelProperty(value="处理结果",required=true)
    @NotBlank(message="处理结果为空")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
