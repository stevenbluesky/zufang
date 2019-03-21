package com.isurpass.message.processor;

import javax.annotation.Resource;

import com.ant.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.vo.GatewayVo;
import com.isurpass.service.HiGatewayService;

public class DeleteRemoteProcessor implements IMessageProcessor
{
	private static Log log = LogFactory.getLog(DeleteRemoteProcessor.class);
	
	@Resource
	private HiGatewayService hiGatewayService;
	
	@Override
	public void process(JSONObject message)
	{
		GatewayVo gatewayVo = JSON.toJavaObject(message,GatewayVo.class);
		if(StringUtil.checkNull(gatewayVo.getPtGatewayid()))
		{
			log.error("Invalid Parameter");
			return ;
		}
		
		hiGatewayService.deleteGateway(gatewayVo.getPtGatewayid());

	}

}
