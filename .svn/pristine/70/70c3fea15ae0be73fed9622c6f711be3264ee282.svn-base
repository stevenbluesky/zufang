package com.isurpass.message.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ant.config.MjConfig;
import com.ant.restful.service.RestfulUtil;
import com.isurpass.message.processor.IMessageProcessor;
import com.isurpass.service.HiLastTimeLogService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class MessageHandler extends SimpleChannelInboundHandler<String> 
{
	private static Log log = LogFactory.getLog(MessageHandler.class);
	private boolean haslogin = false ; 
	private HiLastTimeLogService hiLastTimeLogService;
	private long lastid = 0 ;
	private boolean hassendlastid = false ;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
    {
    	log.error(cause.getMessage() , cause);
        ctx.close();
    }

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{		
		log.info("channelActive");
		super.channelActive(ctx);
		
		login(ctx);
	}

	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception 
	{
	     if ( message == null || message.length() == 0 )
	    	 return ;

	     log.info(message);
	     if ( !haslogin )
	     {
	    	 processLoginResponse(ctx , message);
	     }
	     else 
	     {
	    	 processEventMessage(ctx , message);
	     }
	}
	
	private void processEventMessage(ChannelHandlerContext ctx,String message)
	{
		JSONObject json = null ;
		try
		{
			json = JSON.parseObject(message);
		}
		catch(Throwable e)
		{
			log.error(e.getMessage());
			return ;
		}
		
		if ( json.containsKey("resultcode") && !json.containsKey("lastid"))  //heart beat message 
		{
			return ;
		}
		
		if ( lastid != 0 && lastid != json.getIntValue("lastid"))
		{
			if ( log.isWarnEnabled())
				log.warn(String.format("lastid not equal , lastid is %d" , lastid));
			this.sendLastid(ctx);
			
			return ;
		}
		
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();

		try
		{
			if ( !wac.containsBean(json.getString("type"))) 
			{
				log.info("not support");
				this.saveLastId(json.getIntValue("newid"));
				return ;
			}
			
			IMessageProcessor bean = (IMessageProcessor)wac.getBean(json.getString("type"));
			if ( bean == null )
			{
				log.info("not support");
				this.saveLastId(json.getIntValue("newid"));
				return ;
			}
			bean.process(json);
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t );
		}
		
		this.saveLastId(json.getIntValue("newid"));
	}
	
	private void processLoginResponse(ChannelHandlerContext ctx, String message)
	{
		JSONObject json = JSON.parseObject(message);
	   	 if ( json.containsKey("resultcode") && json.getIntValue("resultcode") == 0 )
	   	 {
	   		 log.info("login success");
	   		 haslogin = true ;
	   		 sendLastid(ctx);
	   	 }
	   	 else if ( json.containsKey("resultcode") 
	   			 && ( json.getIntValue("resultcode") == 30330 || json.getIntValue("resultcode") == 30010 ))
	   	 {
	   		 RestfulUtil.getInstance().login();
	   		 login(ctx);
	   	 }
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception 
	{
		super.userEventTriggered(ctx, evt);
		
		if (evt instanceof IdleStateEvent) 
		{  
	      IdleStateEvent event = (IdleStateEvent) evt;  
	      if (event.state() == IdleState.ALL_IDLE) 
	      {
	    	  sendHeartBeat(ctx);
	      }
	      else if ( event.state() == IdleState.READER_IDLE)
	      {
	    	  haslogin = false ;
	    	  ctx.close();
	      }
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception 
	{
		log.info("channelInactive");
		super.channelInactive(ctx);
		haslogin = false ;
		ctx.close();
	}
	
	private void login(ChannelHandlerContext ctx)
	{
		JSONObject json = new JSONObject();
		json.put("token", RestfulUtil.getToken());
		json.put("code", MjConfig.get("restCode"));
		ctx.writeAndFlush(json.toJSONString());
	}
	
	private void sendLastid(ChannelHandlerContext ctx)
	{
		if ( hassendlastid == true)
			return ;
		
		if ( hiLastTimeLogService == null )
		{
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			hiLastTimeLogService = (HiLastTimeLogService)wac.getBean("HiLastTimeLogService");
		}
		
		lastid = hiLastTimeLogService.findLastTime();
		
		JSONObject nid = new JSONObject();
		nid.put("lastid", lastid);
		
		String strlastid = nid.toJSONString();
		log.info(strlastid);
		
		ctx.writeAndFlush(strlastid);
		
		hassendlastid = true;
	}
	
	private void sendHeartBeat(ChannelHandlerContext ctx)
	{
		JSONObject json = new JSONObject();
		json.put("action", "heartbeat");
		
		log.info(json.toJSONString());
		ctx.writeAndFlush(json.toJSONString());
	}
	
	private void saveLastId(int id)
	{
		hassendlastid = false ;
		lastid = id ;
		hiLastTimeLogService.saveLastTime(lastid);
	}
}
