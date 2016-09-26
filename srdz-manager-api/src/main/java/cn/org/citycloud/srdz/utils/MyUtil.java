package cn.org.citycloud.srdz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
	
	public static Date convertMin(Date date)throws Exception{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str1 = df1.format(date);
		String str2 = str1+" 00:00:00";
		return df2.parse(str2);
	}
	
	public static Date convertMax(Date date)throws Exception{
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str1 = df1.format(date);
		String str2 = str1+" 23:59:59";
		return df2.parse(str2);
	}
}
