package cn.org.citycloud.srdz.utils;

import cn.org.citycloud.srdz.constants.Constants;
import cn.org.citycloud.srdz.entity.Message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 站内信工具类.
 *
 * @author demon
 * @Date 2016/5/23 9:30
 */
public class SiteMsgUtils {

    /**
     * 新建message
     *
     * @param receiver 收信人姓名
     * @param receiverId 收信人id
     * @param busId 业务id
     * @param msgTpl 站内信模板
     * @return message站内信实体类
     */
    public static Message getMessage(String receiver, int receiverId, int busId, String msgTpl) {
        Message message = new Message();
        Map<String, String> data = new HashMap<>();
        data.put("busId", String.valueOf(busId));
        message.setMessageContent(SmsUtil.getMessageTpl(msgTpl, data));
        if (Constants.USER_TOKEN != null) {
            message.setSenderId(Constants.USER_TOKEN.getUserId());
        } else {
            message.setSenderId(1);
        }
        message.setSender(Constants.DEFAULT_SENDER);
        message.setReceiver(receiver);
        message.setReceiverId(receiverId);
        message.setUpdateDate(new Date());
        message.setCreateDate(new Date());
        message.setSendTime(new Date());
        message.setSenderPlatform(Constants.MSG_ADMIN);
        return message;
    }

    /**
     * 获取收信人的平台类型
     *
     * @param applyType
     * @return
     */
    public static int getRsver(int applyType) {
        switch (applyType) {
            case Constants.CASH_TYPE_SUPPLIER:
                return Constants.MSG_SUPPLIER;
            case Constants.CASH_TYPE_SERVICE:
                return Constants.MSG_SERVICE;
            case Constants.CASH_TYPE_SALES:
                return Constants.CASH_TYPE_SALES;
            default:
                return 0;
        }
    }
}
