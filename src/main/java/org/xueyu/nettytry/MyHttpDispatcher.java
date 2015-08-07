package org.xueyu.nettytry;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.xueyu.conf.BeansManager;
import org.xueyu.conf.UrlMapper;
import org.xueyu.conf.UrlMapperPostBean;
import org.xueyu.view.Thymeleaf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class MyHttpDispatcher extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		FullHttpRequest req = (FullHttpRequest)msg;
		String uri = req.getUri();
		
		System.out.println("###last uri in dispatcher:"+uri);
		Method method = UrlMapper.findMethod(uri);
		String clsname = UrlMapper.findClassName(uri);
		UrlMapperPostBean postbean = BeansManager.getContext().getBean(UrlMapperPostBean.class);
		Object bean = postbean.findBean(clsname);
		System.out.println("##method:"+method.toGenericString());
		String respstr = (String)method.invoke(bean, req);
		
//		Thymeleaf thymeleaf = new Thymeleaf();
//		String respstr = thymeleaf.view();
//		System.out.println("##uri:" + uri);
		ByteBuf content = Unpooled.copiedBuffer(respstr, CharsetUtil.UTF_8);
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
		response.headers().set(CONTENT_TYPE, "text/html");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
		ctx.writeAndFlush(response);
	}

}
