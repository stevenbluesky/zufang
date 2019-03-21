package com.ant.restful.service;


import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.isurpass.common.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ant.config.MjConfig;
import com.ant.util.ResultUtil;
import com.ant.util.StringUtil;
import com.ant.vo.DeviceVo;
import com.isurpass.service.HiDeviceService;
import com.isurpass.service.HiOperateLogService;

/**
 * 设备
 * @author aijian
 *
 */
@Path("/device")
@Service
public class DeviceResource {
	
	private static Log log = LogFactory.getLog(DeviceResource.class);
	
	@Resource
	private HiDeviceService hiDeviceService;
	
	@Resource
	private HiOperateLogService hiOperateLogService;
	
	/**
	 * 保存通讯记录-开锁信息
	 * @return
	 */
	@POST
	@Path(value="saveOpenDevice")
	@Produces("application/json")
	public String saveOpenDevice(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			DeviceVo deviceVo = JSON.parseObject(paramjson,DeviceVo.class);
			if(StringUtil.checkNull(deviceVo.getPtDeviceId()) || StringUtil.checkNull(deviceVo.getOperateName())){
				throw new BusinessException(rb.getString("drdeviceid1"));
			}
			hiDeviceService.saveOpenDevice(deviceVo.getPtDeviceId(), deviceVo.getOperateName() , new Date(),rb);
			return ResultUtil.getSuccessInfo(rb.getString("drlogsave1"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("droperatefailed"));
		}
	}
	
	/**
	 * 关闭设备
	 * @return
	 */
	@POST
	@Path(value="closeDevice")
	@Produces("application/json")
	public String closeDevice(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			DeviceVo deviceVo = JSON.parseObject(paramjson,DeviceVo.class);
			if(StringUtil.checkNull(deviceVo.getPtDeviceId())){
				throw new BusinessException(rb.getString("drdeviceid2"));
			}
			hiDeviceService.updateOpenStatus(deviceVo.getPtDeviceId() , 1,new Date());
			return ResultUtil.getSuccessInfo(rb.getString("drlocksuccess"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("droperatefailed"));
		}
	}
	
	/**
	 * 更新电量
	 * @return
	 */
	@POST
	@Path(value="updateBattery")
	@Produces("application/json")
	public String updateBattery(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			DeviceVo deviceVo = JSON.parseObject(paramjson,DeviceVo.class);
			if(StringUtil.checkNull(deviceVo.getPtDeviceId()) || deviceVo.getBattery() == null){
				throw new BusinessException(rb.getString("drdeviceid3"));
			}
			hiDeviceService.updateBattery(deviceVo.getPtDeviceId(), deviceVo.getBattery());
			return ResultUtil.getSuccessInfo(rb.getString("updatesuccess"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("droperatefailed"));
		}
	}
	
	/**
	 * 更新网关信号
	 * @return
	 */
	@POST
	@Path(value="updateSignalFlag")
	@Produces("application/json")
	public String updateSignalFlag(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			DeviceVo deviceVo = JSON.parseObject(paramjson,DeviceVo.class);
			if(StringUtil.checkNull(deviceVo.getPtDeviceId()) || deviceVo.getSignalFlag() == null){
				throw new BusinessException(rb.getString("drptsbid1"));
			}
			
			hiDeviceService.updateSingnalFlag(deviceVo.getPtDeviceId(), deviceVo.getSignalFlag());
			return ResultUtil.getSuccessInfo(rb.getString("drxgcg"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("drxgsb"));
		}
	}
	
	/**
	 * 删除设备
	 * @return
	 */
	@POST
	@Path(value="deleteDevice")
	@Produces("application/json")
	public String deleteDevice(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			DeviceVo deviceVo = JSON.parseObject(paramjson,DeviceVo.class);
			if(StringUtil.checkNull(deviceVo.getPtDeviceId())){
				throw new BusinessException(rb.getString("drptsbid2"));
			}
			
			hiDeviceService.deleteDevice(deviceVo.getPtDeviceId());
			return ResultUtil.getSuccessInfo(rb.getString("drsccg"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("drscsb"));
		}
	}
	
	
	/**
	 * 设备被拆卸
	 * @return
	 */
	@POST
	@Path(value="tampleralarm")
	@Produces("application/json")
	public String tampleralarm(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			DeviceVo deviceVo = JSON.parseObject(paramjson,DeviceVo.class);
			if(StringUtil.checkNull(deviceVo.getPtDeviceId())){
				throw new BusinessException(rb.getString("drptsbid2"));
			}
			
			hiDeviceService.tampleralarm(deviceVo.getPtDeviceId());	//设备拆卸
			
			return ResultUtil.getSuccessInfo(rb.getString("droperatesuccess"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("droperatefailed"));
		}
	}
	
	/**
	 * 设备被拆卸解除
	 * @return
	 */
	@POST
	@Path(value="unalarmtampleralarm")
	@Produces("application/json")
	public String unalarmtampleralarm(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			DeviceVo deviceVo = JSON.parseObject(paramjson,DeviceVo.class);
			if(StringUtil.checkNull(deviceVo.getPtDeviceId())){
				throw new BusinessException(rb.getString("drptsbid2"));
			}
			
			hiDeviceService.unalarmtampleralarm(deviceVo.getPtDeviceId());	//设备拆卸
			
			return ResultUtil.getSuccessInfo(rb.getString("droperatesuccess"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("droperatefailed"));
		}
	}
	
	/**
	 * 更新设备的当前功率
	 * @return
	 */
	@POST
	@Path(value="updateCurrentPower")
	@Produces("application/json")
	public String updateCurrentPower(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			DeviceVo deviceVo = JSON.parseObject(paramjson,DeviceVo.class);
			if(StringUtil.checkNull(deviceVo.getPtDeviceId()) || deviceVo.getCurrentPower() == null){
				throw new BusinessException(rb.getString("drptsbid3"));
			}
			
			int voltage = Integer.parseInt(MjConfig.get("voltage"));
			deviceVo.setCurrentPower(deviceVo.getCurrentPower() * voltage);
			hiDeviceService.updateCurrentPower(deviceVo.getPtDeviceId(), deviceVo.getCurrentPower());
			
			return ResultUtil.getSuccessInfo(rb.getString("droperatesuccess"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("droperatefailed"));
		}
	}
	
	
}
