package com.xjd.hehe.utl;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 应用上下文环境
 * @author elvis.xu
 * @since 2015-6-4
 */
public class AppContext {

	protected static AppContext instance;

	protected Properties properties;

	public AppContext(Properties properties) {
		if (instance == null) {
			instance = this;
			this.properties = properties;
		}
	}

	public static String getProperty(String key) {
		if (instance.properties == null) {
			return null;
		}
		return instance.properties.getProperty(key);
	}

	public static boolean isEnvProduct() {
		return "product".equalsIgnoreCase(getProperty("env"));
	}

	public static boolean isPwdField(String name) {
		name = StringUtils.trimToNull(name);

		if (name != null
				&& (StringUtils.endsWithIgnoreCase(name, "pwd") || StringUtils.endsWithIgnoreCase(name, "password") || StringUtils
						.endsWithIgnoreCase(name, "pass"))) {
			return true;
		}
		return false;
	}

	public static String getPwdMask() {
		return "[******]";
	}

	public static String getDefaultPort() {
		return getProperty("default.port");
	}

	public static String getUploadDirTmp() {
		return getProperty("upload.dir.tmp");
	}

	public static String getUploadDirFormal() {
		return getProperty("upload.dir.formal");
	}

	public static String getUploadDirRoot() {
		return getProperty("upload.dir.root");
	}

	// 糖友可关注的糖友数量上限 1000
	public static String getTangYouFollowTangYouUpLimit() {
		return getProperty("tangYou.follow.tangYou.upLimit");
	}

	// 糖友可被关注的糖友数量上限 10000
	public static String getTangYouByFollowTangYouUpLimit() {
		return getProperty("tangYou.byFollow.tangYou.upLimit");
	}

	// 糖友可关注的圈子数量上限 100
	public static String getTangYouCanFollowCircleUpLimit() {
		return getProperty("tangYou.canFollow.circle.upLimit");
	}

	// 动态(dynamic)的评论数量上限 1000
	public static String getDynamicCommentUpLimit() {
		return getProperty("dynamic.comment.upLimit");
	}

	// 评论的回复数量上限 500
	public static String getCommentReplyUpLimit() {
		return getProperty("comment.reply.upLimit");
	}

	// 单条动态的图片数量上限 9
	public static String getOneDynamicPictureUpLimit() {
		return getProperty("oneDynamic.picture.upLimit");
	}

	// 动态的点赞数量上限 10000
	public static String getDynamicLikeCountUpLimit() {
		return getProperty("dynamic.likeCount.upLimit");
	}

	// 动态的举报数量上限 100
	public static String getDynamicComplaintCountUpLimit() {
		return getProperty("dynamic.complaintCount.upLimit");
	}

	// 用户消息的接收数量上限 1000
	public static String getUserReceiveMessageUpLimit() {
		return getProperty("user.receiveMessage.upLimit");
	}
}
