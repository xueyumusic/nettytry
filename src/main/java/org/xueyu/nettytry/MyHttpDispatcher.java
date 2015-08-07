package org.xueyu.nettytry;

import java.lang.reflect.Method;

import org.xueyu.conf.UrlMapper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

public class MyHttpDispatcher extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		FullHttpRequest req = (FullHttpRequest)msg;
		String uri = req.getUri();
		Method method = UrlMapper.findMethod(uri);
		
	}

}
