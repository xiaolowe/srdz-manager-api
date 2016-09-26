package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 18:02
 */
public class AccountBean {

    @ApiModelProperty(value="用户名",required=true)
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty(value="真实姓名",required=true)
    @NotBlank(message = "真实姓名不能为空")
    private String userTruename;

    @ApiModelProperty(value="手机号码",required=true)
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "0?(13|14|15|18)[0-9]{9}", message = "手机号码格式不正确")
    private String phone;

    private int roleCode;

    private int userStatus;

    private String userEmail;

    private String userAvatar;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTruename() {
        return userTruename;
    }

    public void setUserTruename(String userTruename) {
        this.userTruename = userTruename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
