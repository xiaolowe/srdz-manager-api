package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/21 11:20
 */
public class CashBean {
    private int cashId;

    @ApiModelProperty(value="打款凭证",required=true)
    @NotBlank(message="打款凭证不能为空")
    private String payingCertificate;

    public int getCashId() {
        return cashId;
    }

    public void setCashId(int cashId) {
        this.cashId = cashId;
    }

    public String getPayingCertificate() {
        return payingCertificate;
    }

    public void setPayingCertificate(String payingCertificate) {
        this.payingCertificate = payingCertificate;
    }
}
