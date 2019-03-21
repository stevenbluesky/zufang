package com.ant.util;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class LogonUtils {

	private static Log log = LogFactory.getLog(LogonUtils.class);
	
	/**判断验证码输入是否正确*/
	public static boolean checkNumber(String checkNumber,HttpServletRequest request) {
		//获取页面的验证码
		if(StringUtils.isBlank(checkNumber)){
			return false;
		}
		//获取从Session中生成的验证码
		String CHECK_NUMBER_KEY = (String) request.getSession().getAttribute("CHECK_NUMBER_KEY");
		if(StringUtils.isBlank(CHECK_NUMBER_KEY)){
			return false;
		}
		//从页面中获取输入的验证码和Session中生成的验证码进行比对
		return checkNumber.equalsIgnoreCase(CHECK_NUMBER_KEY);
	}

	/**添加记住我的功能*/
	public static void remeberMe(String name,String password,String remeberMe,HttpServletRequest request,
			HttpServletResponse response) {
		//创建2个Cookie，一个存放用户名，一个存放密码
		//如果name中存在中文，需要进行转码
		try {
			name = URLEncoder.encode(name, "UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage() , e);
		}
		Cookie nameCookie = new Cookie("fbUserName", name);
		Cookie passwordCookie = new Cookie("fbUserPassword", password);
		//设置Cookie的有效路径，项目的根路径
		nameCookie.setPath(request.getContextPath()+"/");
		passwordCookie.setPath(request.getContextPath()+"/");
		/**
		 * 获取页面记住我对应的复选框remeberMe的值
		      * 如果值为yes：设置Cookie的有效时间（1周）
		      * 如果值为null：清空Cookie的有效时间
		 */
		if(remeberMe!=null && remeberMe.equals("checked")){
			//设置Cookie的有效时间（2周）
			nameCookie.setMaxAge(10*60*60*24*7);
			passwordCookie.setMaxAge(10*60*60*24*7);
		}
		else{
			nameCookie.setMaxAge(0);
			passwordCookie.setMaxAge(0);
		}
		//将Cookie放置到Response对象中
		response.addCookie(nameCookie);
		response.addCookie(passwordCookie);
		
	}
}
