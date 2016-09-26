package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/20 9:38
 */
public class MemberLevelBean {

    @ApiModelProperty(value="会员成长值",required=true)
    @Min(value = 0, message = "会员成长值应该大于等于0")
    private int growth;

    @ApiModelProperty(value="会员等级",required=true)
    @NotBlank(message="会员等级不能为空")
    private String memberLevel;

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }
}
