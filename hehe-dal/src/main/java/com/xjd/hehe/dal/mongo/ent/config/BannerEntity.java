package com.xjd.hehe.dal.mongo.ent.config;

import java.util.List;
import java.util.Map;

/**
 * @author elvis.xu
 * @since 2015-10-31 20:41
 */
public class BannerEntity extends ConfigEntity {
	public static final String CODE = "BANNER";

	private List<Item> itemList;

	public BannerEntity() {
		code = CODE;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public static class Item {
		/** Banner图片 */
		private String pic;
		/**
		 * <pre>
		 * Banner链接
		 * http://开头表链接
		 * page://开头表内部页面跳转
		 * </pre>
		 */
		private String link;

		/** 当link为page://形式是, 该参数是额外参数 */
		private Map<String, Object> args;

		public String getPic() {
			return pic;
		}

		public void setPic(String pic) {
			this.pic = pic;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public Map<String, Object> getArgs() {
			return args;
		}

		public void setArgs(Map<String, Object> args) {
			this.args = args;
		}
	}
}
