package com.ant.service.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.isurpass.service.HiRoomFeeService;
import com.isurpass.test.util.TestUtil;

public class RoomFeeServiceImplTest
{
	public static void main(String arg[])
	{
		HiRoomFeeService rfs = (HiRoomFeeService)TestUtil.getBean("hiRoomFeeService");
		List<Map<String , Object>> lst = rfs.findbyMonth("201609");
		System.out.println(JSON.toJSONString(lst));
		
	}
}
