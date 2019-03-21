package com.ant.controller;

import java.util.HashMap;
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

import com.ant.business.Gateway;
import com.ant.vo.GatewayVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiGatewayService;

/**
 * 网关
 *
 */
@Controller
@RequestMapping(value="/service/gateway")
@Scope(value = "prototype")
public class GatewayController extends BaseController{

	private static Log log = LogFactory.getLog(GatewayController.class);
	
	@Resource
	private HiGatewayService hiGatewayService;

	/**
	  * @Description: 分页查询网关
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("gatewayVo")GatewayVo gatewayVo){
		try{
			if(gatewayVo.getPage() == null || gatewayVo.getRows() == null){
				throw new BusinessException(getRB().getString("pagerows"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			gatewayVo.setPersonId(sessionPersonVo.getId());
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			BasicScroll scroll = new BasicScroll(gatewayVo.getPage(), gatewayVo.getRows());
			List<GatewayVo> lst = hiGatewayService.findPage(gatewayVo, scroll,idList);
			
			return createResponse(1 ,getRB().getString("jquerysuccess") , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 查询网关详情
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findById")
	public @ResponseBody Map<String , Object> findById(@ModelAttribute("gatewayVo")GatewayVo gatewayVo){
		try{
			if(gatewayVo.getId() == null){
				throw new BusinessException(getRB().getString("id"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			Gateway dbGateway = hiGatewayService.findById(gatewayVo.getId());
			Gateway.checkSecondAdminRole(dbGateway, sessionPersonVo,getRB());
			
			return createResponse(1 , getRB().getString("jquerysuccess") , dbGateway);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
	/**
	  * @Description: 统计
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/report")
	public @ResponseBody Map<String , Object> report(@ModelAttribute("gatewayVo")GatewayVo gatewayVo){
		try{
			if(gatewayVo.getPage() == null || gatewayVo.getRows() == null){
				throw new BusinessException(getRB().getString("id"));
			}
			PersonVo sessionPersonVo = super.getCurrentUser();
			gatewayVo.setPersonId(sessionPersonVo.getId());
//			int gatewayCount = gatewayService.findGatewayCount(sessionPersonVo.getId());	//网关数量
			gatewayVo.setSignalFlag(1);//离线异常
			
			BasicScroll scroll = new BasicScroll(gatewayVo.getPage(), gatewayVo.getRows());
			List<Long> idList = PersonController.findOwnDistrictIdList(sessionPersonVo);
			List<GatewayVo> lst = hiGatewayService.findPage(gatewayVo,scroll,idList);
			
			Map<String , Object> returnMap = new HashMap<String , Object>();
			returnMap.put("sumGatewaryCount", hiGatewayService.findCountByPersonId(sessionPersonVo.getId(),idList));
			returnMap.put("gatewayCount", scroll.getRows());
			returnMap.put("gateway", super.createResponse(scroll.getRows(), lst));
			
			return super.createResponse(1, getRB().getString("jquerysuccess"), returnMap);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
	
}
