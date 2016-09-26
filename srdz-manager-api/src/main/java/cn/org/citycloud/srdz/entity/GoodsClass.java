package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the goods_class database table.
 * 
 */
@Entity
@Table(name="goods_class")
@NamedQuery(name="GoodsClass.findAll", query="SELECT g FROM GoodsClass g")
public class GoodsClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="goods_class_id")
	private int goodsClassId;

	@Column(name="class_image")
	private String classImage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="goods_class_name")
	private String goodsClassName;

	@Column(name="parent_id")
	private int parentId;

	private int sort;

	@Lob
	@Column(name = "goods_desc")
	private String goodsDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="del_flag")
	private int delFlag;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name="goods_class_id")
	private Set<GoodsClassBanner> goodsClassBannerSet;

	public GoodsClass() {
	}

	public int getGoodsClassId() {
		return this.goodsClassId;
	}

	public void setGoodsClassId(int goodsClassId) {
		this.goodsClassId = goodsClassId;
	}

	public String getClassImage() {
		return this.classImage;
	}

	public void setClassImage(String classImage) {
		this.classImage = classImage;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGoodsClassName() {
		return this.goodsClassName;
	}

	public void setGoodsClassName(String goodsClassName) {
		this.goodsClassName = goodsClassName;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set<GoodsClassBanner> getGoodsClassBannerSet() {
		return goodsClassBannerSet;
	}

	public void setGoodsClassBannerSet(Set<GoodsClassBanner> goodsClassBannerSet) {
		this.goodsClassBannerSet = goodsClassBannerSet;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
}