package cn.org.citycloud.srdz.entity;

import cn.org.citycloud.srdz.annotation.FieldName;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the supplier database table.
 * 
 */
@Entity
@NamedQuery(name="Supplier.findAll", query="SELECT s FROM Supplier s")
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="supplier_id")
	private int supplierId;

	@FieldName(name = "开户行")
	@Column(name="account_bank")
	private String accountBank;

	@FieldName(name = "开户人")
	@Column(name="account_owner")
	private String accountOwner;

	@FieldName(name = "银行")
	@Column(name="bank_name")
	private String bankName;

	@Column(name="banner_image")
	private String bannerImage;

	@FieldName(name = "营业执照")
	@Column(name="business_license")
	private String businessLicense;

	@FieldName(name = "银行卡号")
	@Column(name="card_no")
	private String cardNo;

	@Column(name="card_owner")
	private String cardOwner;

	@FieldName(name = "企业名称")
	@Column(name="comany_name")
	private String comanyName;

	@FieldName(name = "申请人")
	@Column(name="contact_name")
	private String contactName;

	@FieldName(name = "联系电话")
	@Column(name="contact_phone")
	private String contactPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="logo_iamge")
	private String logoIamge;

	@FieldName(name = "组织结构代码")
	@Column(name="org_code")
	private String orgCode;

	@FieldName(name = "法人身份证照片")
	@Column(name="owner_card_image")
	private String ownerCardImage;

	@Column(name="owner_identity")
	private String ownerIdentity;

	private String password;

	private String phone;

	private int postcode;

	@FieldName(name = "区/县")
	@Column(name="region_area")
	private int regionArea;

	@Column(name="region_area_name")
	private String regionAreaName;

	@Column(name="region_city")
	private int regionCity;

	@Column(name="region_city_name")
	private String regionCityName;

	@Column(name="region_prov")
	private int regionProv;

	@Column(name="region_prov_name")
	private String regionProvName;

	@Column(name="sale_address_image")
	private String saleAddressImage;

	private int status;

	@FieldName(name = "供应商地址")
	@Column(name="supplier_address")
	private String supplierAddress;

	@FieldName(name = "供应商名称")
	@Column(name="supplier_name")
	private String supplierName;

	@Column(name="supplier_owner")
	private String supplierOwner;

	@Column(name="supplier_shop_name")
	private String supplierShopName;

	@FieldName(name = "税务登记证")
	@Column(name="tax_register_certifacate")
	private String taxRegisterCertifacate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	//bi-directional many-to-one association to SupplierLevel
	@ManyToOne
	@JoinColumn(name="supplier_level_id")
	private SupplierLevel supplierLevel;

	//bi-directional many-to-one association to ServiceCenter
	@ManyToOne
	@JoinColumn(name="service_center_id")
	private ServiceCenter serviceCenter;

	public Supplier() {
	}

	public int getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getAccountBank() {
		return this.accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountOwner() {
		return this.accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBannerImage() {
		return this.bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
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

	public String getCardOwner() {
		return this.cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getComanyName() {
		return this.comanyName;
	}

	public void setComanyName(String comanyName) {
		this.comanyName = comanyName;
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

	public String getLogoIamge() {
		return this.logoIamge;
	}

	public void setLogoIamge(String logoIamge) {
		this.logoIamge = logoIamge;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPostcode() {
		return this.postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public int getRegionArea() {
		return this.regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

	public String getRegionAreaName() {
		return this.regionAreaName;
	}

	public void setRegionAreaName(String regionAreaName) {
		this.regionAreaName = regionAreaName;
	}

	public int getRegionCity() {
		return this.regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public String getRegionCityName() {
		return this.regionCityName;
	}

	public void setRegionCityName(String regionCityName) {
		this.regionCityName = regionCityName;
	}

	public int getRegionProv() {
		return this.regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public String getRegionProvName() {
		return this.regionProvName;
	}

	public void setRegionProvName(String regionProvName) {
		this.regionProvName = regionProvName;
	}

	public String getSaleAddressImage() {
		return this.saleAddressImage;
	}

	public void setSaleAddressImage(String saleAddressImage) {
		this.saleAddressImage = saleAddressImage;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSupplierAddress() {
		return this.supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierOwner() {
		return this.supplierOwner;
	}

	public void setSupplierOwner(String supplierOwner) {
		this.supplierOwner = supplierOwner;
	}

	public String getSupplierShopName() {
		return this.supplierShopName;
	}

	public void setSupplierShopName(String supplierShopName) {
		this.supplierShopName = supplierShopName;
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

	public SupplierLevel getSupplierLevel() {
		return supplierLevel;
	}

	public void setSupplierLevel(SupplierLevel supplierLevel) {
		this.supplierLevel = supplierLevel;
	}

	public ServiceCenter getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(ServiceCenter serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
}