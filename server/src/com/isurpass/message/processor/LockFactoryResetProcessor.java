package com.isurpass.message.processor;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.ant.business.Device;
import com.isurpass.service.HiDeviceService;
import com.isurpass.service.HiLockPasswordService;
import com.isurpass.service.HiOperateLogService;

public class LockFactoryResetProcessor implements IMessageProcessor{
	private static Log log = LogFactory.getLog(AddLockuserResultProcessor.class);

	@Resource
	private HiLockPasswordService hilockpasswordService;
	@Resource
	private HiDeviceService hideviceservice;
	@Resource
	private HiOperateLogService hiOperateLogService;
	@Override
	public void process(JSONObject message) {
		String zwavedeviceid = message.getString("zwavedeviceid");
		Device device = hideviceservice.findByPtDeviceId(zwavedeviceid);
		if(device==null){
			log.error("(lockfactoryreset)can not find the device by zwavedeviceid:"+zwavedeviceid);
			return ;
		}
		hilockpasswordService.deleteByZwavedeviceid(device.getId().intValue());
		hiOperateLogService.saveOperateLog(24,"门锁【"+ device.getDeviceName() + "】复位", "",device.getPersonId(),null,"",
				 device.getId(),device.getDeviceName(),"","");
	}
}
