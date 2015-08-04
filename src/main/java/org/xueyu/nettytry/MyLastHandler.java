package org.xueyu.nettytry;

import org.xueyu.view.Thymeleaf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;

public class MyLastHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	if (msg instanceof FullHttpRequest) {
    		System.out.println("##http handler");
    		//DefaultFullHttpRequest req = (DefaultFullHttpRequest)msg;
    		FullHttpRequest req = (FullHttpRequest)msg;
    		String uri = req.getUri();
    		//String respstr = "hello my uri is : " + uri;
    		Thymeleaf thymeleaf = new Thymeleaf();
    		String respstr = thymeleaf.view();
    		System.out.println("##uri:" + uri);
    		//ctx.writeAndFlush(msg);
    		ByteBuf content = Unpooled.copiedBuffer(respstr, CharsetUtil.UTF_8);
    		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
    		 response.headers().set(CONTENT_TYPE, "text/plain");
             response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
    		ctx.writeAndFlush(response);
    		
    	} else {
    		System.out.println("##last hander"+msg.toString());
    		ctx.writeAndFlush(msg);
    	}
    }
    
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//        ctx.flush();
//    }
}
