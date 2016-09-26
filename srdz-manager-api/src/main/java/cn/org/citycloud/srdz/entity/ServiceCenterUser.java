package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the service_center_user database table.
 * 
 */
@Entity
@Table(name="service_center_user")
@NamedQuery(name="ServiceCenterUser.findAll", query="SELECT s FROM ServiceCenterUser s")
public class ServiceCenterUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="service_center_user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int serviceCenterUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String phone;

	@Column(name="role_code")
	private int roleCode;

	@Column(name="service_center_id")
	private int serviceCenterId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="user_avatar")
	private String userAvatar;

	@Column(name="user_email")
	private String userEmail;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_pwd")
	private String userPwd;

	@Column(name="user_status")
	private int userStatus;

	@Column(name="user_truename")
	private String userTruename;

	public ServiceCenterUser() {
	}

	public int getServiceCenterUserId() {
		return this.serviceCenterUserId;
	}

	public void setServiceCenterUserId(int serviceCenterUserId) {
		this.serviceCenterUserId = serviceCenterUserId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(int roleCode) {
		this.roleCode = roleCode;
	}

	public int getServiceCenterId() {
		return this.serviceCenterId;
	}

	public void setServiceCenterId(int serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserAvatar() {
		return this.userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public int getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserTruename() {
		return this.userTruename;
	}

	public void setUserTruename(String userTruename) {
		this.userTruename = userTruename;
	}

}