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

import com.ant.business.DeviceBatteryLog;
import com.ant.vo.DeviceBatteryLogVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.exception.BusinessException;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiDeviceBatteryLogService;

/**
 * 设备电量历史
 *
 */
@Controller
@RequestMapping(value="/service/deviceBattery")
@Scope(value = "prototype")
public class DeviceBatteryController extends BaseController{

	private static Log log = LogFactory.getLog(DeviceBatteryController.class);
	@Resource
	private HiDeviceBatteryLogService hiDeviceBatteryLogService;
	
	/**
	  * @Description: 分页查询电量历史
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("deviceBatteryLogVo")DeviceBatteryLogVo deviceBatteryLogVo)
	{
		try
		{
			if(deviceBatteryLogVo.getPage() == null || deviceBatteryLogVo.getRows() == null || deviceBatteryLogVo.getDeviceId() == null)
			{
				throw new BusinessException(getRB().getString("pagerowsdeviceid"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			deviceBatteryLogVo.setPersonId(sessionPersonVo.getId());
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				deviceBatteryLogVo.setPersonId(sessionPersonVo.getSuperpersonid().longValue());
			}else{
				deviceBatteryLogVo.setPersonId(sessionPersonVo.getId());
			}
			BasicScroll scroll = new BasicScroll(deviceBatteryLogVo.getPage() , deviceBatteryLogVo.getRows());
			List<DeviceBatteryLog> lst = hiDeviceBatteryLogService.findPage(deviceBatteryLogVo,scroll);
			
			return createResponse(1 , "" , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
}