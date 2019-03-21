package com.ant.restful.service;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
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
import com.ant.util.ResultUtil;
import com.ant.util.StringUtil;
import com.isurpass.service.HiDeviceDegreesLogService;

/**
 * 电表度数变更
 * @author aijian
 *
 */
@Path("/deviceDegreesLog")
@Service
public class DeviceDegreesLogResource {
	
	private static Log log = LogFactory.getLog(DeviceDegreesLogResource.class);
	@Resource
	private HiDeviceDegreesLogService hiDeviceDegreesLogService;
	

	/**
	 * 保存电表历史记录
	 * @return
	 */
	@POST
	@Path(value="saveDeviceDegreesLog")
	@Produces("application/json")
	public String saveDeviceDegreesLog(@FormParam("paramjson") String paramjson,HttpServletRequest request)
	{
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try
		{
			Map pmap = JSON.parseObject(paramjson, Map.class);
			String zwavedeviceid = StringUtil.objectToString(pmap.get("zwavedeviceid"));
			String floatparmStr = StringUtil.objectToString(pmap.get("floatparm"));
			if (StringUtil.checkNull(zwavedeviceid) || StringUtil.checkNull(floatparmStr))
			{
				throw new BusinessException(rb.getString("ddlrdeviceid"));
			}
			Float floatparm = Float.parseFloat(floatparmStr);
			hiDeviceDegreesLogService.saveDeviceDegreesLog(zwavedeviceid, floatparm, new Date());
			return ResultUtil.getSuccessInfo(rb.getString("jsave"), null);
		} 
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return ResultUtil.getFailureInfo(e.getMessage());
		} 
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return ResultUtil.getFailureInfo(rb.getString("droperatefailed"));
		}
	}
	
}
