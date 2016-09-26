package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 15:29
 */
public class BrandBean {

    @ApiModelProperty(value = "品牌名称", required = true)
    @NotBlank(message = "品牌名称不能为空")
    private String brandName;

    @ApiModelProperty(value = "品牌状态", required = true)
    private int status;

    @ApiModelProperty(value = "排序", required = true)
    private int brandSort;

    @ApiModelProperty(value = "品牌logo", required = true)
    @NotBlank(message = "品牌logo不能为空")
    private String logo;


    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBrandSort() {
        return brandSort;
    }

    public void setBrandSort(int brandSort) {
        this.brandSort = brandSort;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
