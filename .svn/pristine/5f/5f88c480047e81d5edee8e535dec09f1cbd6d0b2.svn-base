package com.isurpass.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ant.business.Device;
import com.ant.business.DeviceDegreesHourLog;
import com.ant.util.Utils;
import com.ant.vo.DeviceDegreesHourLogVo;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;
import com.isurpass.common.hibernate.HqlWrap;


/**
 * 电表度数时变更service
 *
 */
public class HiDeviceDegreesHourLogService extends BaseService<DeviceDegreesHourLog>
{

	/**
	 * 保存电表度数
	 */
	public void saveDeviceDegreesHourLog(Device dbDevice,Float currentDegrees,String hour)
	{
		float value =currentDegrees - dbDevice.getCurrentDegrees(); 
		if(value < 0)
			value = 0;
		
		DeviceDegreesHourLog dbDeviceDegreesHourLog = this.findDeviceDegreesHourLog(dbDevice.getId(), hour);
		if(dbDeviceDegreesHourLog == null)
		{
			dbDeviceDegreesHourLog = new DeviceDegreesHourLog();
			dbDeviceDegreesHourLog.setDegrees(value);
			dbDeviceDegreesHourLog.setDeviceId(dbDevice.getId());
			dbDeviceDegreesHourLog.setHour(hour);
			dbDeviceDegreesHourLog.setPersonId(dbDevice.getPersonId());
			dbDeviceDegreesHourLog.setInputDate(new Date());
			super.save(dbDeviceDegreesHourLog);
		}
		else
		{
			dbDeviceDegreesHourLog.setDegrees(dbDeviceDegreesHourLog.getDegrees() + value);
			super.update(dbDeviceDegreesHourLog);
		}
	}
	
	/**
	 * 查询日志
	 * @param deviceId
	 * @param hour
	 * @return
	 */
	public DeviceDegreesHourLog findDeviceDegreesHourLog(Long deviceId,String hour)
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.add(ExpWrap.eq("deviceId", deviceId));
		cw.add(ExpWrap.eq("hour", hour));
		
		return cw.uniqueResult();
	}
	
	/**
	 * 按小区、按月电量
	 * @param deviceDegreesHourLogVo
	 * @return
	 */
	public List<Map> reportByMonth(DeviceDegreesHourLogVo deviceDegreesHourLogVo)
	{
		HqlWrap hw = new HqlWrap(super.sessionFactory);
		hw.add("select substring(ddl.hour ,1,6) as reportMonth,ROUND(SUM(ddl.degrees),2) as degrees from ");
		hw.add("DeviceDegreesHourLog ddl , Device d , Room r , District di ");
		hw.add("where ddl.deviceId = d.id and d.bindRoomId = r.id and r.districtId = di.id and d.deviceType = 1 ");
		hw.addifnotnull("and r.districtId = ? ", deviceDegreesHourLogVo.getDistrictId());
		hw.addifnotnull("and r.build = ? ", deviceDegreesHourLogVo.getBuild());
		hw.addifnotnull("and r.unit = ? ", deviceDegreesHourLogVo.getUnit());
		hw.addifnotnull("and r.floor = ? ", deviceDegreesHourLogVo.getFloor());
		hw.addifnotnull("and r.id = ? ", deviceDegreesHourLogVo.getRoomId());
		if ( deviceDegreesHourLogVo.getBeginMonth() != null )
			hw.addifnotnull("and ddl.hour >= ? ", deviceDegreesHourLogVo.getBeginMonth());
		if ( deviceDegreesHourLogVo.getEndMonth() != null )
			hw.addifnotnull("and ddl.hour <= ? ", createEndTime(deviceDegreesHourLogVo.getEndMonth()));
		hw.add("group by substring(ddl.hour ,1,6) ");
		
		hw.setResultBeanClass(Map.class);
		return hw.list();
	}

	private String createEndTime(String endtime)
	{
		Date d = Utils.parseTime(endtime , "yyyyMM");
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		
		return Utils.formatTime(c.getTime() , "yyyyMMdd");
	}
	
	/**
	 * 按小区、按日电量
	 * @param deviceDegreesHourLogVo
	 * @return
	 */
	public List<Map> reportByDay(DeviceDegreesHourLogVo deviceDegreesHourLogVo)
	{
		HqlWrap hw = new HqlWrap(super.sessionFactory);
		hw.add("select substring(ddl.hour ,1,8) as reportDay,ROUND(SUM(ddl.degrees),2) as degrees ");
		hw.add("from DeviceDegreesHourLog ddl , Device d , Room r , District di ");
		hw.add("where ddl.deviceId = d.id and d.bindRoomId = r.id and r.districtId = di.id ");
		hw.addifnotnull("and r.districtId = ? ", deviceDegreesHourLogVo.getDistrictId());
		hw.addifnotnull("and r.build = ? ", deviceDegreesHourLogVo.getBuild());
		hw.addifnotnull("and r.unit = ? ", deviceDegreesHourLogVo.getUnit());
		hw.addifnotnull("and r.floor = ? ", deviceDegreesHourLogVo.getFloor());
		hw.addifnotnull("and r.id = ? ", deviceDegreesHourLogVo.getRoomId());
		if ( deviceDegreesHourLogVo.getBeginMonth() != null )
			hw.addifnotnull("and ddl.hour >= ? ", deviceDegreesHourLogVo.getBeginMonth());
		if ( deviceDegreesHourLogVo.getEndMonth() != null )
			hw.addifnotnull("and ddl.hour <= ? ", createEndTime(deviceDegreesHourLogVo.getEndMonth()));
		hw.add("group by substring(ddl.hour ,1,8) ");
		hw.setResultBeanClass(Map.class);
		return hw.list();
	}
	
	
	/**
	 * 按小区、按时电量
	 * @param deviceDegreesHourLogVo
	 * @return
	 */
	public List<Map> reportByHour(DeviceDegreesHourLogVo deviceDegreesHourLogVo)
	{
		HqlWrap hw = new HqlWrap(super.sessionFactory);
		hw.add("select ddl.hour as reportDay,ROUND(SUM(ddl.degrees),2) as degrees ");
		hw.add("from DeviceDegreesHourLog ddl , Device d , Room r , District di ");
		hw.add("where ddl.deviceId = d.id and d.bindRoomId = r.id and r.districtId = di.id ");
		hw.addifnotnull("and r.districtId = ? ", deviceDegreesHourLogVo.getDistrictId());
		hw.addifnotnull("and r.build = ? ", deviceDegreesHourLogVo.getBuild());
		hw.addifnotnull("and r.unit = ? ", deviceDegreesHourLogVo.getUnit());
		hw.addifnotnull("and r.floor = ? ", deviceDegreesHourLogVo.getFloor());
		hw.addifnotnull("and r.id = ? ", deviceDegreesHourLogVo.getRoomId());
		if ( deviceDegreesHourLogVo.getBeginMonth() != null )
			hw.addifnotnull("and ddl.hour >= ? ", deviceDegreesHourLogVo.getBeginMonth());
		if ( deviceDegreesHourLogVo.getEndMonth() != null )
			hw.addifnotnull("and ddl.hour <= ? ", createEndTime(deviceDegreesHourLogVo.getEndMonth()));
		hw.add("group by ddl.hour  ");
		hw.setResultBeanClass(Map.class);
		return hw.list();
	}
	
}
