package org.xueyu.nettytry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;

public class MyLastHttpHandler extends
		SimpleChannelInboundHandler<DefaultFullHttpRequest> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			DefaultFullHttpRequest msg) throws Exception {
		String uri = msg.getUri();
		System.out.println("###last uri:"+uri);
		
	}

}
