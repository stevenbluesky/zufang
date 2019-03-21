package com.ant.service.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.ant.vo.DeviceDegreesHourLogVo;
import com.isurpass.service.HiDeviceDegreesHourLogService;

public class HiDeviceDegreesHourLogServiceTest
{
	public static void main(String arg[])
	{
		ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");  
		HiDeviceDegreesHourLogService svr=(HiDeviceDegreesHourLogService)context.getBean("HiDeviceDegreesHourLogService");
		
		DeviceDegreesHourLogVo vo = new DeviceDegreesHourLogVo();
		List lst = svr.reportByMonth(vo);
		
		System.out.println(JSON.toJSONString(lst));
	}
}
