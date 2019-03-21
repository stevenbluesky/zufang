package com.isurpass.service;

import java.util.Date;
import java.util.List;

import com.ant.business.OpenDeviceLog;
import com.ant.vo.OpenDeviceLogVo;
import com.isurpass.common.hibernate.BasicScroll;

/**
 * 开锁记录service
 * @author aijian
 *
 */
public class HiOpenDeviceLogService extends BaseService<OpenDeviceLog>
{

	/**
	 * 保存开锁记录
	 * @param district
	 */
	public void saveOpenDeviceLog(String operateName,Long deviceId, Long personId,Integer operatepersonid,String result , Date time,String type,Integer type2,String msg1)
	{
		OpenDeviceLog newOpenDeviceLog = new OpenDeviceLog();
		newOpenDeviceLog.setOperateName(operateName);
		newOpenDeviceLog.setDeviceId(deviceId);
		newOpenDeviceLog.setPersonId(personId);
		newOpenDeviceLog.setOperatepersonid(operatepersonid);
		newOpenDeviceLog.setInputDate(time);
		newOpenDeviceLog.setResult(result);
		newOpenDeviceLog.setType(type);
		newOpenDeviceLog.setType2(type2);
		newOpenDeviceLog.setMsg1(msg1);
		super.save(newOpenDeviceLog);
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<OpenDeviceLog> findPage(OpenDeviceLogVo openDeviceLogVo,BasicScroll scroll)
	{
		return super.findPage(openDeviceLogVo.getPersonId(), openDeviceLogVo.getDeviceId(), openDeviceLogVo.getQueryFlag(), "inputDate", scroll);
	}
	
}
