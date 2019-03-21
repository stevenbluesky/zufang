package com.isurpass.message.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ant.config.MjConfig;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MessageClient implements Runnable{

	private static Log log = LogFactory.getLog(MessageClient.class);

	   public static void connect(String host , int port)
	   {
	        EventLoopGroup workerGroup = new NioEventLoopGroup();

            Bootstrap b = new Bootstrap();
            b.group(workerGroup); 
            b.channel(NioSocketChannel.class); 
            b.option(ChannelOption.SO_KEEPALIVE, true); 
            b.handler(new MessageClientInitializer());

	        for ( ;; )
	        {
	        	try
	        	{
	        		ChannelFuture f = b.connect(host, port).sync(); 
	            	f.channel().closeFuture().sync();
	        	}
	        	catch(InterruptedException e)
	        	{
	        		log.error(e.getMessage() , e);
	        		break;
	        	}
	        	catch(Throwable t )
		        {
		        	log.error(t.getMessage());
		        }
	        	
	        	try
				{
					Thread.sleep(60 * 1000);
				} 
	        	catch (InterruptedException e)
				{
					log.error(e.getMessage() , e);
					break;
				}
	        }
	        
	        workerGroup.shutdownGracefully();
	    }
	   
		@Override
		public void run() 
		{
			MessageClient.connect(MjConfig.get("tcpHost"), Integer.parseInt(MjConfig.get("tcpPort")));
			
		}
}
