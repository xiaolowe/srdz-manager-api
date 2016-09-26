package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/19 16:13
 */
public class SalesMemberBean {

    @ApiModelProperty(value="开户行",required=true)
    @NotBlank(message = "开户行不能为空")
    private String accountBank;

    @ApiModelProperty(value="开户人",required=true)
    @NotBlank(message = "开户人不能为空")
    private String accountOwner;

    @ApiModelProperty(value="银行卡号",required=true)
    @NotBlank(message = "银行卡号不能为空")
    private String cardNo;

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
