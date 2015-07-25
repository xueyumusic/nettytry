package org.xueyu.nettytry;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MyServerHandler extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		byte[] dst = new byte[in.readableBytes()];
		in.readBytes(dst);
		for (byte b: dst) {
			System.out.println("received single:"+(char)b);
		}
		System.out.println("recevied bytes:"+ new String(dst));
		out.add(dst);
		
//		if (in.readableBytes() < 2) {
//			System.out.println("##curm once");
//			return;
//		}
//		char c = in.readChar();
		
//		System.out.print("recevied char:"+c);
//		out.add(c);
	}

}
