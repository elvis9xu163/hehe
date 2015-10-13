package com.xjd.hehe.spider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xjd.hehe.spider.haha.spider.SpiderJokeGood;

public class Launcher {

	public static void main(String[] args) {
		ApplicationContext contxt = new ClassPathXmlApplicationContext("classpath*:config/spring-*.xml");

		SpiderJokeGood jokeGood = (SpiderJokeGood) contxt.getBean(SpiderJokeGood.class);
		jokeGood.grap();

		// SpiderJokeDetail jokeDetail = (SpiderJokeDetail) contxt.getBean(SpiderJokeDetail.class);
		// jokeDetail.grap(1976459L);
	}
}
