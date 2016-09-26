package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the sales_shop database table.
 * 
 */
@Entity
@Table(name="sales_shop")
@NamedQuery(name="SalesShop.findAll", query="SELECT s FROM SalesShop s")
public class SalesShop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="sales_shop_id")
	private int salesShopId;

	@Column(name="banner_image")
	private String bannerImage;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String logo;

	@Column(name="sales_shop_name")
	private String salesShopName;

	private int status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	//bi-directional one-to-one association to SalesMember
	@OneToOne
	@JoinColumn(name="member_id")
	private SalesMember salesMember;

	//bi-directional many-to-one association to Goods
	@OneToMany
	@JoinColumn(name="member_id")
	private Set<SalesGoods> goods;

	public SalesShop() {
	}

	public int getSalesShopId() {
		return this.salesShopId;
	}

	public void setSalesShopId(int salesShopId) {
		this.salesShopId = salesShopId;
	}

	public String getBannerImage() {
		return this.bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSalesShopName() {
		return this.salesShopName;
	}

	public void setSalesShopName(String salesShopName) {
		this.salesShopName = salesShopName;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public SalesMember getSalesMember() {
		return this.salesMember;
	}

	public void setSalesMember(SalesMember salesMember) {
		this.salesMember = salesMember;
	}

	public Set<SalesGoods> getGoods() {
		return this.goods;
	}

	public void setGoods(Set<SalesGoods> goods) {
		this.goods = goods;
	}

}