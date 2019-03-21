package com.isurpass.common.winxipay;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONObject;
import com.ant.config.MjConfig;
import com.ant.restful.service.HttpUtil;

public class WeixinPayCall 
{
	private static Log log = LogFactory.getLog(WeixinPayCall.class);
	protected final static String SUCCESS = "SUCCESS";
	
	protected final static String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	protected String appid ;//= "wxf8a9189eda46c84f" ;
	protected String mchid ;//= "1491306082" ;
	protected String apikey ;//= "BGEbEn6vx4UeQvKBKS471YJlFK17M15g";
	protected String scene_info ;//= "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://dev.isurpass.com.cn\",\"wap_name\": \"\u7ECF\u7EAC\u7EB5\u6A2A\"}}";
	protected String notify_url ;//= "http://dev.isurpass.com.cn/zufang/service/roomfee/winxinnotify";
	protected String appname ;
	
	public WeixinPayCall(String appid , String mchid , String apikey , String url , String contextpath, String name)
	{
		this.appid = appid ;
		this.mchid = mchid ;
		this.apikey = apikey ;
		scene_info = String.format("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"http://%s\",\"wap_name\": \"%s\"}}", url , name);
		notify_url = String.format("http://%s/%s/service/roomfee/winxinnotify", url , contextpath);
		this.appname = name;
	}
	
	public String unifiedorder(String description ,String userip , int totalfee, int trascationid, JSONObject atcj)
	{
		String rq = createRequestData(description ,userip , totalfee , trascationid, atcj );
		
		String str = HttpUtil.httprequest(UNIFIEDORDER_URL, rq);
		
		Map<String , String> rstm = parseResult(str);
		
		if ( this.checkSing(rstm) == false )
		{
			log.error("sign error");
			return null;
		}
		
		if ( SUCCESS.equals(rstm.get("return_code")) && SUCCESS.equals(rstm.get("result_code")))
			return rstm.get("mweb_url");
		else 
			log.error("weixin return error");
		return null ;
	}
	
	public static Map<String , String> parseResult(String str)
	{
		Document document = null ;
		try {
			document = DocumentHelper.parseText(str);
		} catch (DocumentException e1) {
			e1.printStackTrace();
			return null ;
		}
		Element root = document.getRootElement();

		Map<String , String> map = new HashMap<String , String>();
		
		for ( Object o : root.elements())
		{
			Element e = ( Element)o;
			map.put(e.getName(), e.getText());
		}
		return map ;
	}
	
	public String createRequestData(String description ,String userip , int totalfee , int trascationid , JSONObject atcj) 
	{
		Map<String , String> pm = new HashMap<String , String>();
		
		pm.put("appid", appid);
		pm.put("mch_id", mchid);
		pm.put("device_info", "WEB");
		pm.put("nonce_str", craetenoncestr(20));
		pm.put("sign_type", "MD5");
		pm.put("body", description);
		pm.put("out_trade_no", String.format("%s_%d" , MjConfig.get("transactionnoprefix") , trascationid));
		pm.put("fee_type", "CNY");
		pm.put("total_fee", String.valueOf(totalfee));
		pm.put("spbill_create_ip", userip);
		pm.put("notify_url", notify_url);
		pm.put("trade_type", "MWEB");
		pm.put("scene_info", scene_info);

		pm.put("attach", atcj.toJSONString());
		
		String sign = createSign(pm);
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		
		for ( String key : pm.keySet())
		{
			Element e = root.addElement(key);
			e.setText(pm.get(key));
		}
		Element e = root.addElement("sign");
		e.setText(sign);

		return document.asXML();
	}
	
	private static StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"); 
	
	private String craetenoncestr(int length)
	{
		StringBuffer sb = new StringBuffer();
		SecureRandom r = new SecureRandom();
		
		for ( int i = 0 ; i < length ; i ++ )
			sb.append(buffer.charAt(r.nextInt(buffer.length())));

		return sb.toString();
	}
	
	public boolean checkSing(Map<String , String> pm)
	{
		String sign = pm.get("sign");
		if ( StringUtils.isBlank(sign))
			return false ;
		pm.remove("sign");
		String s = createSign(pm);
		return sign.equals(s);
	}
	
	private String createSign(Map<String , String> pm)
	{
		StringBuilder paramString = new StringBuilder();
		String[] sa = pm.keySet().toArray(new String[0]);
		Arrays.sort(sa);
		
		for ( String key : sa)
			paramString.append(key).append("=").append(pm.get(key)).append("&");
		paramString.append("key=").append(apikey);
		
		log.info(paramString.toString());
		return DigestUtils.md5Hex(paramString.toString()).toString().toUpperCase();
	}
}
