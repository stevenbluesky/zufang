package com.ant.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.isurpass.common.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.business.RoomPrivilege;
import com.ant.vo.RoomPrivilegeVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiRoomPrivilegeService;

@Controller
@RequestMapping(value="/service/roomprivilege")
@Scope(value = "prototype")
public class RoomPrivilegeController extends BaseController{

	private static Log log = LogFactory.getLog(RoomPrivilegeController.class);
	
	@Resource
	private HiRoomPrivilegeService hiRoomPrivilegeService ;

	@RequestMapping(value="/saveRoomPrivilege")
	public @ResponseBody Map<String , Object> saveRoom(@ModelAttribute("roomprivliege")RoomPrivilege roompriviliege){
		try{
			if(roompriviliege.getGrantBeginDate() == null
					|| roompriviliege.getGrantEndDate() == null  
					|| roompriviliege.getGrantUserName() == null
					|| roompriviliege.getGrantRealName() == null ){
				throw new BusinessException(getRB().getString("rpcauthetc"));
			}

			hiRoomPrivilegeService.saveRoomPrivilege(roompriviliege,super.getCurrentUser(),getRB());
			
			return createResponse(1 , getRB().getString("jaddsuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteRoomPrivilege")
	public @ResponseBody Map<String , Object> deleteRoomPrivilege(@ModelAttribute("roomprivliege")RoomPrivilege roompriviliege){
		try{
			hiRoomPrivilegeService.deleteRoomPrivilege(roompriviliege,super.getCurrentUser(),getRB() );
			
			return createResponse(1 , getRB().getString("jsave") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("roomVo")RoomPrivilegeVo roompriviliege){
		try{
			if(roompriviliege.getRoomId() == null ){
				throw new BusinessException(getRB().getString("rpcroom"));
			}
			
			BasicScroll scroll = new BasicScroll(roompriviliege.getPage(), roompriviliege.getRows());
			List<RoomPrivilege> lst = hiRoomPrivilegeService.findPage(roompriviliege, scroll);
			
			return createResponse(1 , getRB().getString("jquerysuccess") , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
}
