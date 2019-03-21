package com.ant.controller;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ant.business.CommuLog;
import com.ant.business.OperateLog;
import com.ant.util.MessageParser;
import com.ant.vo.CommuLogVo;
import com.ant.vo.PersonVo;
import com.isurpass.common.hibernate.BasicScroll;
import com.isurpass.service.HiCommuLogService;

/**
 * 通讯记录
 * @author aijian
 *
 */
@Controller
@RequestMapping(value="/service/commuLog")
@Scope(value = "prototype")
public class CommuLogController extends BaseController{
	
	private static Log log = LogFactory.getLog(CommuLogController.class);
	@Resource
	private HiCommuLogService hiCommuLogService;
	
	/**
	  * @Description: 分页查询通讯记录
	  * @Version: V1.00 
	  * @Parameters:@return
	 */
	@RequestMapping(value="/findPage")
	public @ResponseBody Map<String , Object> findPage(@ModelAttribute("commuLogVo")CommuLogVo commuLogVo)
	{
		try
		{
			if(commuLogVo.getPage() == null || commuLogVo.getRows() == null || commuLogVo.getDeviceId() == null)
			{
				//throw new BusinessException("page、rows、设备id不能为空");
			}
			
			PersonVo sessionPersonVo = getCurrentUser();
			if(PersonController.checkIfSecondAdmin(sessionPersonVo)){
				commuLogVo.setPersonId(sessionPersonVo.getSuperpersonid().longValue());
			}else{
				commuLogVo.setPersonId(sessionPersonVo.getId());
			}
			BasicScroll scroll = new BasicScroll(commuLogVo.getPage() , commuLogVo.getRows());
			List<CommuLog> lst = hiCommuLogService.findPage(commuLogVo,scroll);
			for(CommuLog l:lst){
				if(l.getType1()!=null&&l.getType2()!=null){//如果type1,type2不为空(新插入的纪录)，则需要将此条记录转换国际化
					String result = "";
					if(l.getType2()==1){
						result = getRB().getString("clcs");
					}else{
						result = getRB().getString("clcf");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String format = sdf.format(l.getInputDate());
					switch(l.getType1()){
					case 1:
						String str1="{\"what\":\""+getRB().getString("clc1")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js1 = (JSONObject) JSONObject.parse(str1);
			    		MessageParser mp1 = new MessageParser(getRB().getString("clc"),js1);	
						l.setCommuText(mp1.getMessage());
						break;
					case 2:
						String str2="{\"what\":\""+getRB().getString("clc2")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js2 = (JSONObject) JSONObject.parse(str2);
			    		MessageParser mp2 = new MessageParser(getRB().getString("clc"),js2);	
						l.setCommuText(mp2.getMessage());
						break;
					case 3:
						String str3="{\"what\":\""+getRB().getString("clc3")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js3 = (JSONObject) JSONObject.parse(str3);
			    		MessageParser mp3 = new MessageParser(getRB().getString("clc"),js3);	
						l.setCommuText(mp3.getMessage());
						break;
					case 4:
						String str4="{\"what\":\""+getRB().getString("clc4")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js4 = (JSONObject) JSONObject.parse(str4);
			    		MessageParser mp4 = new MessageParser(getRB().getString("clc"),js4);	
						l.setCommuText(mp4.getMessage());
						break;
					case 5:
						String str5="{\"what\":\""+getRB().getString("clc5")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js5 = (JSONObject) JSONObject.parse(str5);
			    		MessageParser mp5 = new MessageParser(getRB().getString("clc"),js5);	
						l.setCommuText(mp5.getMessage());
						break;
					case 6:
						String str6="{\"what\":\""+getRB().getString("clc6")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js6 = (JSONObject) JSONObject.parse(str6);
			    		MessageParser mp6 = new MessageParser(getRB().getString("clc"),js6);	
						l.setCommuText(mp6.getMessage());
						break;
					case 7:
						String str7="{\"what\":\""+getRB().getString("clc7")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js7 = (JSONObject) JSONObject.parse(str7);
			    		MessageParser mp7 = new MessageParser(getRB().getString("clc"),js7);	
						l.setCommuText(mp7.getMessage());
						break;
					case 8:
						String str8="{\"what\":\""+getRB().getString("clc8")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js8 = (JSONObject) JSONObject.parse(str8);
			    		MessageParser mp8 = new MessageParser(getRB().getString("clc"),js8);	
						l.setCommuText(mp8.getMessage());
						break;
					case 9:
						String str9="{\"what\":\""+getRB().getString("clc9")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js9 = (JSONObject) JSONObject.parse(str9);
			    		MessageParser mp9 = new MessageParser(getRB().getString("clc"),js9);	
						l.setCommuText(mp9.getMessage());
						break;
					case 10:
						String str10="{\"what\":\""+getRB().getString("clc10")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js10 = (JSONObject) JSONObject.parse(str10);
			    		MessageParser mp10 = new MessageParser(getRB().getString("clc"),js10);	
						l.setCommuText(mp10.getMessage());
						break;
					case 11:
						String str11="{\"what\":\""+getRB().getString("clc11")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js11 = (JSONObject) JSONObject.parse(str11);
			    		MessageParser mp11 = new MessageParser(getRB().getString("clc"),js11);	
						l.setCommuText(mp11.getMessage());
						break;
					case 12:
						String str12="{\"what\":\""+getRB().getString("clc12")+"\",\"result\":\""+result+"\",\"when\":\""+format+"\"}";
		        		JSONObject js12 = (JSONObject) JSONObject.parse(str12);
			    		MessageParser mp12 = new MessageParser(getRB().getString("clc"),js12);	
						l.setCommuText(mp12.getMessage());
						break;
					default :
						break;
					}
				}
			}
			return createResponse(1 , "" , scroll.getRows() , lst);
			//this.printJson(ResultUtil.getSuccessInfo("查询成功。",grid));
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage() , e);
			return super.createErrorResponse(-1, e.getMessage());
			//this.printJson(ResultUtil.getFailureInfo(e.getMessage()));
			//调用异常信息
		}
	}
	

}
