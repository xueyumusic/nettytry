package org.xueyu.nettytry;

import java.util.List;

import org.xueyu.conf.UrlMapper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;



public class MyServerHandlerRMI extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		if (msg.equals("goo")) {
			Object obj = UrlMapper.findAndInvokeFunc(msg);
			out.add(obj);
		} else {
			out.add("##miss goo..");
		}
		
	}

}
