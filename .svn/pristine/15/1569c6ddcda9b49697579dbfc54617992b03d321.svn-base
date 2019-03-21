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

import com.ant.vo.PersonVo;
import com.ant.vo.RoomFeeVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiRoomFeeService;

@Controller
@RequestMapping(value="/service/userfee")
@Scope(value = "prototype")
public class UserFeeController extends BaseController
{
	private static Log log = LogFactory.getLog(UserFeeController.class);
	
	@Resource
	private HiRoomFeeService hiRoomFeeService;
	
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("roomFeeVo")RoomFeeVo roomfee){
		try{
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			roomfee.setPersonId(sessionPersonVo.getId());
			BasicScroll scroll = new BasicScroll(roomfee.getPage(), roomfee.getRows());
			List<RoomFeeVo> lst = hiRoomFeeService.findPage(roomfee, scroll);
			
			return createResponse(1 , getRB().getString("jquerysuccess") , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
}
