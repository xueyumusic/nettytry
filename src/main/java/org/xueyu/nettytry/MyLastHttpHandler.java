package org.xueyu.nettytry;

import java.lang.reflect.Method;

import org.xueyu.conf.BeansManager;
import org.xueyu.conf.UrlMapper;
import org.xueyu.conf.UrlMapperPostBean;

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
//		Method method = UrlMapper.findMethod(uri);
//		String clsname = UrlMapper.findClassName(uri);
//		UrlMapperPostBean postbean = BeansManager.getContext().getBean(UrlMapperPostBean.class);
//		Object bean = postbean.findBean(clsname);
		
	}

}
