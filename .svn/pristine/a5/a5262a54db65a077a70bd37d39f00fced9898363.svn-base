package com.ant.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.RequestContext;

import com.alibaba.fastjson.JSON;
import com.ant.vo.PersonVo;

public class BaseController
{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	public PersonVo getCurrentUser()
	{
		if(request.getSession().getAttribute("user") != null)
		{
			String str = request.getSession().getAttribute("user").toString();
			return JSON.parseObject(str,PersonVo.class);
		}
		
		return null;
	}
	
	public Map<String , Object> createResponse(int resultCode , String msg , Object content)
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);

		rst.put("object", content);
		
		return rst ;
	}

	public Map<String , Object> createResponse(int resultCode , String msg , Object content1,Object content2)
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);

		rst.put("object", content1);
		rst.put("obj", content2);
		return rst ;
	}
	public Map<String , Object> createResponse(int resultCode , String msg , Object content1,boolean isZwaveDevice)
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);

		rst.put("object", content1);
		rst.put("isZwaveDevice", isZwaveDevice);
		return rst ;
	}
	public Map<String, Object> createResponse(int count , Object object)
	{
		Map<String , Object> obj = new HashMap<String , Object>();
		obj.put("count", count);
		obj.put("list", object);
		return obj ;
	}
	
	public Map<String , Object> createResponse(int resultCode , String msg , int count , Object content)
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);

		rst.put("object", createResponse(count , content));
		
		return rst ;
	}

	public Map<String , Object> createResponse(int resultCode , String msg , int count , Object content,int synchronizedCount ,int toBeSynchronizedCount,int needSynchronizeCount)
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);

		rst.put("object", createResponse(count , content));
		rst.put("synchronizedCount", synchronizedCount);
		rst.put("toBeSynchronizedCount", toBeSynchronizedCount);
		rst.put("needSynchronizeCount", needSynchronizeCount);
		
		return rst ;
	}
	public Map<String , Object> createResponse(int resultCode , String msg , int count , Object content,int use,long doorlockpasswordruleid)
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);

		rst.put("object", createResponse(count , content));
		
		rst.put("use", use);
		rst.put("doorlockpasswordruleid", doorlockpasswordruleid);
		return rst ;
	}


	public Map<String , Object> createResponse(int resultCode , String msg , int count , Object content,boolean isUseSpecifiyUsercode)
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);

		rst.put("object", createResponse(count , content));
		rst.put("isUseSpecifiyUsercode", isUseSpecifiyUsercode);
		
		return rst ;
	}

	public Map<String , Object> createResponse(int resultCode , String msg , int count , Object content,boolean isUseSpecifiyUsercode,boolean isZwaveDevice)
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);

		rst.put("object", createResponse(count , content));
		rst.put("isUseSpecifiyUsercode", isUseSpecifiyUsercode);
		rst.put("isZwaveDevice", isZwaveDevice);
		
		return rst ;
	}
	
	public Map<String , Object> createErrorResponse(int resultCode , String msg )
	{
		Map<String , Object> rst = new HashMap<String , Object>();
		rst.put("success", resultCode);
		rst.put("message", msg);
		return rst ;
	}
	
	 public String getMessage(String key)
	 {
        RequestContext requestContext = new RequestContext(request);
        String value = requestContext.getMessage(key);
        return value;
    }
	 
	 public ResourceBundle getRB(){
		ResourceBundle rb;
		String country = request.getLocale().getCountry();
		if ("CN".equals(country)||"Hans".equalsIgnoreCase(country)||"chs".equalsIgnoreCase(country)) {
		    rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
		}else if("TW".equals(country)||"HK".equals(country)||"Hant".equalsIgnoreCase(country)||"CHT".equalsIgnoreCase(country)){
			rb = ResourceBundle.getBundle("messages",Locale.TRADITIONAL_CHINESE);
		}else {
		    rb = ResourceBundle.getBundle("messages", Locale.US);
		}
		return rb;
	 }
}
