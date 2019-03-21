package com.ant.restful.service;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.ant.util.StringUtil;
import com.isurpass.common.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ant.util.ResultUtil;
import com.ant.vo.GatewayVo;
import com.ant.vo.PtGatewayVo;
import com.isurpass.service.HiGatewayService;

/**
 * 网关
 * @author aijian
 *
 */
@Path("/gateway")
@Service
public class GatewayResource {
	
	private static Log log = LogFactory.getLog(GatewayResource.class);
	
	@Resource
	private HiGatewayService hiGatewayService;
	

	/**
	 * 同步网关和设备信息
	 * @return
	 */
	@POST
	@Path(value="syncGatewayAndDevice")
	@Produces("application/json")
	public String syncGatewayAndDevice(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			PtGatewayVo ptGatewayVo = JSON.parseObject(paramjson,PtGatewayVo.class);
			if(StringUtil.checkNull(ptGatewayVo.getLoginname()) || StringUtil.checkNull(ptGatewayVo.getDeviceid()) || 
					StringUtil.checkNull(ptGatewayVo.getName())){
				throw new BusinessException(rb.getString("gr11"));
			}
			
			hiGatewayService.syncGatewayAndDevice(ptGatewayVo);
			return ResultUtil.getSuccessInfo(rb.getString("gr12"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("gr13"));
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
			GatewayVo gatewayVo = JSON.parseObject(paramjson,GatewayVo.class);
			if(StringUtil.checkNull(gatewayVo.getPtGatewayid()) || gatewayVo.getSignalFlag() == null){
				throw new BusinessException(rb.getString("gr21"));
			}
			
			hiGatewayService.updateSingnalFlag(gatewayVo.getPtGatewayid(), gatewayVo.getSignalFlag());
			return ResultUtil.getSuccessInfo(rb.getString("modifysuccess"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("gr13"));
		}
	}
	
	/**
	 * 删除网关
	 * @return
	 */
	@POST
	@Path(value="deleteGateway")
	@Produces("application/json")
	public String deleteGateway(@FormParam("paramjson") String paramjson,HttpServletRequest request){
		ResourceBundle rb;
        String language = request.getLocale().getLanguage();
        if ("zh".equals(language)) {
            rb = ResourceBundle.getBundle("messages", Locale.SIMPLIFIED_CHINESE);
        } else {
            rb = ResourceBundle.getBundle("messages", Locale.US);
        }
		try {
			GatewayVo gatewayVo = JSON.parseObject(paramjson,GatewayVo.class);
			if(StringUtil.checkNull(gatewayVo.getPtGatewayid())){
				throw new BusinessException(rb.getString("gr31"));
			}
			
			hiGatewayService.deleteGateway(gatewayVo.getPtGatewayid());
			return ResultUtil.getSuccessInfo(rb.getString("drsccg"), null);
		}catch (BusinessException e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(e.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage() , e);
			return ResultUtil.getFailureInfo(rb.getString("drscsb"));
		}
	}
	
}
