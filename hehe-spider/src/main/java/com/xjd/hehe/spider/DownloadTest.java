package com.xjd.hehe.spider;

import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

/**
 * @author elvis.xu
 * @since 2015-10-14 00:59
 */
public class DownloadTest {

	public static void main(String[] args) throws IOException {

		Request request = Request.Get("http://image.haha.mx/2015/10/13/big/1980363_5b56512e663b437f6a3e83d0b5cd2171_1444700590.jpg");

		Response res = null;
		try {
//			Form form = Form.form();
//			form.add("order", "time");
//			form.add("r", "comment_list");
//			form.add("drive_info", "182d329ad2026a0cb42e7fbf78a7680175f80000");
//			form.add("jid", entity.getRef().getId());
//			form.add("page", page + "");
//			form.add("offset", offset + "");
//			request.bodyForm(form.build());

//			request.setHeader("Host", "image.haha.mx");
//			request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
//			request.setHeader("", "");



			res = request.execute();

			byte[] bs = res.returnContent().asBytes();
			System.out.println(bs.length);


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (res != null) {
				res.discardContent();
			}
		}

	}
}
