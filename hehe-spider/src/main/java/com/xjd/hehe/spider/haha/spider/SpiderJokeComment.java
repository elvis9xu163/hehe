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

import com.xjd.hehe.dal.mongo.ent.JokeEntity;
import com.xjd.hehe.spider.haha.bean.CommentWithPage;
import com.xjd.hehe.spider.haha.saver.SaverComment;
import com.xjd.hehe.spider.haha.saver.SaverJoke;
import com.xjd.hehe.utl.AppContext;
import com.xjd.hehe.utl.JsonUtil;

@Service
public class SpiderJokeComment {
	private static Logger log = LoggerFactory.getLogger(SpiderJokeComment.class);

	@Autowired
	SaverComment saverComment;

	public void grap(JokeEntity entity) {
		Request request = Request.Post(AppContext.getProperty("haha.api.url"));

		int page = 1, totallPage = 1, offset = 10;

		while (page <= totallPage) {
			Response res = null;
			try {
				Form form = Form.form();
				form.add("order", "time");
				form.add("r", "comment_list");
				form.add("drive_info", "182d329ad2026a0cb42e7fbf78a7680175f80000");
				form.add("jid", entity.getRef().getId());
				form.add("page", page + "");
				form.add("offset", offset + "");
				request.bodyForm(form.build());

				res = request.execute();

				String content = res.returnContent().asString(Charset.forName("utf8"));
				log.info("grab: {}-{}", entity.getId(), content);

				CommentWithPage commentWithPage = JsonUtil.parse(content, CommentWithPage.class);
				
				saverComment.save(commentWithPage.getComments(), entity);

				totallPage = commentWithPage.getCount() / offset + (commentWithPage.getCount() % offset > 0 ? 1 : 0);
				page ++;

			} catch (IOException e) {
				log.error("抓取joke详情异常.", e);
			} finally {
				if (res != null) {
					res.discardContent();
				}
			}
		}
	}
}
