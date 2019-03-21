package com.isurpass.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ant.business.Device;
import com.ant.business.DeviceDegreesLog;
import com.ant.business.District;
import com.ant.vo.DeviceDegreesLogVo;
import com.isurpass.common.hibernate.BasicScroll;

/**
 * 电表度数变更service
 *
 */
public class HiDeviceDegreesLogService extends BaseService<DeviceDegreesLog>
{
	
	private static Log log = LogFactory.getLog(HiDeviceDegreesLogService.class);
	/**
	 * 保存电表度数
	 */
	public void saveDeviceDegreesLog(String zwavedeviceid,Float floatparm , Date time){
		
		if ( time == null )
			time = new Date();
		String month = new SimpleDateFormat("yyyyMM").format(time);
		String hour = new SimpleDateFormat("yyyyMMddHH").format(time);
		
		HiDeviceService hiDeviceService = super.createService(HiDeviceService.class);
		Device dbDevice = hiDeviceService.findByPtDeviceId(zwavedeviceid);
		
		if ( dbDevice == null )
		{
			log.error(String.format("device(%s) is null" , zwavedeviceid));
			return ;
		}
		
		/**保存电表度数变更日志*/
		DeviceDegreesLog newDeviceDegreesLog = new DeviceDegreesLog();
		newDeviceDegreesLog.setDeviceId(dbDevice.getId());
		newDeviceDegreesLog.setDegrees(floatparm);
		newDeviceDegreesLog.setInputDate(time);
		newDeviceDegreesLog.setPersonId(dbDevice.getPersonId());
		super.save(newDeviceDegreesLog);
		if(dbDevice.getBindRoomId() != null)
		{
			HiDistrictService hiDistrictService = super.createService(HiDistrictService.class);
			District dbDistrict = hiDistrictService.findDistrictByRoomId(dbDevice.getBindRoomId());
			/**保存电量日志*/
			HiPowerPriceLogService hiPowerPriceLogService = super.createService(HiPowerPriceLogService.class); 
			hiPowerPriceLogService.savePowerPriceLog(dbDistrict, dbDevice, month, floatparm);
			
			HiDeviceDegreesHourLogService hiDeviceDegreesHourLogService = super.createService(HiDeviceDegreesHourLogService.class);
			hiDeviceDegreesHourLogService.saveDeviceDegreesHourLog(dbDevice, floatparm, hour);
		}
		dbDevice.setCurrentDegrees(floatparm);
		dbDevice.setInitPreMonthDegrees(0);	//不需要初始化本月初始讀數
		hiDeviceService.update(dbDevice);
	}
	
	
	/**
	 * 分页查询读数变更
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<DeviceDegreesLog> findPage(DeviceDegreesLogVo deviceDegreesLogVo,BasicScroll scroll)
	{
		return super.findPage(deviceDegreesLogVo.getPersonId(), deviceDegreesLogVo.getDeviceId(), deviceDegreesLogVo.getQueryFlag(), "inputDate", scroll);
	}
	
}
