package cn.org.citycloud.srdz.utils;

import com.smsweb.www.servlet.anna.AnnaHttpSendResponseObject;
import com.smsweb.www.servlet.anna.SDKProxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.org.citycloud.srdz.constants.Constants.SMS_RESET_PWD;


/**
 * 短信发送util.
 *
 * @author demon
 * @Date 2016/5/11 10:54
 */
public class SmsUtil {
    /**
     * 发送短信验证码
     * @param phone
     * @param code
     * @return
     */
    public static  String sendSms(String phone,String code){

        // 维纳多公司
        // http://www.weinaduo.net/
        SDKProxy sdk2 = new SDKProxy();
        sdk2.init("http://yl.mobsms.net/send/sendAnna.aspx?", "shanrong", "shanrong1");
        AnnaHttpSendResponseObject oneresp = sdk2.send(phone, code);
//		System.out.println(oneresp.getErrid()+","+oneresp.getErr());

        if(oneresp != null)
            return oneresp.getErrid();
        else
            return null;
    }


    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static String getRandomStr(boolean numberFlag, int length){
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    /**
     * 获取相应的短信模板
     *
     * @param busCode 业务编码
     * @param data 业务数据
     * @return 替换相应的业务数据后的结果
     */
    public static String getMessageTpl(String busCode, Map<String, String> data) {
        Properties properties = new Properties();
        InputStream in = SmsUtil.class.getClassLoader().getResourceAsStream("short_message.properties");
        String result = "";
        try {
            properties.load(in);
            result = replacePlaceholder(properties.get(busCode).toString(), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更换占位符${}
     *
     * @param source 源字符串
     * @param data 业务数据
     * @return 更换占位符后的数据
     */
    public static String replacePlaceholder(String source, Map<String, String> data) {
        StringBuffer result = new StringBuffer();
        Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z]+\\}");
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            String matchString = matcher.group();
            matchString = matchString.replace("$", "");
            matchString = matchString.replace("{", "");
            matchString = matchString.replace("}", "");
            String value = data.get(matchString).toString();
            matcher.appendReplacement(result, value);
        }
        return matcher.appendTail(result).toString();
    }

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<>();
        data.put("userName", "1");
        data.put("pwd", "2");
        System.out.println(getMessageTpl(SMS_RESET_PWD, data));
    }
}
