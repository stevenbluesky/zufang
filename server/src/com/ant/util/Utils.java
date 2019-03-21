package com.ant.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.isurpass.common.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.constant.CommonConstant;
import java.text.ParseException;

public class Utils {

	private static Log log = LogFactory.getLog(Utils.class);
	
	public static void checkResult(String str,ResourceBundle rb)
	{
		JSONObject resultMap = null;
		try {
			resultMap = JSON.parseObject(str);
		} catch (Exception e) {
			throw new BusinessException(rb.getString("scf"));
		}
		if(StringUtil.checkNull(resultMap.getString("resultCode"))){
			throw new BusinessException(rb.getString("seecib"));
		}
		int resultCode = resultMap.getIntValue("resultCode");
		if(resultCode != 0 &&  resultCode != 30580){
			throw new BusinessException(CommonConstant.getErrorMsg(resultCode,rb));
		}
	}
	
	public static void checkResult(String str)
	{
		JSONObject resultMap = null;
		try {
			resultMap = JSON.parseObject(str);
		} catch (Exception e) {
			throw new BusinessException("服务器连接失败");
		}
		if(StringUtil.checkNull(resultMap.getString("resultCode"))){
			throw new BusinessException("系统错误，错误代码为空");
		}
		int resultCode = resultMap.getIntValue("resultCode");
		if(resultCode != 0 &&  resultCode != 30580){
			throw new BusinessException("系统错误，错误代码为"+resultCode);
		}
	}
	
	public static Date parseTime(String time , String format)
	{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date d = null ;
		try {
			d = sf.parse(time);
		} catch (ParseException e) {
			log.error("" , e);
			return null;
		}
		return d ;
	}
	
	public static Date parseTime(String time)
	{
		return parseTime(time , "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String formatTime(Date date , String format)
	{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}
	
	public static String formatTime(Date date)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}
	
	
}
