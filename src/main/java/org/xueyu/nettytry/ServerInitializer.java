package org.xueyu.nettytry;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		//p.addLast(new MyServerHandler());
		p.addLast("frame", new LineBasedFrameDecoder(80));
		p.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
		p.addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8));
		//p.addLast("myhandler1", new MyServerHandler1());
		p.addLast("myrmihandle", new MyServerHandlerRMI());
		p.addLast("last", new MyLastHandler());
	}

}
