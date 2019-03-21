package com.isurpass.message.processor;

import java.util.Date;

import javax.annotation.Resource;
import com.ant.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.ant.util.Utils;
import com.isurpass.service.HiDeviceDegreesLogService;

public class DeviceStatusProcessor implements IMessageProcessor
{
	private static Log log = LogFactory.getLog(DeviceStatusProcessor.class);
	@Resource
	private HiDeviceDegreesLogService hiDeviceDegreesLogService;

	@Override
	public void process(JSONObject message)
	{
		String zwavedeviceid = StringUtil.objectToString(message.getString("zwavedeviceid"));
		String floatparmStr = StringUtil.objectToString(message.getString("floatparm"));
		if(StringUtil.checkNull(zwavedeviceid) || StringUtil.checkNull(message.getString("newtime")))
		{
			log.error("Invalid Parameter");
			return ;
		}
		if ( StringUtil.checkNotNull(floatparmStr) )
		{
			Float floatparm = Float.parseFloat(floatparmStr);
			
			Date d = Utils.parseTime(message.getString("newtime"));
			hiDeviceDegreesLogService.saveDeviceDegreesLog(zwavedeviceid, floatparm , d);
		}
	}

}
