package com.ant.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ant.util.StringUtil;
import com.isurpass.common.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.business.Manager;
import com.ant.vo.ManagerVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiManagerService;

/**
 * 管理员
 * @author aijian
 *
 */
@Controller
@RequestMapping(value="/service/manager")
@Scope(value = "prototype")
public class ManagerController extends BaseController{

	private static Log log = LogFactory.getLog(ManagerController.class);
	
	@Resource
	private HiManagerService hiManagerService;

	/**
	  * @Description: 新增小区管理员
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/saveManager")
	public @ResponseBody Map<String , Object> saveManager(@ModelAttribute("manager")Manager manager){
		try{
			if(manager.getDistrictId() == null || StringUtil.checkNull(manager.getLoginPassword()) ){
				throw new BusinessException(getRB().getString("acommunityid"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			hiManagerService.saveManager(manager, sessionPersonVo,getRB());
			return createResponse(1 , getRB().getString("jaddsuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 修改小区管理员
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/updateManager")
	public @ResponseBody Map<String , Object> updateManager(@ModelAttribute("manager")Manager manager){
		try{
			if(manager.getId() == null||  StringUtil.checkNull(manager.getLoginPassword()) )
				throw new BusinessException(getRB().getString("aidid"));
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			hiManagerService.updateManager(manager, sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jsave") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 分页查询小区管理员
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("managerVo")ManagerVo managerVo){
		try{
			if(managerVo.getDistrictId() == null || managerVo.getPage() == null || managerVo.getRows() == null){
				throw new BusinessException(getRB().getString("acom"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			managerVo.setPersonId(sessionPersonVo.getId());
			BasicScroll scroll = new BasicScroll(managerVo.getPage(), managerVo.getRows());
			List<Manager> lst = hiManagerService.findPage(managerVo, scroll);
			
			return createResponse(1 , getRB().getString("jquerysuccess") , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 根据id查询小区管理员
	  * @Author: aijian
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findManagerById")
	public @ResponseBody Map<String , Object> findManagerById(@ModelAttribute("managerVo")ManagerVo managerVo){
		try{
			if(managerVo.getId() == null)
				throw new BusinessException(getRB().getString("aid"));
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			Manager dbManager = hiManagerService.findById(managerVo.getId());
			Manager.checkSecondAdminRole(dbManager, sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jquerysuccess") , dbManager);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
}
