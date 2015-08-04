package org.xueyu.nettytry;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xueyu.conf.AppConfig;
import org.xueyu.conf.BeansManager;
import org.xueyu.conf.UrlMapper;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        //UrlMapper urlmapper = ctx.getBean(UrlMapper.class);
        UrlMapper urlmapper = BeansManager.getContext().getBean(UrlMapper.class);
        System.out.println("##beans manager:"+BeansManager.class.getName());
        System.out.println("##mapper name:" + urlmapper.getName());
        
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	ServerBootstrap b = new ServerBootstrap();
        	b.group(bossGroup, workerGroup)
        		.channel(NioServerSocketChannel.class)
        		.option(ChannelOption.SO_BACKLOG, 100)
        		.handler(new LoggingHandler(LogLevel.DEBUG))
        		.childHandler(new ServerInitializer());
        	
        	ChannelFuture f = b.bind(8001).sync();
        	f.channel().closeFuture().sync();
        } catch (Exception ex) {
        	
        }
    }
}
