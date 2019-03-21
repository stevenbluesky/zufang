package com.isurpass.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.ant.vo.DeviceVo;

public class HiDeviceServiceTest
{
	public static void main(String arg[])
	{
		ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");  
		HiDeviceService svr=(HiDeviceService)context.getBean("HiDeviceService");
		
		DeviceVo deviceVo = new DeviceVo();
		deviceVo.setBindStatus(0);
		deviceVo.setPersonId(74L);
		List<DeviceVo> lst = svr.findPage(deviceVo, null,null);
		
//		List<DeviceVo> lst = svr.findBindDeviceVo(259L);
//		
		for ( DeviceVo dv : lst )
			System.out.println(JSON.toJSONString(dv));
		System.out.println(JSON.toJSONString(lst));

	}
}
