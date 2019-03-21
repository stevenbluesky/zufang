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

import com.ant.business.DeviceDegreesLog;
import com.ant.vo.DeviceDegreesLogVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiDeviceDegreesLogService;

/**
 * 设备读数历史
 *
 */
@Controller
@RequestMapping(value="/service/deviceDegreesLog")
@Scope(value = "prototype")
public class DeviceDegreesLogController extends BaseController{

	private static Log log = LogFactory.getLog(DeviceDegreesLogController.class);
	@Resource
	private HiDeviceDegreesLogService hiDeviceDegreesLogService;


	/**
	  * @Description: 分页查询读数历史
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("deviceDegreesLogVo")DeviceDegreesLogVo deviceDegreesLogVo)
	{
		try
		{
			if(deviceDegreesLogVo.getPage() == null || deviceDegreesLogVo.getRows() == null || deviceDegreesLogVo.getDeviceId() == null)
				throw new BusinessException(getRB().getString("pagerowsdeviceid"));
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				deviceDegreesLogVo.setPersonId(sessionPersonVo.getSuperpersonid().longValue());
			}else{
				deviceDegreesLogVo.setPersonId(sessionPersonVo.getId());
			}
			BasicScroll scroll = new BasicScroll(deviceDegreesLogVo.getPage() , deviceDegreesLogVo.getRows());
			List<DeviceDegreesLog> lst = hiDeviceDegreesLogService.findPage(deviceDegreesLogVo,scroll);
			
			return createResponse(1 , "" , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
}
