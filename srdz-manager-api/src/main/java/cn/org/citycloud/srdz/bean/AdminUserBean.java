package cn.org.citycloud.srdz.bean;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 17:40
 */
public class AdminUserBean {
    private String userName;

    private String roleCode;

    private String userTruename;

    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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
}
