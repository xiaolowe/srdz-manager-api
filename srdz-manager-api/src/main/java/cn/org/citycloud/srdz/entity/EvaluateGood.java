package cn.org.citycloud.srdz.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the evaluate_goods database table.
 * 
 */
@Entity
@Table(name="evaluate_goods")
@NamedQuery(name="EvaluateGood.findAll", query="SELECT e FROM EvaluateGood e")
public class EvaluateGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="geval_id")
	private int gevalId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="geval_addtime")
	private Date gevalAddtime;

	@Column(name="geval_content")
	private String gevalContent;

	@Column(name="geval_image")
	private String gevalImage;

	@Column(name="geval_isanonymous")
	private int gevalIsanonymous;

	@Column(name="geval_remark")
	private String gevalRemark;

	@Column(name="geval_scores")
	private int gevalScores;

	@Column(name="geval_status")
	private int gevalStatus;

	@Column(name="goods_id")
	private int goodsId;

	@Column(name="goods_name")
	private String goodsName;

	@Column(name="goods_price")
	private BigDecimal goodsPrice;

	@Column(name="member_id")
	private int memberId;

	@Column(name="member_truename")
	private String memberTruename;

	@Column(name="order_goods_id")
	private int orderGoodsId;

	@Column(name="order_id")
	private int orderId;

	@Column(name="reply_content")
	private String replyContent;

	@ManyToOne
	@JoinColumn(name="supplier_id")
	private Supplier supplier;

	public EvaluateGood() {
	}

	public int getGevalId() {
		return this.gevalId;
	}

	public void setGevalId(int gevalId) {
		this.gevalId = gevalId;
	}

	public Date getGevalAddtime() {
		return this.gevalAddtime;
	}

	public void setGevalAddtime(Date gevalAddtime) {
		this.gevalAddtime = gevalAddtime;
	}

	public String getGevalContent() {
		return this.gevalContent;
	}

	public void setGevalContent(String gevalContent) {
		this.gevalContent = gevalContent;
	}

	public String getGevalImage() {
		return this.gevalImage;
	}

	public void setGevalImage(String gevalImage) {
		this.gevalImage = gevalImage;
	}

	public int getGevalIsanonymous() {
		return this.gevalIsanonymous;
	}

	public void setGevalIsanonymous(int gevalIsanonymous) {
		this.gevalIsanonymous = gevalIsanonymous;
	}

	public String getGevalRemark() {
		return this.gevalRemark;
	}

	public void setGevalRemark(String gevalRemark) {
		this.gevalRemark = gevalRemark;
	}

	public int getGevalScores() {
		return this.gevalScores;
	}

	public void setGevalScores(int gevalScores) {
		this.gevalScores = gevalScores;
	}

	public int getGevalStatus() {
		return this.gevalStatus;
	}

	public void setGevalStatus(int gevalStatus) {
		this.gevalStatus = gevalStatus;
	}

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return this.goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsPrice() {
		return this.goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberTruename() {
		return this.memberTruename;
	}

	public void setMemberTruename(String memberTruename) {
		this.memberTruename = memberTruename;
	}

	public int getOrderGoodsId() {
		return this.orderGoodsId;
	}

	public void setOrderGoodsId(int orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getReplyContent() {
		return this.replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}