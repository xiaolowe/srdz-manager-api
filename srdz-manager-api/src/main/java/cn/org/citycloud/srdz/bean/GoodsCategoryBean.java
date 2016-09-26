package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/28 9:01
 */
public class GoodsCategoryBean {

    @ApiModelProperty(value="分类名称",required=true)
    @NotBlank(message="分类名称不能为空")
    private String goodsClassName;

    @ApiModelProperty(value="父ID",required=true)
    private int parentId = -1;

    @ApiModelProperty(value="排序",required=true)
    private int sort;

    @ApiModelProperty(value="描述",required=true)
    private String goodsDesc;

    @ApiModelProperty(value="是否是三级分类,0不是，1是",required=true)
    private int isThirdLevel;

    @ApiModelProperty(value="分类banner图",required=true)
    private Set<GoodsClassBannerBean> goodsClassBannerSet;

    public String getGoodsClassName() {
        return goodsClassName;
    }

    public void setGoodsClassName(String goodsClassName) {
        this.goodsClassName = goodsClassName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Set<GoodsClassBannerBean> getGoodsClassBannerSet() {
        return goodsClassBannerSet;
    }

    public void setGoodsClassBannerSet(Set<GoodsClassBannerBean> goodsClassBannerSet) {
        this.goodsClassBannerSet = goodsClassBannerSet;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public int getIsThirdLevel() {
        return isThirdLevel;
    }

    public void setIsThirdLevel(int isThirdLevel) {
        this.isThirdLevel = isThirdLevel;
    }
}
