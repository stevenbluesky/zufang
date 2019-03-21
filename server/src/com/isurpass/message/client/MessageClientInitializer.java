package com.isurpass.message.client;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class MessageClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception 
    {
        ChannelPipeline pipeline = ch.pipeline();
        
        //pipeline.addLast(new ChannelTrafficShapingHandler(0 ,32));
        pipeline.addLast(new IdleStateHandler(180 , 30 , 30, TimeUnit.SECONDS));
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,new ByteBuf[] {Unpooled.wrappedBuffer("\n".getBytes())})) ; 
        pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
        pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
       
        
        pipeline.addLast("handler", new MessageHandler());
    }
}
