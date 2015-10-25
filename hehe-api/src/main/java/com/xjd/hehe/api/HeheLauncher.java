package com.xjd.hehe.api;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xjd.hehe.utl.AppContext;

/**
 * @author elvis.xu
 * @since 2015-10-24 20:34
 */
public class HeheLauncher {

	private static Logger log = LoggerFactory.getLogger(HeheLauncher.class);

	public static void main(String[] args) throws Exception {
		ApplicationContext contxt = new ClassPathXmlApplicationContext("classpath*:config/spring-*.xml");

		ServerBootstrap bootstrap = contxt.getBean(ServerBootstrap.class);

		try {
			int port = getPort(args);

			ChannelFuture channelFuture = bootstrap.bind(port).sync();

			log.info("server start at '{}'...", port);

			channelFuture.channel().closeFuture().sync();
		} finally {
			// 同步关闭套接字
			bootstrap.childGroup().shutdownGracefully();
			bootstrap.group().shutdownGracefully();
		}
	}

	protected static int getPort(String[] args) {
		int port = -1;
		if (args != null && args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				// Do-Nothing
			}
		}
		if (port == -1) {
			try {
				port = Integer.parseInt(AppContext.getDefaultPort());
			} catch (NumberFormatException e) {
				// Do-Nothing
			}
		}
		if (port == -1) {
			port = 8090;
		}
		return port;
	}
}
