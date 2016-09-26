package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/18 11:30
 */
public class ServiceCenterBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value="开户行",required=true)
    private String accountBank;

    @ApiModelProperty(value="银行",required=true)
    private String bankName;

    @ApiModelProperty(value="营业执照",required=true)
    private String businessLicense;

    @ApiModelProperty(value="银行卡号",required=true)
    private String cardNo;

    @ApiModelProperty(value="联系人",required=true)
    @NotBlank(message="联系人不能为空")
    private String contactName;

    @ApiModelProperty(value="联系电话",required=true)
    @NotBlank(message="联系电话不能为空")
    @Pattern(regexp = "0?(13|14|15|18)[0-9]{9}", message = "联系电话格式不正确")
    private String contactPhone;

    @ApiModelProperty(value="组织结构代码",required=true)
    private String orgCode;

    @ApiModelProperty(value="身份证照片",required=true)
    @NotBlank(message="身份证照片不能为空")
    private String ownerCardImage;

    @ApiModelProperty(value="身份证",required=true)
    @NotBlank(message="身份证不能为空")
    @Pattern(regexp = "\\d{17}[\\d|x]|\\d{15}", message = "身份证格式不正确")
    private String ownerIdentity;

    @ApiModelProperty(value="服务中心名称",required=true)
    @NotBlank(message="服务中心名称不能为空")
    private String serviceCenterName;

    @ApiModelProperty(value="法人姓名",required=true)
    private String serviceCenterOwner;

    @ApiModelProperty(value="企业名称",required=true)
    private String companyName;

    @ApiModelProperty(value="服务中心类型,0企业，1合伙人",required=true)
    private int serviceCenterType;

    @ApiModelProperty(value="1  正常   0  禁用",required=true)
    @Min(0)
    private int status;

    @ApiModelProperty(value="服务中心等级ID",required=true)
    private int serviceCenterLevelId = -1;

    @ApiModelProperty(value="持卡人",required=true)
    private String cardOwner;

    @ApiModelProperty(value="省id",required=true)
    private int regionProv;

    @ApiModelProperty(value="市id",required=true)
    private int regionCity;

    @ApiModelProperty(value="区域id",required=true)
    private int regionArea;

    @ApiModelProperty(value="省名称",required=true)
    @NotBlank(message="省不能为空")
    private String regionProvName;

    @ApiModelProperty(value="市名称",required=true)
    @NotBlank(message="市不能为空")
    private String regionCityName;

    @ApiModelProperty(value="区域名称",required=true)
    @NotBlank(message="区域不能为空")
    private String regionAreaName;

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }

    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public void setServiceCenterName(String serviceCenterName) {
        this.serviceCenterName = serviceCenterName;
    }

    public String getServiceCenterOwner() {
        return serviceCenterOwner;
    }

    public void setServiceCenterOwner(String serviceCenterOwner) {
        this.serviceCenterOwner = serviceCenterOwner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getServiceCenterLevelId() {
        return serviceCenterLevelId;
    }

    public void setServiceCenterLevelId(int serviceCenterLevelId) {
        this.serviceCenterLevelId = serviceCenterLevelId;
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

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
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
