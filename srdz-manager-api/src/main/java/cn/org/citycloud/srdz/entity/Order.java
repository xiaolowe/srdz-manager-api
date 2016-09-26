package cn.org.citycloud.srdz.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private int orderId;

	@Column(name="back_order_cause")
	private String backOrderCause;

	@Column(name="back_order_status")
	private int backOrderStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="confirm_date")
	private Date confirmDate;

	@Column(name="confirm_id")
	private int confirmId;

	@Column(name="confirm_name")
	private String confirmName;

	@Column(name="contact_address")
	private String contactAddress;

	@Column(name="contact_name")
	private String contactName;

	@Column(name="contact_phone")
	private String contactPhone;

	@Column(name="coupon_price")
	private BigDecimal couponPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private int exception;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="exception_time")
	private Date exceptionTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="finish_time")
	private Date finishTime;

	@Column(name="flow_company_name")
	private String flowCompanyName;

	@Column(name="flow_number")
	private BigInteger flowNumber;

	@Column(name="flow_price")
	private BigDecimal flowPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="get_goods_time")
	private Date getGoodsTime;

	@Column(name="init_price")
	private BigDecimal initPrice;

	@Column(name="is_flow")
	private int isFlow;

	@Column(name="member_addr_id")
	private int memberAddrId;

	@Column(name="member_id")
	private int memberId;

	@Column(name="member_name")
	private String memberName;

	@Column(name="member_phone")
	private String memberPhone;

	@Column(name="order_cause")
	private String orderCause;

	@Column(name="order_price")
	private BigDecimal orderPrice;

	@Column(name="order_status")
	private int orderStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="order_time")
	private Date orderTime;

	@Column(name="order_type")
	private int orderType;

	@Column(name="pay_code")
	private String payCode;

	@Column(name="pay_id")
	private String payId;

	@Column(name="pay_price")
	private BigDecimal payPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pay_time")
	private Date payTime;

	@Column(name="post_code")
	private String postCode;

	@Column(name="pre_pay")
	private String prePay;

	@Column(name="region_area")
	private int regionArea;

	@Column(name="region_area_name")
	private String regionAreaName;

	@Column(name="region_city")
	private int regionCity;

	@Column(name="region_city_name")
	private String regionCityName;

	@Column(name="region_prov")
	private int regionProv;

	@Column(name="region_prov_name")
	private String regionProvName;

	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_goods_time")
	private Date sendGoodsTime;

	@Column(name="supplier_amount")
	private BigDecimal supplierAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@OneToOne
	@JoinColumn(name = "order_id")
	private OrderInfo orderInfo;

	@OneToOne
	@JoinColumn(name = "order_id")
	private SalesOrderInfo salesOrderInfo;

	@OneToMany
	@JoinColumn(name = "order_id")
	private Set<OrderGoods> orderGoods;

	public Order() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getBackOrderCause() {
		return this.backOrderCause;
	}

	public void setBackOrderCause(String backOrderCause) {
		this.backOrderCause = backOrderCause;
	}

	public int getBackOrderStatus() {
		return this.backOrderStatus;
	}

	public void setBackOrderStatus(int backOrderStatus) {
		this.backOrderStatus = backOrderStatus;
	}

	public Date getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public int getConfirmId() {
		return this.confirmId;
	}

	public void setConfirmId(int confirmId) {
		this.confirmId = confirmId;
	}

	public String getConfirmName() {
		return this.confirmName;
	}

	public void setConfirmName(String confirmName) {
		this.confirmName = confirmName;
	}

	public String getContactAddress() {
		return this.contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactName() {
		return this.contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public BigDecimal getCouponPrice() {
		return this.couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getException() {
		return this.exception;
	}

	public void setException(int exception) {
		this.exception = exception;
	}

	public Date getExceptionTime() {
		return this.exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	public Date getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getFlowCompanyName() {
		return this.flowCompanyName;
	}

	public void setFlowCompanyName(String flowCompanyName) {
		this.flowCompanyName = flowCompanyName;
	}

	public BigInteger getFlowNumber() {
		return this.flowNumber;
	}

	public void setFlowNumber(BigInteger flowNumber) {
		this.flowNumber = flowNumber;
	}

	public BigDecimal getFlowPrice() {
		return this.flowPrice;
	}

	public void setFlowPrice(BigDecimal flowPrice) {
		this.flowPrice = flowPrice;
	}

	public Date getGetGoodsTime() {
		return this.getGoodsTime;
	}

	public void setGetGoodsTime(Date getGoodsTime) {
		this.getGoodsTime = getGoodsTime;
	}

	public BigDecimal getInitPrice() {
		return this.initPrice;
	}

	public void setInitPrice(BigDecimal initPrice) {
		this.initPrice = initPrice;
	}

	public int getIsFlow() {
		return this.isFlow;
	}

	public void setIsFlow(int isFlow) {
		this.isFlow = isFlow;
	}

	public int getMemberAddrId() {
		return this.memberAddrId;
	}

	public void setMemberAddrId(int memberAddrId) {
		this.memberAddrId = memberAddrId;
	}

	public int getMemberId() {
		return this.memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return this.memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getOrderCause() {
		return this.orderCause;
	}

	public void setOrderCause(String orderCause) {
		this.orderCause = orderCause;
	}

	public BigDecimal getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public int getOrderType() {
		return this.orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getPayCode() {
		return this.payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public BigDecimal getPayPrice() {
		return this.payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPrePay() {
		return this.prePay;
	}

	public void setPrePay(String prePay) {
		this.prePay = prePay;
	}

	public int getRegionArea() {
		return this.regionArea;
	}

	public void setRegionArea(int regionArea) {
		this.regionArea = regionArea;
	}

	public String getRegionAreaName() {
		return this.regionAreaName;
	}

	public void setRegionAreaName(String regionAreaName) {
		this.regionAreaName = regionAreaName;
	}

	public int getRegionCity() {
		return this.regionCity;
	}

	public void setRegionCity(int regionCity) {
		this.regionCity = regionCity;
	}

	public String getRegionCityName() {
		return this.regionCityName;
	}

	public void setRegionCityName(String regionCityName) {
		this.regionCityName = regionCityName;
	}

	public int getRegionProv() {
		return this.regionProv;
	}

	public void setRegionProv(int regionProv) {
		this.regionProv = regionProv;
	}

	public String getRegionProvName() {
		return this.regionProvName;
	}

	public void setRegionProvName(String regionProvName) {
		this.regionProvName = regionProvName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSendGoodsTime() {
		return this.sendGoodsTime;
	}

	public void setSendGoodsTime(Date sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}

	public BigDecimal getSupplierAmount() {
		return this.supplierAmount;
	}

	public void setSupplierAmount(BigDecimal supplierAmount) {
		this.supplierAmount = supplierAmount;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public SalesOrderInfo getSalesOrderInfo() {
		return salesOrderInfo;
	}

	public void setSalesOrderInfo(SalesOrderInfo salesOrderInfo) {
		this.salesOrderInfo = salesOrderInfo;
	}

	public Set<OrderGoods> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(Set<OrderGoods> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}
}