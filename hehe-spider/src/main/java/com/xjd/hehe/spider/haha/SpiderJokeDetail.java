package com.xjd.hehe.spider.haha;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

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
public class SpiderJokeDetail {
	private static Logger log = LoggerFactory.getLogger(SpiderJokeDetail.class);

	@Autowired
	SaverJoke jokeSaver;

	public void grap(Long id) {
		Request request = Request.Post(AppContext.getProperty("haha.api.url"));

		Response res = null;
		List<String> readPages = new LinkedList<>();
		try {
			Form form = Form.form();
			form.add("r", "joke_one");
			form.add("drive_info", "182d329ad2026a0cb42e7fbf78a7680175f80000");
			form.add("jid", id + "");
			request.bodyForm(form.build());

			res = request.execute();

			String content = res.returnContent().asString(Charset.forName("utf8"));
			log.info("grab: {}-{}", id, content);

			if ("0".equals(content)) {
				log.info("audit fail: {}", id);
				jokeSaver.auditFail(id);

			} else {
				List list = JsonUtil.parse(content, List.class);
				jokeSaver.save(list);
			}

		} catch (IOException e) {
			log.error("", e);
			if (res != null) {
				res.discardContent();
			}
		}
	}
}
