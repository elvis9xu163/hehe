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

	/** 亲，登录后才能干事哦！ */
	String RES_0100 = "0100";
	/** 亲，您需要重新登录一次哦！ */
	String RES_0101 = "0101";
	/** 亲，用户名只能是手机号或邮箱(推荐)哦！ */
	String RES_0102 = "0102";

	/** 非法客户端 */
	String RES_9970 = "9970";
	/** 亲，您的应用版本太低了哦，请升级最新版体验更多功能吧！ */
	String RES_9971 = "9971";

	/** 内部错误 */
	String RES_9999 = "9999";

}
