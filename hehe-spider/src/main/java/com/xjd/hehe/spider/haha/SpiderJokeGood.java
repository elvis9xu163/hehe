package com.xjd.hehe.spider.haha;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.utl.AppContext;
import com.xjd.hehe.utl.JsonUtil;

@Service
public class SpiderJokeGood {
	private static Logger log = LoggerFactory.getLogger(SpiderJokeGood.class);

	@Autowired
	SaverJoke jokeSaver;

	public void grap() {
		Request request = Request.Post(AppContext.getProperty("haha.api.url"));

		Response res = null;
		List<String> readPages = new LinkedList<>();
		try {
			while (true) {
				Form form = Form.form();
				form.add("r", "joke_list");
				form.add("type", "good");
				form.add("offset", "20");
				form.add("drive_info", "182d329ad2026a0cb42e7fbf78a7680175f80000");
				String readPage = joinReadPage(readPages);
				form.add("read", readPage);
				request.bodyForm(form.build());

				res = request.execute();

				String content = res.returnContent().asString(Charset.forName("utf8"));
				log.info("grab: {}-{}", readPage, content);
				if ("end".equals(content)) {
					log.info("grap finish!");
					break;
				}

				Map map = JsonUtil.parse(content, Map.class);

				Object page = map.get("page");
				readPages.add(page + "");

				jokeSaver.save((List) map.get("joke"));
			}

		} catch (IOException e) {
			log.error("", e);
			if (res != null) {
				res.discardContent();
			}
		}
	}

	protected String joinReadPage(List<String> list) {
		StringBuilder sb = new StringBuilder("");
		boolean first = true;
		for (String s : list) {
			if (!first) {
				sb.append(",");
			}
			sb.append(s);
			first = false;
		}
		return sb.toString();
	}
}
