package com.isurpass.message.processor;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.util.StringUtil;
import com.ant.util.Utils;
import com.ant.vo.DeviceVo;
import com.isurpass.message.vo.Event;
import com.isurpass.service.HiDeviceService;

public class DoorLockOpenProcessor implements IMessageProcessor
{
	@Resource
	private HiDeviceService hiDeviceService;
	
	@Override
	public void process(JSONObject message)
	{
		Event event = JSON.toJavaObject(message,Event.class);
		event.setEventtime(Utils.parseTime(message.getString("newtime")));
		
		hiDeviceService.doorlockOpenEvent(event);
		
	}


}
