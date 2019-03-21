package com.isurpass.service;

import java.util.Date;
import java.util.List;
import com.ant.business.CommuLog;
import com.ant.vo.CommuLogVo;
import com.isurpass.common.hibernate.BasicScroll;

public class HiCommuLogService extends BaseService<CommuLog>{

	public void saveCommuLog(String commuText,Long deviceId, Long personId,Integer operatePersonId,Integer type1,Integer type2){
		CommuLog newCommuLog = new CommuLog();
		newCommuLog.setCommuText(commuText);
		newCommuLog.setPersonId(personId);
		newCommuLog.setOperatepersonid(operatePersonId);
		newCommuLog.setDeviceId(deviceId);
		newCommuLog.setInputDate(new Date());
		newCommuLog.setType1(type1);
		newCommuLog.setType2(type2);
		super.save(newCommuLog);
	}
	
	public List<CommuLog> findPage(CommuLogVo commuLogVo,BasicScroll scroll){
		return super.findPage(commuLogVo.getPersonId(), commuLogVo.getDeviceId(), commuLogVo.getQueryFlag(), "inputDate", scroll);
	}
	
}
