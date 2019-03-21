package com.isurpass.message.processor;

import javax.annotation.Resource;

import com.ant.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.vo.DeviceVo;
import com.isurpass.service.HiDeviceService;

public class UnalarmTamplerAlarm implements IMessageProcessor
{
	private static Log log = LogFactory.getLog(UnalarmTamplerAlarm.class);
	
	@Resource
	private HiDeviceService hiDeviceService;

	@Override
	public void process(JSONObject message)
	{
		DeviceVo deviceVo = JSON.toJavaObject(message,DeviceVo.class);
		if(StringUtil.checkNull(deviceVo.getPtDeviceId()))
		{
			log.error("Invalid Parameter");
			return ;
		}
		
		hiDeviceService.unalarmtampleralarm(deviceVo.getPtDeviceId());

	}

}
