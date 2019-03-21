package com.ant.controller;


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

import com.ant.business.OpenDeviceLog;
import com.ant.business.OperateLog;
import com.ant.vo.OpenDeviceLogVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiOpenDeviceLogService;

/**
 * 开锁记录
 *
 */
@Controller
@RequestMapping(value="/service/openDeviceLog")
@Scope(value = "prototype")
public class OpenDeviceLogController extends BaseController{

	private static Log log = LogFactory.getLog(OpenDeviceLogController.class);
	
	@Resource
	private HiOpenDeviceLogService hiOpenDeviceLogService;


	/**
	  * @Description: 分页查询开锁记录
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("openDeviceLogVo")OpenDeviceLogVo openDeviceLogVo){
		try{
			if(openDeviceLogVo.getPage() == null || openDeviceLogVo.getRows() == null || openDeviceLogVo.getDeviceId() == null){
				throw new BusinessException(getRB().getString("pagerowsdeviceid"));
			}
			
			PersonVo sessionPersonVo = super.getCurrentUser();
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				openDeviceLogVo.setPersonId(sessionPersonVo.getSuperpersonid().longValue());
			}else{
				openDeviceLogVo.setPersonId(sessionPersonVo.getId());
			}
			BasicScroll scroll = new BasicScroll(openDeviceLogVo.getPage(), openDeviceLogVo.getRows());
			List<OpenDeviceLog> lst = hiOpenDeviceLogService.findPage(openDeviceLogVo,scroll);
			for(OpenDeviceLog l:lst){
				if(!StringUtils.isEmpty(l.getType())&&l.getType2()!=null){//如果type,type2不为空，则需要将此条记录转换国际化
					switch(l.getType2()){
					case 1:
						l.setOperateName(getRB().getString("odlc1"));
						l.setResult(getRB().getString("odlco"));
						break;
					case 2:
						l.setOperateName(getRB().getString("odlc2"));
						l.setResult(getRB().getString("odlco"));
						break;
					case 3:
						l.setOperateName(getRB().getString("odlc3")+l.getMsg1());
						l.setResult(getRB().getString("odlco"));
						break;
					case 4:
						l.setOperateName(getRB().getString("odlc4"));
						l.setResult(getRB().getString("odlco"));
						break;
					case 5:
						l.setOperateName(getRB().getString("odlc5"));
						l.setResult(getRB().getString("odlcc"));
						break;
					case 6:
						l.setOperateName(l.getMsg1());
						l.setResult(getRB().getString("odlco"));
						break;
					default:
						break;
					}
				}
			}
			return createResponse(1 , getRB().getString("jquerysuccess") , scroll.getRows() , lst);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
		}
	}
}
