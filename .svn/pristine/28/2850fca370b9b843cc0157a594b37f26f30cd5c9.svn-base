package com.isurpass.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;

import com.ant.business.SmsHistory;
import com.ant.constant.SmsStatus;
import com.ant.util.Utils;
import com.ant.vo.RoomVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.common.hibernate.CriteriaWrap;
import com.isurpass.common.hibernate.ExpWrap;

public class SmsHistoryService extends BaseService<SmsHistory>
{
	public void saveSmsHistory(SmsHistory sms) 
	{
		super.save(sms);
	}

	public void hasSent(Long smshisotryid) 
	{
		SmsHistory sms = (SmsHistory)super.findOneByProperty("smshistoryid", smshisotryid);
		hasSent(sms);
	}
	
	public void hasSent(SmsHistory sms)
	{
		if ( sms == null )
			return ;
		sms.setStatus(SmsStatus.hassent.getStatus());
		sms.setSendtime(new Date());
		
		super.update(sms);
	}
	
	public List<SmsHistory> findnewSms()
	{
		return super.findByProperty( "status", SmsStatus.newsms.getStatus());
	}
	
	public List<SmsHistory> find(RoomVo room, String phonenumber, String starttime , String endtime ,BasicScroll scroll )
	{
		CriteriaWrap cw = super.createCriteriaWrap();
		cw.addifNotNull(ExpWrap.eq("personId", room.getPersonId()));
		cw.addifNotNull(ExpWrap.eq("districtId", room.getDistrictId()));
		cw.addifNotNull(ExpWrap.eq("build", room.getBuild()));
		cw.addifNotNull(ExpWrap.eq("unit", room.getUnit()));
		cw.addifNotNull(ExpWrap.eq("floor", room.getFloor()));
		cw.addifNotNull(ExpWrap.like("roomName", room.getRoomName()));
		cw.addifNotNull(ExpWrap.like("phonenumber", phonenumber));
		if ( StringUtils.isNotBlank(starttime))
			cw.addifNotNull(ExpWrap.ge("sendtime", Utils.parseTime(starttime)));
		if ( StringUtils.isNotBlank(endtime))
			cw.addifNotNull(ExpWrap.le("sendtime", Utils.parseTime(endtime)));
		cw.setScroll(scroll);
		cw.addOrder(Order.desc("smshistoryid"));
		return cw.list();
	}
}
