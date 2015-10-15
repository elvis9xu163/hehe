package com.xjd.hehe.spider;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xjd.hehe.spider.haha.spider.*;

public class Launcher {
	public static Logger log = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) {
		ApplicationContext contxt = new ClassPathXmlApplicationContext("classpath*:config/spring-*.xml");

		if (args == null) {
			log.info("No argument!");
			return;
		}

		List<String> argList = Arrays.asList(args);

		if (argList.contains("good")) {
			SpiderJokeGood jokeGood = contxt.getBean(SpiderJokeGood.class);
			jokeGood.grap();
		}

		if (argList.contains("new")) {
			SpiderJokeNew jokeNew = contxt.getBean(SpiderJokeNew.class);
			jokeNew.grap();
		}

		if (argList.contains("text")) {
			SpiderJokeText jokeText = contxt.getBean(SpiderJokeText.class);
			jokeText.grap();
		}

		if (argList.contains("pic")) {
			SpiderJokePic jokeText = contxt.getBean(SpiderJokePic.class);
			jokeText.grap();
		}

		if (argList.contains("web_good")) {
			SpiderJokeWebGood jokeText = contxt.getBean(SpiderJokeWebGood.class);
			jokeText.grap();
		}

		if (argList.contains("topic_beauty")) {
			SpiderJokeTopicBeauty jokeText = contxt.getBean(SpiderJokeTopicBeauty.class);
			jokeText.grap();
		}
	}
}
