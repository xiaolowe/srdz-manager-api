package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/27 13:21
 */
public class GoodsClassBean {

    private int goodsClassId;

    @ApiModelProperty(value="分类名称",required=true)
    @NotBlank(message="分类名称不能为空")
    private String goodsClassName;

    private int parentId;

    private String classImage;

    private int sort;

    private List<GoodsClassBean> children;

    public int getGoodsClassId() {
        return goodsClassId;
    }

    public void setGoodsClassId(int goodsClassId) {
        this.goodsClassId = goodsClassId;
    }

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

    public List<GoodsClassBean> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsClassBean> children) {
        this.children = children;
    }

    public String getClassImage() {
        return classImage;
    }

    public void setClassImage(String classImage) {
        this.classImage = classImage;
    }
}
