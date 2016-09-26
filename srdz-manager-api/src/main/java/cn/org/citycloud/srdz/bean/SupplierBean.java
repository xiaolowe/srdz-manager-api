package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/15 16:33
 */
public class SupplierBean {

    @ApiModelProperty(value = "开户行", required = true)
    @NotBlank(message = "开户行不能为空")
    private String accountBank;

    @ApiModelProperty(value = "开户人", required = true)
    @NotBlank(message = "开户人不能为空")
    private String accountOwner;

    @ApiModelProperty(value = "营业执照", required = true)
    @NotBlank(message = "营业执照不能为空")
    private String businessLicense;

    @ApiModelProperty(value = "银行卡号", required = true)
    @NotBlank(message = "银行卡号不能为空")
    private String cardNo;

    @ApiModelProperty(value = "组织结构代码", required = true)
    @NotBlank(message = "组织结构代码不能为空")
    private String orgCode;

    @ApiModelProperty(value = "法人身份证照片", required = true)
    @NotBlank(message = "法人身份证照片不能为空")
    private String ownerCardImage;

    @ApiModelProperty(value = "税务登记证", required = true)
    @NotBlank(message = "税务登记证不能为空")
    private String taxRegisterCertifacate;

    @ApiModelProperty(value = "供应商名称", required = true)
    @NotBlank(message = "供应商名称不能为空")
    private String supplierName;

    @ApiModelProperty(value = "供应商等级id", required = true)
    private int supplierLevelId;

    @ApiModelProperty(value = "企业名称", required = true)
    @NotBlank(message = "企业名称不能为空")
    private String comanyName;

    @ApiModelProperty(value = "供应商地址", required = true)
    @NotBlank(message = "供应商地址不能为空")
    private String supplierAddress;

    @ApiModelProperty(value = "服务中心id", required = true)
    private int serviceCenterId;

    @ApiModelProperty(value = "申请人", required = true)
    @NotBlank(message = "申请人不能为空")
    private String contactName;

    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "0?(13|14|15|18)[0-9]{9}", message = "联系电话格式不正确")
    private String contactPhone;

    @ApiModelProperty(value = "法人姓名", required = true)
    @NotBlank(message = "法人姓名不能为空")
    private String supplierOwner;

    @ApiModelProperty(value = "省id", required = true)
    private int regionProv;

    @ApiModelProperty(value = "市id", required = true)
    private int regionCity;

    @ApiModelProperty(value = "区/县id", required = true)
    private int regionArea;

    @ApiModelProperty(value = "logo", required = true)
    @NotBlank(message = "logo不能为空")
    private String logoIamge;

    @ApiModelProperty(value = "banner", required = true)
    @NotBlank(message = "banner不能为空")
    private String bannerImage;

    @ApiModelProperty(value = "法人身份证", required = true)
    @NotBlank(message = "法人身份证不能为空")
    @Pattern(regexp = "\\d{17}[\\d|x]|\\d{15}", message = "身份证格式不正确")
    private String ownerIdentity;

    @ApiModelProperty(value = "店铺名称", required = true)
    @NotBlank(message = "店铺名称不能为空")
    private String supplierShopName;

    @ApiModelProperty(value = "持卡人", required = true)
    @NotBlank(message = "持卡人不能为空")
    private String cardOwner;

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

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOwnerCardImage() {
        return ownerCardImage;
    }

    public void setOwnerCardImage(String ownerCardImage) {
        this.ownerCardImage = ownerCardImage;
    }

    public String getTaxRegisterCertifacate() {
        return taxRegisterCertifacate;
    }

    public void setTaxRegisterCertifacate(String taxRegisterCertifacate) {
        this.taxRegisterCertifacate = taxRegisterCertifacate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getSupplierLevelId() {
        return supplierLevelId;
    }

    public void setSupplierLevelId(int supplierLevelId) {
        this.supplierLevelId = supplierLevelId;
    }

    public String getComanyName() {
        return comanyName;
    }

    public void setComanyName(String comanyName) {
        this.comanyName = comanyName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public int getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(int serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSupplierOwner() {
        return supplierOwner;
    }

    public void setSupplierOwner(String supplierOwner) {
        this.supplierOwner = supplierOwner;
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

    public String getLogoIamge() {
        return logoIamge;
    }

    public void setLogoIamge(String logoIamge) {
        this.logoIamge = logoIamge;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }

    public String getSupplierShopName() {
        return supplierShopName;
    }

    public void setSupplierShopName(String supplierShopName) {
        this.supplierShopName = supplierShopName;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }
}
