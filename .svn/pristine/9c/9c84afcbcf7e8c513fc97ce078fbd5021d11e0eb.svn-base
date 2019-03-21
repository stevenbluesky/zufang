package com.isurpass.message.processor;

import javax.annotation.Resource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.business.Device;
import com.ant.vo.LockNetworkInfoVo;
import com.isurpass.service.HiDeviceService;

public class LockNetworkInfoProcessor implements IMessageProcessor {

	@Resource
	private HiDeviceService hideviceservice;

	@Override
	public void process(JSONObject message) {
		LockNetworkInfoVo lockNetworkInfo = JSON.parseObject(message.getString("objparam"),LockNetworkInfoVo.class);
		
		String zwavedeviceid = message.getString("zwavedeviceid");
		int networkintensity = lockNetworkInfo.getNetworkintensity();
		
		Device device = hideviceservice.findByPtDeviceId(zwavedeviceid);
		if(device!=null){
			device.setSignalstrength(networkintensity);
			hideviceservice.update(device);
		}
	}

}
