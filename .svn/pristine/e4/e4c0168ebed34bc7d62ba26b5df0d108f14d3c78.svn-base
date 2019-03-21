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

import com.ant.vo.DeviceDegreesHourLogVo;
import com.isurpass.service.HiDeviceDegreesHourLogService;

/**
 * 设备读数历史
 *
 */
@Controller
@RequestMapping(value="/service/deviceDegreesHourLog")
@Scope(value = "prototype")
public class DeviceDegreesHourLogController extends BaseController{

	private static Log log = LogFactory.getLog(DeviceDegreesHourLogController.class);
	@Resource
	private HiDeviceDegreesHourLogService hiDeviceDegreesHourLogService;

	/**
	  * @Description: 按月统计
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/reportByMonth")
	public @ResponseBody Map<String , Object> reportByMonth(@ModelAttribute("deviceDegreesHourLogVo")DeviceDegreesHourLogVo deviceDegreesHourLogVo)
	{
		try
		{
			List<Map> lst = hiDeviceDegreesHourLogService.reportByMonth(deviceDegreesHourLogVo);
			return createResponse(1 , getRB().getString("jquerysuccess") , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 按日统计
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/reportByDay")
	public @ResponseBody Map<String , Object> reportByDay(@ModelAttribute("deviceDegreesHourLogVo")DeviceDegreesHourLogVo deviceDegreesHourLogVo)
	{
		try{
			List<Map> lst = hiDeviceDegreesHourLogService.reportByDay(deviceDegreesHourLogVo);
			return createResponse(1 , "" ,  lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 按时统计
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/reportByHour")
	public @ResponseBody Map<String , Object> reportByHour(@ModelAttribute("deviceDegreesHourLogVo")DeviceDegreesHourLogVo deviceDegreesHourLogVo){
		try{
			List<Map> lst = hiDeviceDegreesHourLogService.reportByHour(deviceDegreesHourLogVo);
			return createResponse(1 , "" ,  lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
}
