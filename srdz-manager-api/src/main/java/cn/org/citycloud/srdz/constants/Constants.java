package cn.org.citycloud.srdz.constants;

import cn.org.citycloud.srdz.bean.UserToken;

/**
 * 常量Class
 * @author lanbo
 *
 */
public class Constants {

	public static UserToken USER_TOKEN = null;

	public static final long TOKEN_EXPIRES_IN = 24*3600;
	
	public static final String TOKEN_SECRET = "IFFa52XkBEQ9AoO8";
	
	// 会员的状态 1为开启 0为关闭
	public static final int MEMBER_STATUS_OPEN = 1;
	
	public static final int MEMBER_STATUS_CLOSED = 0;
	
	// 接口外网地址
	public static final String API_URL1 = "";
	
	// 接口外网路径
	public static final String API_PATH = "/xbty-manager-api";
	
	//图片外网地址
	public static final String FILE_URL="";
	
	// 接口外网地址
	public static final String API_URL = "";
	
	// 素材库类型 [1:我的素材库， 2:系统素材库]
	public static final int MATERIAL_PERSONAL = 1;
	public static final int MATERIAL_SYSTEM = 2;
	
	public static final int ARTICLE_STATE = 1;

	// 会员的状态 1为开启 0为关闭
	public static final int MEMBER_STATE_OPEN = 1;

	public static final int MEMBER_STATE_CLOSED = 0;

	// 站内信默认发送人
	public static final String DEFAULT_SENDER = "系统";

	/** 邮件主机地址 */
	public static final String MAIL_HOST = "mail.host";

	/** 邮件发送系统用户名 */
	public static final String MAIL_USERNAME = "mail.username";

	/** 邮件发送系统密码 */
	public static final String MAIL_PWD = "mail.pwd";

	public static final String MAIL_PWD_KEY = "mail.pwd.key";

	/************************* 订单状态 *************************/
	/** 订单状态：已取消 */
	public static final int ORDER_ALREADY_CANCEL = 0;

	/** 订单状态：未付款 */
	public static final int ORDER_UNPAYED = 10;

	/** 订单状态：已付款 */
	public static final int ORDER_ALREADY_PAYED = 20;

	/** 订单状态：已发货 */
	public static final int ORDER_ALREADY_SEND = 30;

	/** 订单状态：待收货 */
	public static final int ORDER_AWAIT_RECEIVE = 40;

	/** 订单状态：待评价 */
	public static final int ORDER_AWAIT_EVALUATE = 50;

	/** 订单状态：已完成 */
	public static final int ORDER_DONE = 60;

	/** 订单异常状态：正常 */
	public static final int ORDER_EXCEPTION_NORMAL = 0;

	/** 订单异常状态：买家异常 */
	public static final int ORDER_EXCEPTION_BUYER = 1;

	/** 订单异常状态：卖家异常 */
	public static final int ORDER_EXCEPTION_SALER = 2;

	/** 订单退款状态：退款中 */
	public static final int ORDER_REFUNDING = 120;

	/** 订单退款状态：拒绝退款 */
	public static final int ORDER_REFUND_REJECTED = 130;

	/** 订单退款状态：已退款 */
	public static final int ORDER_REFUNDED = 140;

	/************************* 商品状态 *************************/
	/** 商品状态：正常 */
	public static final int GOODS_NORMAL = 0;

	/** 商品状态：上架 */
	public static final int GOODS_SHELVE = 1;

	/** 商品状态：下架 */
	public static final int GOODS_UNSHELVE = 2;

	/** 商品状态：禁售 */
	public static final int GOODS_AVOID_SALE = 10;

	/************************* 商品是否推荐 *************************/
	/** 商品是否推荐：不推荐 */
	public static final int UNRECOMMEND = 0;

	/** 商品是否推荐：推荐 */
	public static final int RECOMMEND = 1;

	/************************* 广告状态 *************************/
	/** 广告状态：正常 */
	public static final int AD_NOMAL = 0;

	/** 广告状态：过期 */
	public static final int AD_OUT_DATE = 1;

	/************************* 活动状态 *************************/
	/** 活动状态：未开始 */
	public static final int ACTIVITY_NOT_START = 0;

	/** 活动状态：进行中 */
	public static final int ACTIVITY_RUNNING = 1;

	/** 活动状态：已结束 */
	public static final int ACTIVITY_OVER = 2;

	/** 活动状态：关闭 */
	public static final int ACTIVITY_CLOSED = 3;

	/************************* 提款申请类型 *************************/
	/** 提款申请类型：供应商提款 */
	public static final int CASH_TYPE_SUPPLIER = 2;

	/** 提款申请类型：服务中心提款 */
	public static final int CASH_TYPE_SERVICE = 3;

	/** 提款申请类型：分销提款 */
	public static final int CASH_TYPE_SALES = 4;

	/** 提款申请类型：平台提款 */
	public static final int CASH_TYPE_PLAT = 1;

	/************************* 提款申请状态 *************************/
	/** 提款申请状态：未审核 */
	public static final int CASH_UNAUDIT = 0;

	/** 提款申请状态：审核通过 */
	public static final int CASH_AUDIT_SUCCESS = 1;

	/** 提款申请状态：已打款 */
	public static final int CASH_ALREADY_PAY = 2;

	/** 提款申请状态：审核驳回 */
	public static final int CASH_AUDIT_FALSE = 3;

	/** 提款申请状态：供应商未审核 */
	public static final int CASH_SUPPLIER_UNAUDIT = 4;

	/** 提款申请状态：供应商审核通过 */
	public static final int CASH_SUPPLIER_SUCCESS = 5;

	/** 提款申请状态：供应商审核驳回 */
	public static final int CASH_SUPPLIER_FALSE = 6;

	/************************* 平台优惠券状态 *************************/
	/** 平台优惠券：正常 */
	public static final int COUPON_NORMAL = 10;

	/** 平台优惠券：停发 */
	public static final int COUPON_STOP = 20;

	/************************* 用户角色 *************************/
	/** 用户角色：管理员 */
	public static int ROLE_ADMIN = 1;

	/** 用户角色：财务 */
	public static int ROLE_FIN = 2;

	/** 用户角色：出纳 */
	public static int ROLE_TELLER = 3;

	/** 用户角色：超管 */
	public static int ROLE_SUPER = 99;

	/************************* 短信模板 *************************/
	/** 重置密码短信模板buscode */
	public static String SMS_RESET_PWD = "reset_pwd";

	/** 新建服务中心用户短信模板buscode */
	public static String SMS_NEW_SERVICE_CENTER = "new_service_center";

	/** 驳回供应商短信模板buscode */
	public static String SMS_REJECTED_SUPPLIER = "supplier_rejected";

	/************************* 站内信模板 *************************/
	/** 提款申请被驳回buscode */
	public static String CASH_REJECTED = "cash_rejected";

	/** 确认打款buscode */
	public static String CASH_SURE = "cash_sure";

	/** 商品禁售buscode */
	public static String GOOD_FORBID = "goods_forbid";

	/** 商品取消禁售buscode */
	public static String GOOD_CANCEL_FORBID = "goods_cancel_forbid";

	/** 订单超过三天被取消buscode */
	public static String ORDER_CANCEL = "order_cancel";

	/************************* 站内信platform *************************/
	/** 管理后台 */
	public static int MSG_ADMIN = 1;

	/** 供应商 */
	public static int MSG_SUPPLIER = 2;

	/** 服务中心 */
	public static int MSG_SERVICE = 3;

	/** 用户 */
	public static int MSG_USER = 4;

	/************************* 商品类型 *************************/
	/** 商品类型：普通 */
	public static int GOODS_TYPE_NORMAL = 0;

	/** 商品类型：特惠 */
	public static int GOODS_TYPE_DIS = 1;

	/************************* 分销会员状态 *************************/
	/** 状态：关闭 */
	public static int MEMBER_CLOSE = 0;

	/** 状态：开启 */
	public static int MEMBER_OPEN = 1;

	/************************* 财务支出申请类型 *************************/
	/** 申请类型：取款 */
	public static int DRAW_MONEY = 1;

	/** 申请类型：退款 */
	public static int REFUND = 2;
}
