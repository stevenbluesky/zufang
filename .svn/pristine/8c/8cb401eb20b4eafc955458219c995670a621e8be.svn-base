package com.ant.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.isurpass.common.exception.BusinessException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ant.business.Device;
import com.ant.util.StringUtil;
import com.ant.vo.DeviceVo;
import com.ant.vo.PersonVo;
import com.ant.vo.PowerPriceLogVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiCommuLogService;
import com.isurpass.service.HiDeviceService;
import com.isurpass.service.HiOpenDeviceLogService;
import com.isurpass.service.HiPowerPriceLogService;

/**
 * 设备
 * @author aijian
 *
 */
@Controller
@RequestMapping(value="/service/device")
@Scope(value = "prototype")
public class DeviceController extends BaseController{

	private static Log log = LogFactory.getLog(DeviceController.class);
	
	@Resource
	private HiDeviceService hiDeviceService;
	@Resource
	private HiCommuLogService hiCommuLogService;
	@Resource
	private HiOpenDeviceLogService hiOpenDeviceLogService;
	@Resource
	private HiPowerPriceLogService hiPowerPriceLogService;

	/**
	  * @Description: 按网关分页查询门锁
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPageByGatewayId")
	public @ResponseBody Map<String , Object> findPageByGatewayId(@ModelAttribute("device")Device device){
		try{
			Integer page = StringUtil.objectToInteger(request.getParameter("page"));
			Integer rows = StringUtil.objectToInteger(request.getParameter("rows"));
			
			if(device.getGatewayId() == null || page == null || rows == null){
				throw new BusinessException(getRB().getString("gatewayidpagerows"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			DeviceVo deviceVo = new DeviceVo();
			deviceVo.setGatewayId(device.getGatewayId());
			deviceVo.setPersonId(sessionPersonVo.getId());
			
			BasicScroll scroll = new BasicScroll(page,rows);
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			List<DeviceVo> lst = hiDeviceService.findPage(deviceVo,scroll,idList);
			
			return createResponse(1 , "" , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 分页查询门锁
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("deviceVo")DeviceVo deviceVo){
		try{
			
			if(deviceVo.getPage() == null || deviceVo.getRows() == null){
				throw new BusinessException(getRB().getString("pagerows"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			deviceVo.setPersonId(sessionPersonVo.getId());
			
			BasicScroll scroll = new BasicScroll(deviceVo.getPage(),deviceVo.getRows());
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			List<DeviceVo> lst = hiDeviceService.findPage(deviceVo,scroll,idList);
			
			return createResponse(1 , "" , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 统计门锁
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/report")
	public @ResponseBody Map<String , Object> report(@ModelAttribute("deviceVo")DeviceVo deviceVo){
		try{
			
			if(deviceVo.getPage() == null || deviceVo.getRows() == null || deviceVo.getNoticeFlag() == null)
				throw new BusinessException(getRB().getString("pagerowsnoticeflag"));
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			deviceVo.setPersonId(sessionPersonVo.getId());
			
			BasicScroll scroll = new BasicScroll(deviceVo.getPage(),deviceVo.getRows());
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			List<DeviceVo> lst = hiDeviceService.findPage(deviceVo,scroll,idList);
			Map<String , Object> returnMap = new HashMap<String , Object>();
			returnMap.put("sumDeviceCount", hiDeviceService.findCountByPersonId(sessionPersonVo.getId(),idList));
			returnMap.put("deviceCount", scroll.getRows());
			returnMap.put("device", createResponse(scroll.getRows() , lst));

			return createResponse(1 , getRB().getString("checksuccess") , returnMap);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 初始化门锁信息
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/initDevice")
	public @ResponseBody Map<String , Object> initDevice(@ModelAttribute("deviceVo")DeviceVo deviceVo){
		try{
			
			if(deviceVo.getId() == null)
				throw new BusinessException(getRB().getString("id"));
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			DeviceVo dbDeviceVo = hiDeviceService.initDevice(deviceVo.getId());
			DeviceVo.checkSecondAdminRole(dbDeviceVo, sessionPersonVo);

			return createResponse(1 , getRB().getString("checksuccess") , dbDeviceVo,isZwaveLock(dbDeviceVo));
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	private boolean isZwaveLock(DeviceVo device){
		if(device!=null&&StringUtils.isNotBlank(device.getProductor())&&device.getProductor().length()>4){
			return true;
		}
		return false;
	}
	/**
	  * @Description: 修改密码
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/updatePassword")
	public @ResponseBody Map<String , Object> updatePassword(@ModelAttribute("deviceVo")DeviceVo deviceVo){
		boolean success = false;
		PersonVo sessionPersonVo = super.getCurrentUser();
		try{
			if(deviceVo.getId() == null  || deviceVo.getPasswordFlag() == null){
				throw new BusinessException(getRB().getString("idpassword"));
			}
			
			String phone = StringUtil.objectToString(request.getParameter("phone"));
			if(deviceVo.getPasswordFlag() == 0 &&  StringUtil.checkNull(deviceVo.getPassword())){
				throw new BusinessException(getRB().getString("password"));
			}
			hiDeviceService.updatePassword(deviceVo,phone,sessionPersonVo,getRB());
			success = true;
			return createResponse(1 , getRB().getString("modifysuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}finally{
			long personid = 0;
			int operatepersonid = sessionPersonVo.getId().intValue();
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				personid = sessionPersonVo.getSuperpersonid().longValue();
			}else{
				personid = sessionPersonVo.getId();
			}
			/**保存通讯记录*/
			String text = "";
			if(success){
				text = "成功";
				if("FFFFFF".equals(deviceVo.getPassword())){
					hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
							+ "设置用户密码失效" + text, deviceVo.getId(),personid,operatepersonid,8,1);
				}else{
					hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
							+ "修改用户密码" + text, deviceVo.getId(), personid,operatepersonid,7,1);
				}
			}else{
				text = "失败";
				if("FFFFFF".equals(deviceVo.getPassword())){
					hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
							+ "设置用户密码失效" + text, deviceVo.getId(), personid,operatepersonid,8,0);
				}else{
					hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
							+ "修改用户密码" + text, deviceVo.getId(), personid,operatepersonid,7,0);
				}
			}	
		}
	}
	
	/**
	  * @Description: 修改临时密码
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/updateTempPassword")
	public @ResponseBody Map<String , Object> updateTempPassword(@ModelAttribute("deviceVo")DeviceVo deviceVo){
		boolean success = false;
		Device dbDevice = new Device();
		PersonVo sessionPersonVo = super.getCurrentUser();
		try{
			String phone = StringUtil.objectToString(request.getParameter("phone"));
			if(deviceVo.getId() == null || deviceVo.getTempPasswordFlag() == null)
				throw new BusinessException(getRB().getString("jidtemporarycode"));

			dbDevice = hiDeviceService.updateTempPassword(deviceVo,phone,sessionPersonVo,getRB());
			
			success = true;
			return createResponse(1 , getRB().getString("modifysuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}finally{
			long personid = 0;
			int operatepersonid = sessionPersonVo.getId().intValue();
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				personid = sessionPersonVo.getSuperpersonid().longValue();
			}else{
				personid = sessionPersonVo.getId();
			}
			/**保存通讯记录*/
			String text = "";
			if(success){
				text = "成功";
				if("FFFFFF".equals(deviceVo.getTempPassword())){
					hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
							+ "设置临时密码失效" + text, dbDevice.getId(), personid,operatepersonid,6,1);
				}else{
					hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
							+ "修改临时密码" + text, dbDevice.getId(), personid,operatepersonid,5,1);
				}
			}else{
				text = "失败";
				if("FFFFFF".equals(deviceVo.getTempPassword())){
					hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
							+ "设置临时密码失效" + text, dbDevice.getId(), personid,operatepersonid,6,0);
				}else{
					hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
							+ "修改临时密码" + text, dbDevice.getId(), personid,operatepersonid,5,0);
				}
			}	
		}
	}
	
	/**
	  * @Description: 开锁
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/openDevice")
	public @ResponseBody Map<String , Object> openDevice(@ModelAttribute("deviceVo")DeviceVo deviceVo){
		boolean success = false;
		Device dbDevice = new Device();
		PersonVo sessionPersonVo = super.getCurrentUser();
		try{
			if(deviceVo.getId() == null){
				throw new BusinessException(getRB().getString("id"));
			}
			
			dbDevice = hiDeviceService.openDevice(deviceVo.getId(), sessionPersonVo,getRB());
			
			success = true;
			return createResponse(1 , getRB().getString("jopensuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}finally{
			long personid = 0;
			int operatepersonid = sessionPersonVo.getId().intValue();
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				personid = sessionPersonVo.getSuperpersonid().longValue();
			}else{
				personid = sessionPersonVo.getId();
			}
			/**保存通讯记录*/
			String text = "";
			if(success){
				text = "成功";
				hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
						+ "打开设备" + text, deviceVo.getId(), personid,operatepersonid,1,1);
			}else{
				text = "失败";
				hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
						+ "打开设备" + text, deviceVo.getId(), personid,operatepersonid,1,0);
			}				
			if(success){
				hiOpenDeviceLogService.saveOpenDeviceLog(sessionPersonVo.getRealName(), deviceVo.getId(), personid,operatepersonid, "打开设备成功",new Date(),"doorlockopen",6,sessionPersonVo.getRealName());
			}
			
		}
	}
	
	/**
	  * @Description: 关闭
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/closeDevice")
	public @ResponseBody Map<String , Object> closeDevice(@ModelAttribute("deviceVo")DeviceVo deviceVo){
		boolean success = false;
		Device dbDevice = new Device();
		PersonVo sessionPersonVo = super.getCurrentUser();
		try{
			if(deviceVo.getId() == null){
				throw new BusinessException(getRB().getString("id"));
			}
			
			dbDevice = hiDeviceService.closeDevice(deviceVo.getId(), sessionPersonVo,getRB());
			
			success = true;
			return createResponse(1 , getRB().getString("jclosesuccess") , null);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}finally{
			long personid = 0;
			int operatepersonid = sessionPersonVo.getId().intValue();
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				personid = sessionPersonVo.getSuperpersonid().longValue();
			}else{
				personid = sessionPersonVo.getId();
			}
			/**保存通讯记录*/
			String text = "";
			if(success){
				text = "成功";
				hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
						+ "关闭设备" + text, dbDevice.getId(), personid,operatepersonid,2,1);
			}else{
				text = "失败";
				hiCommuLogService.saveCommuLog("您于" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
						+ "关闭设备" + text, dbDevice.getId(), personid,operatepersonid,2,0);
			}			
			if(success){
				hiOpenDeviceLogService.saveOpenDeviceLog(sessionPersonVo.getRealName(), deviceVo.getId(), personid,operatepersonid, "关闭设备成功",new Date(),"doorlockclose",6,sessionPersonVo.getRealName());
			}
			
		}
	}
	
	/**
	  * @Description: 统计用电情况
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/reportDeviceDegreesLog")
	public @ResponseBody Map<String , Object> reportDeviceDegreesLog(@ModelAttribute("powerPriceLogVo")PowerPriceLogVo powerPriceLogVo){
		try{
			if(powerPriceLogVo.getPage() == null || powerPriceLogVo.getRows() == null )
				throw new BusinessException(getRB().getString("pagerowsdeviceid"));
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			powerPriceLogVo.setPersonId(sessionPersonVo.getId());
			BasicScroll scroll = new BasicScroll(powerPriceLogVo.getPage(),powerPriceLogVo.getRows());
			List<PowerPriceLogVo> lst = hiPowerPriceLogService.findPage(powerPriceLogVo,scroll);
			
			return super.createResponse(1, getRB().getString("jquerysuccess"), scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	
}
