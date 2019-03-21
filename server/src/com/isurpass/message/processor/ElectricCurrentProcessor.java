package com.isurpass.message.processor;

import javax.annotation.Resource;

import com.ant.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.config.MjConfig;
import com.ant.vo.DeviceVo;
import com.isurpass.service.HiDeviceService;

public class ElectricCurrentProcessor implements IMessageProcessor
{
	private static Log log = LogFactory.getLog(ElectricCurrentProcessor.class);
	
	@Resource
	private HiDeviceService hiDeviceService;

	@Override
	public void process(JSONObject message)
	{
		DeviceVo deviceVo = JSON.toJavaObject(message,DeviceVo.class);
		deviceVo.setPtDeviceId(message.getString("zwavedeviceid"));
		deviceVo.setCurrentPower(message.getFloat("floatparm"));
		if(StringUtil.checkNull(deviceVo.getPtDeviceId()) || deviceVo.getCurrentPower() == null)
		{
			log.error("Invalid Parameter");
			return ;
		}
		
		int voltage = Integer.parseInt(MjConfig.get("voltage"));
		deviceVo.setCurrentPower(deviceVo.getCurrentPower() * voltage);
		hiDeviceService.updateCurrentPower(deviceVo.getPtDeviceId(), deviceVo.getCurrentPower());

	}

}
