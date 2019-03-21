package com.isurpass.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.scheduling.annotation.Async;
import com.ant.business.OperateLog;
import com.ant.vo.OperateLogVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class HiOperateLogService extends BaseService<OperateLog>{
	
	@Async
	public void saveOperateLog(Integer operateType,String title,String operateName,
			Long personId,Integer operatepersonid,String ip,Long businessId,String msg1,String msg2,String msg3){
		OperateLog newOperateLog = new OperateLog();
		newOperateLog.setOperateType(operateType);
		newOperateLog.setTitle(title);
		newOperateLog.setOperateName(operateName);
		newOperateLog.setPersonId(personId);
		newOperateLog.setOperatepersonid(operatepersonid);
		newOperateLog.setIp(ip);
		newOperateLog.setBusinessId(businessId);
		newOperateLog.setInputDate(new Date());
		newOperateLog.setMsg1(msg1);
		newOperateLog.setMsg2(msg2);
		newOperateLog.setMsg3(msg3);
		super.save(newOperateLog);
	}
	public List<OperateLog> findPage(OperateLogVo operateLogVo,BasicScroll scroll)
	{
		CriteriaWrap cw = new CriteriaWrap(OperateLog.class.getName() , sessionFactory);
		cw.add(ExpWrap.eq("personId",  operateLogVo.getPersonId()));
		cw.addifNotNull(ExpWrap.like("operateName", operateLogVo.getOperateName()));
		cw.addifNotNull(ExpWrap.ge("inputDate", operateLogVo.getBeginDate()));
		
		if(operateLogVo.getEndDate() != null){
			Calendar c = Calendar.getInstance();
			c.setTime(operateLogVo.getEndDate());
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			cw.add(ExpWrap.le("inputDate",  c.getTime()));
		}
		cw.setScroll(scroll);
		cw.addOrder(Order.desc("inputDate"));
		
		return cw.list();
	}

}
