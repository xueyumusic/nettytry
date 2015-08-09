package org.xueyu.nettytry;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;


import java.util.Map;
import java.util.Map.Entry;

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
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

public class MyHttpDispatcher extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		FullHttpRequest req = (FullHttpRequest)msg;
		String uri = req.getUri();
		
		System.out.println("###last uri in dispatcher:"+uri);
		System.out.println("###req method:"+req.getMethod());
		if (req.getMethod() == HttpMethod.GET) {
			QueryStringDecoder decoder = new QueryStringDecoder(uri, Charset.defaultCharset());
			String reqPath = decoder.path();
			System.out.println("##req path is "+reqPath);
			
			Method method = UrlMapper.findMethod(reqPath);
			String clsname = UrlMapper.findClassName(reqPath);
			UrlMapperPostBean postbean = BeansManager.getContext().getBean(UrlMapperPostBean.class);
			Object bean = postbean.findBean(clsname);
			System.out.println("##method:"+method.toGenericString());
			
			Map<String, List<String>> params = decoder.parameters();
			for(Entry<String, List<String>> entry: params.entrySet()) {
				System.out.println("##param name:" + entry.getKey());
				System.out.println("##param value:" + entry.getValue().get(0));
			}
			
			String respstr = (String)method.invoke(bean, req.copy());
			
	//		Thymeleaf thymeleaf = new Thymeleaf();
	//		String respstr = thymeleaf.view();
	//		System.out.println("##uri:" + uri);
			ByteBuf content = Unpooled.copiedBuffer(respstr, CharsetUtil.UTF_8);
			DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
			response.headers().set(CONTENT_TYPE, "text/html");
	        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
			ctx.writeAndFlush(response);
		} else if (req.getMethod() == HttpMethod.POST) {
			HttpPostRequestDecoder postDecoder = new HttpPostRequestDecoder(req);
			List<InterfaceHttpData> httpdatas = postDecoder.getBodyHttpDatas();
			for (InterfaceHttpData data: httpdatas) {
				if (data.getHttpDataType() == HttpDataType.Attribute) {
					String name = data.getName();
					Attribute attribute = (Attribute)data;
					String value = attribute.getValue();
					System.out.println("##post attributes:name:"+name+"  value:"+value);
					
				}
			}
			
			String contenttype = req.headers().get(CONTENT_TYPE);
			if (contenttype.equals("application/json")) {
				String 	json = req.content().toString(CharsetUtil.UTF_8);
				System.out.println("###post json string:"+json);
			}
			
			String reqPath = req.getUri();
			Method method = UrlMapper.findMethod(reqPath);
			String clsname = UrlMapper.findClassName(reqPath);
			UrlMapperPostBean postbean = BeansManager.getContext().getBean(UrlMapperPostBean.class);
			Object bean = postbean.findBean(clsname);
			System.out.println("##method:"+method.toGenericString());
			
			String respstr = (String)method.invoke(bean, req.copy());
			
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

}
