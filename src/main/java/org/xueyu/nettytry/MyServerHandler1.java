package org.xueyu.nettytry;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class MyServerHandler1 extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("##process string:"+msg);
		out.add(msg+"--last");
//		for(Object o: out) {
//			ctx.writeAndFlush(o);
//		}
	}

}
