package com.ant.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.business.SmsHistory;
import com.ant.vo.PersonVo;
import com.ant.vo.RoomVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.SmsHistoryService;

@Controller
@RequestMapping(value="/service/sms")
@Scope(value = "prototype")
public class SMSHistoryController extends BaseController
{
	private static Log log = LogFactory.getLog(SMSHistoryController.class);

	@Resource
	private SmsHistoryService smsHistoryService ;
	
	@RequestMapping(value="/querysmshistory")
	public @ResponseBody Map<String , Object> querysmshistory(@ModelAttribute("roomVo")RoomVo room , String phonenumber , String starttime , String endtime)
	{
		try
		{
			PersonVo sessionPersonVo = super.getCurrentUser();
			room.setPersonId(sessionPersonVo.getId());
			
			BasicScroll scroll = new BasicScroll(room.getPage(), room.getRows());
			List<SmsHistory> lst = smsHistoryService.find(room,phonenumber , starttime ,endtime, scroll);
			
			return createResponse(0 , getRB().getString("jquerysuccess") , scroll.getRows() , lst);
		}		
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}

	public void setSmsHistoryService(SmsHistoryService smsHistoryService)
	{
		this.smsHistoryService = smsHistoryService;
	}
}
