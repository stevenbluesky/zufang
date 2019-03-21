package com.ant.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.isurpass.service.HiDeviceService;

public class DeviceServiceTest
{
	public static void main(String arg[])
	{
		ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");  
		HiDeviceService svr=(HiDeviceService)context.getBean("hiDeviceService");
		
		List lst = svr.findBindDeviceVo(259L);
		
		System.out.println(JSON.toJSONString(lst));

	}
}
