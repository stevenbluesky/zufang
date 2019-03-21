package com.ant.restful.service;


import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ant.util.ResultUtil;
import com.isurpass.common.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ant.business.LastTimeLog;
import com.isurpass.service.HiLastTimeLogService;

/**
 * 最后一次推送时间
 * @author aijian
 *
 */
@Path("/lastTimeLog")
@Service
public class LastTimeLogResource {
	
	private static Log log = LogFactory.getLog(LastTimeLogResource.class);
	
	@Resource
	private HiLastTimeLogService hiLastTimeLogService;
	

	/**
	 * 查询最后一次推送时间
	 * @return
	 */
	@POST
	@Path(value="findLastTime")
	@Produces("application/json")
	public String findLastTime(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			
			Long lastId = hiLastTimeLogService.findLastTime();
			return ResultUtil.getSuccessInfo(rb.getString("checksuccess"), lastId);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("gr13"));
		}
	}
	
	/**
	 * 保存最后一次推送时间
	 * @return
	 */
	@POST
	@Path(value="saveLastTime")
	@Produces("application/json")
	public String saveLastTime(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			LastTimeLog lastTimeLog = JSON.parseObject(paramjson,LastTimeLog.class);
			if(lastTimeLog.getLastId() == null){
				throw new BusinessException(rb.getString("rtlr1"));
			}
			hiLastTimeLogService.saveLastTime(lastTimeLog.getLastId());
			return ResultUtil.getSuccessInfo(rb.getString("jsave"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("gr13"));
		}
	}
	
}
