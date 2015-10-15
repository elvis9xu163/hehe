package com.xjd.hehe.spider.haha.spider;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.spider.haha.bean.JokeWithPage;
import com.xjd.hehe.spider.haha.saver.SaverJoke;
import com.xjd.hehe.utl.AppContext;
import com.xjd.hehe.utl.JsonUtil;

@Service
public class SpiderJokePic {
	private static Logger log = LoggerFactory.getLogger(SpiderJokePic.class);

	@Autowired
	SaverJoke jokeSaver;

	public void grap() {
		Request request = Request.Post(AppContext.getProperty("haha.api.url"));
		int maxPage = SpiderConfig.getMaxPage();
		Response res = null;
		try {
			int page = 1;
			while (true) {
				if (page > maxPage) {
					break;
				}
				Form form = Form.form();
				form.add("r", "joke_list");
				form.add("type", "pic");
				form.add("offset", "20");
				form.add("page", page + "");
				form.add("drive_info", "182d329ad2026a0cb42e7fbf78a7680175f80000");
				request.bodyForm(form.build());

				res = request.execute();

				String content = res.returnContent().asString(Charset.forName("utf8"));
				log.info("pic grab: {}-{}", page, content);
				if ("end".equals(content)) {
					log.info("grap finish!");
					break;
				}

				JokeWithPage jokeWithPage = JsonUtil.parse(content, JokeWithPage.class);

				jokeSaver.save(jokeWithPage.getJoke(), true);
			}

		} catch (IOException e) {
			log.error("抓取pic joke异常.", e);

		} finally {
			if (res != null) {
				res.discardContent();
			}
		}
	}
}
