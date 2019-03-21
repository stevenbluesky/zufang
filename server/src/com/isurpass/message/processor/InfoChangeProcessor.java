package com.isurpass.message.processor;

import javax.annotation.Resource;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.util.StringUtil;
import com.ant.vo.PtGatewayVo;
import com.isurpass.service.HiGatewayService;

public class InfoChangeProcessor implements IMessageProcessor {

	private static Log log = LogFactory.getLog(InfoChangeProcessor.class);
	
	@Resource
	private HiGatewayService hiGatewayService;
	
	@Override
	public void process(JSONObject message) 
	{
		boolean ifChangeSingalFlag = false;
		PtGatewayVo ptGatewayVo = JSON.parseObject(message.getString("objparam"),PtGatewayVo.class);
		if(message.getString("objparam").contains("gatewayonline")){
			ifChangeSingalFlag = true;
		}
		if(StringUtil.checkNull(ptGatewayVo.getLoginname()) 
				|| StringUtil.checkNull(ptGatewayVo.getDeviceid()) 
				|| StringUtil.checkNull(ptGatewayVo.getName()))
		{
			log.error("Invalid Parameter");
			return ;
		}
		hiGatewayService.syncGatewayAndDevice(ptGatewayVo,ifChangeSingalFlag);
	}

}
