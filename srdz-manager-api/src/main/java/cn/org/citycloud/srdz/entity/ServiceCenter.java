package cn.org.citycloud.srdz.entity;

import cn.org.citycloud.srdz.annotation.FieldName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the service_center database table.
 * 
 */
@Entity
@Table(name="service_center")
@NamedQuery(name="ServiceCenter.findAll", query="SELECT s FROM ServiceCenter s")
public class ServiceCenter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="service_center_id")
	private int serviceCenterId;

	@FieldName(name = "开户行")
	@Column(name="account_bank")
	private String accountBank;

	@FieldName(name = "银行")
	@Column(name="bank_name")
	private String bankName;

	@FieldName(name = "营业执照")
	@Column(name="business_license")
	private String businessLicense;

	@FieldName(name = "银行卡号")
	@Column(name="card_no")
	private String cardNo;

	@FieldName(name = "联系人")
	@Column(name="contact_name")
	private String contactName;

	@FieldName(name = "联系电话")
	@Column(name="contact_phone")
	private String contactPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@FieldName(name = "组织结构代码")
	@Column(name="org_code")
	private String orgCode;

	@Column(name="region_prov")
	private int regionProv;

	@Column(name="region_city")
	private int regionCity;

	@Column(name="region_area")
	private int regionArea;

	@Column(name="region_prov_name")
	private String regionProvName;

	@Column(name="region_city_name")
	private String regionCityName;

	@Column(name="region_area_name")
	private String regionAreaName;

	@FieldName(name = "法人身份证照片")
	@Column(name="owner_card_image")
	private String ownerCardImage;

	@FieldName(name = "法人身份证")
	@Column(name="owner_identity")
	private String ownerIdentity;

	@FieldName(name = "服务中心名称")
	@Column(name="service_center_name")
	private String serviceCenterName;

	@FieldName(name = "法人姓名")
	@Column(name="service_center_owner")
	private String serviceCenterOwner;

	private int status;

	@FieldName(name = "税务登记证")
	@Column(name="tax_register_certifacate")
	private String taxRegisterCertifacate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@FieldName(name = "持卡人")
	@Column(name="card_owner")
	private String cardOwner;

	@FieldName(name = "企业名称")
	@Column(name="company_name")
	private String companyName;

	@Column(name="service_center_type")
	private int serviceCenterType;

	//bi-directional many-to-one association to SupplierLevel
	@ManyToOne
	@JoinColumn(name="service_center_level_id")
	private ServiceCenterLevel serviceCenterLevel;

	public ServiceCenter() {
	}

	public int getServiceCenterId() {
		return this.serviceCenterId;
	}

	public void setServiceCenterId(int serviceCenterId) {
		this.serviceCenterId = serviceCenterId;
	}

	public String getAccountBank() {
		return this.accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBusinessLicense() {
		return this.businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOwnerCardImage() {
		return this.ownerCardImage;
	}

	public void setOwnerCardImage(String ownerCardImage) {
		this.ownerCardImage = ownerCardImage;
	}

	public String getOwnerIdentity() {
		return this.ownerIdentity;
	}

	public void setOwnerIdentity(String ownerIdentity) {
		this.ownerIdentity = ownerIdentity;
	}

	public String getServiceCenterName() {
		return this.serviceCenterName;
	}

	public void setServiceCenterName(String serviceCenterName) {
		this.serviceCenterName = serviceCenterName;
	}

	public String getServiceCenterOwner() {
		return this.serviceCenterOwner;
	}

	public void setServiceCenterOwner(String serviceCenterOwner) {
		this.serviceCenterOwner = serviceCenterOwner;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTaxRegisterCertifacate() {
		return this.taxRegisterCertifacate;
	}

	public void setTaxRegisterCertifacate(String taxRegisterCertifacate) {
		this.taxRegisterCertifacate = taxRegisterCertifacate;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public ServiceCenterLevel getServiceCenterLevel() {
		return serviceCenterLevel;
	}

	public void setServiceCenterLevel(ServiceCenterLevel serviceCenterLevel) {
		this.serviceCenterLevel = serviceCenterLevel;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getServiceCenterType() {
		return serviceCenterType;
	}

	public void setServiceCenterType(int serviceCenterType) {
		this.serviceCenterType = serviceCenterType;
	}

	public int getRegionProv() {
		return regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public int getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public int getRegionArea() {
		return regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

	public String getRegionProvName() {
		return regionProvName;
	}

	public void setRegionProvName(String regionProvName) {
		this.regionProvName = regionProvName;
	}

	public String getRegionCityName() {
		return regionCityName;
	}

	public void setRegionCityName(String regionCityName) {
		this.regionCityName = regionCityName;
	}

	public String getRegionAreaName() {
		return regionAreaName;
	}

	public void setRegionAreaName(String regionAreaName) {
		this.regionAreaName = regionAreaName;
	}
}