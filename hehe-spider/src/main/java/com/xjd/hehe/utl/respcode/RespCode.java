package com.xjd.hehe.utl.respcode;

/**
 * 返回码
 * @author elvis.xu
 * @since 2015-6-5
 */
public interface RespCode {

	/** 成功 */
	String RES_0000 = "0000";

	/** 参数{0}格式错误: {1} */
	String RES_0010 = "0010";
	/** 参数{0}必须为有效的枚举值 */
	String RES_0011 = "0011";
	/** 参数{0}不能为空 */
	String RES_0012 = "0012";
	/** 参数{0}取值不在范围内 */
	String RES_0013 = "0013";
	/** 参数{0}必须为有效的ObjectId */
	String RES_0014 = "0014";
	
	/** 未找到要上传的文件 */
	String RES_0020 = "0020";

	/** 请先登录 */
	String RES_0100 = "0100";
	/** TOKEN无效，请重新登录 */
	String RES_0101 = "0101";
	
	/** 该动态已删除 */
	String RES_0120 = "0120";
	/** 该评论已删除 */
	String RES_0121 = "0121";
	
	/** 超出系统限制，如有疑问请联系客服。 */
	String RES_9950 = "9950";

	/** 未知应用 */
	String RES_9970 = "9970";
	/** 未知应用版本 */
	String RES_9971 = "9971";
	/** 亲，您的应用版本太低了哦，请升级最新版本体验更多功能吧！ */
	String RES_9972 = "9972";

	/** 内部错误 */
	String RES_9999 = "9999";

}
