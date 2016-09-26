package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the goods database table.
 */
@Entity
@Table(name = "goods")
@NamedQuery(name = "Goods.findAll", query = "SELECT g FROM Goods g")
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private int goodsId;

    @Column(name = "already_sale")
    private int alreadySale;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "auto_down_time")
    private Date autoDownTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "discount_flg")
    private byte discountFlg;

    @Column(name = "goods_add_count")
    private int goodsAddCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "goods_addtime")
    private Date goodsAddtime;

    @Column(name = "goods_class_id")
    private int goodsClassId;

    @Column(name = "goods_class_name")
    private String goodsClassName;

    @Column(name = "goods_class_three_id")
    private int goodsClassThreeId;

    @Column(name = "goods_class_three_name")
    private String goodsClassThreeName;

    @Column(name = "goods_class_two_id")
    private int goodsClassTwoId;

    @Column(name = "goods_class_two_name")
    private String goodsClassTwoName;

    @Lob
    @Column(name = "goods_desc")
    private String goodsDesc;

    @Column(name = "goods_image")
    private String goodsImage;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_status")
    private int goodsStatus;

    @Column(name = "init_price")
    private BigDecimal initPrice;

    @Column(name = "init_sale_count")
    private int initSaleCount;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "saler_flg")
    private byte salerFlg;

    private String standard;

    private int surplus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_recommend")
    private int isRecommend;

    //bi-directional many-to-one association to Supplier
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToOne
    @JoinColumn(name = "goods_id")
    private DistGoods distGoods;

    @OneToMany
    @JoinColumn(name = "goods_id")
    private Set<GoodsBanner> goodsBanners;

    @ManyToOne
    @JoinColumn(name = "flow_template_id")
    private FlowTemplate flowTemplate;

    @ManyToOne
    @JoinColumn(name = "region_prov")
    private RegionInfo province;

    @ManyToOne
    @JoinColumn(name = "region_city")
    private RegionInfo city;

    @ManyToOne
    @JoinColumn(name = "region_area")
    private RegionInfo area;

    public Goods() {
    }

    public int getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getAlreadySale() {
        return this.alreadySale;
    }

    public void setAlreadySale(int alreadySale) {
        this.alreadySale = alreadySale;
    }

    public Date getAutoDownTime() {
        return this.autoDownTime;
    }

    public void setAutoDownTime(Date autoDownTime) {
        this.autoDownTime = autoDownTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte getDiscountFlg() {
        return this.discountFlg;
    }

    public void setDiscountFlg(byte discountFlg) {
        this.discountFlg = discountFlg;
    }

    public int getGoodsAddCount() {
        return this.goodsAddCount;
    }

    public void setGoodsAddCount(int goodsAddCount) {
        this.goodsAddCount = goodsAddCount;
    }

    public Date getGoodsAddtime() {
        return this.goodsAddtime;
    }

    public void setGoodsAddtime(Date goodsAddtime) {
        this.goodsAddtime = goodsAddtime;
    }

    public int getGoodsClassId() {
        return this.goodsClassId;
    }

    public void setGoodsClassId(int goodsClassId) {
        this.goodsClassId = goodsClassId;
    }

    public String getGoodsClassName() {
        return this.goodsClassName;
    }

    public void setGoodsClassName(String goodsClassName) {
        this.goodsClassName = goodsClassName;
    }

    public int getGoodsClassThreeId() {
        return this.goodsClassThreeId;
    }

    public void setGoodsClassThreeId(int goodsClassThreeId) {
        this.goodsClassThreeId = goodsClassThreeId;
    }

    public String getGoodsClassThreeName() {
        return this.goodsClassThreeName;
    }

    public void setGoodsClassThreeName(String goodsClassThreeName) {
        this.goodsClassThreeName = goodsClassThreeName;
    }

    public int getGoodsClassTwoId() {
        return this.goodsClassTwoId;
    }

    public void setGoodsClassTwoId(int goodsClassTwoId) {
        this.goodsClassTwoId = goodsClassTwoId;
    }

    public String getGoodsClassTwoName() {
        return this.goodsClassTwoName;
    }

    public void setGoodsClassTwoName(String goodsClassTwoName) {
        this.goodsClassTwoName = goodsClassTwoName;
    }

    public String getGoodsDesc() {
        return this.goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getGoodsImage() {
        return this.goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsStatus() {
        return this.goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public BigDecimal getInitPrice() {
        return this.initPrice;
    }

    public void setInitPrice(BigDecimal initPrice) {
        this.initPrice = initPrice;
    }

    public int getInitSaleCount() {
        return this.initSaleCount;
    }

    public void setInitSaleCount(int initSaleCount) {
        this.initSaleCount = initSaleCount;
    }

    public BigDecimal getSalePrice() {
        return this.salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public byte getSalerFlg() {
        return this.salerFlg;
    }

    public void setSalerFlg(byte salerFlg) {
        this.salerFlg = salerFlg;
    }

    public String getStandard() {
        return this.standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public int getSurplus() {
        return this.surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public DistGoods getDistGoods() {
        return distGoods;
    }

    public void setDistGoods(DistGoods distGoods) {
        this.distGoods = distGoods;
    }

    public Set<GoodsBanner> getGoodsBanners() {
        return goodsBanners;
    }

    public void setGoodsBanners(Set<GoodsBanner> goodsBanners) {
        this.goodsBanners = goodsBanners;
    }

    public FlowTemplate getFlowTemplate() {
        return flowTemplate;
    }

    public void setFlowTemplate(FlowTemplate flowTemplate) {
        this.flowTemplate = flowTemplate;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public RegionInfo getProvince() {
        return province;
    }

    public void setProvince(RegionInfo province) {
        this.province = province;
    }

    public RegionInfo getCity() {
        return city;
    }

    public void setCity(RegionInfo city) {
        this.city = city;
    }

    public RegionInfo getArea() {
        return area;
    }

    public void setArea(RegionInfo area) {
        this.area = area;
    }
}