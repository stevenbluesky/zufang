package com.isurpass.service;

import java.util.Date;
import java.util.List;

import com.ant.business.Device;
import com.ant.business.DeviceBatteryLog;
import com.ant.vo.DeviceBatteryLogVo;
import com.isurpass.common.hibernate.BasicScroll;

public class HiDeviceBatteryLogService extends BaseService<DeviceBatteryLog>
{
	public void saveDeviceBatteryLog(Device dbDevice,Integer battery)
	{
		DeviceBatteryLog newDeviceBatteryLog = new DeviceBatteryLog();
		newDeviceBatteryLog.setBattery(battery);
		newDeviceBatteryLog.setDeviceId(dbDevice.getId());
		newDeviceBatteryLog.setInputDate(new Date());
		newDeviceBatteryLog.setPersonId(dbDevice.getPersonId());
		super.save(newDeviceBatteryLog);
	}
	
	public List<DeviceBatteryLog> findPage(DeviceBatteryLogVo deviceBatteryLogVo,BasicScroll scroll)
	{
		return super.findPage(deviceBatteryLogVo.getPersonId(), deviceBatteryLogVo.getDeviceId(), deviceBatteryLogVo.getQueryFlag(), "inputDate", scroll);
	}
}
