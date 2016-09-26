package cn.org.citycloud.srdz.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/6/7 15:32
 */
@Entity
@Table(name = "goods")
public class SalesGoods implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private int goodsId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "goods_addtime")
    private Date goodsAddtime;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "goods_status")
    private int goodsStatus;

    @Column(name = "member_id")
    private int memberId;

    private String standard;

    @Column(name = "init_price")
    private BigDecimal initPrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierName supplier;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Date getGoodsAddtime() {
        return goodsAddtime;
    }

    public void setGoodsAddtime(Date goodsAddtime) {
        this.goodsAddtime = goodsAddtime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public BigDecimal getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(BigDecimal initPrice) {
        this.initPrice = initPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public SupplierName getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierName supplier) {
        this.supplier = supplier;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
