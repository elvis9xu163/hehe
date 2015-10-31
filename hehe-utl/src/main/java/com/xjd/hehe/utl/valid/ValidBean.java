package com.xjd.hehe.utl.valid;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.xjd.hehe.utl.respcode.RespCode;
import com.xjd.hehe.utl.valid.constraint.Timestamp;


public class ValidBean {

	@NotBlank(message = RespCode.RES_0012)
	@Timestamp
	private String time;

//	@NotBlank(message = RespCode.RES_0012)
//	@Date(pattern = Date.DatePattern.yyyyMMdd)
//	private String babyBirth;

	@NotBlank(message = RespCode.RES_0012)
	@Pattern(regexp = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(170)|(18[^4,\\D]))\\d{8}$", message = RespCode.RES_0010)
	private String mobile;

	@NotBlank(message = RespCode.RES_0012)
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = RespCode.RES_0010)
	private String mail;

	@NotBlank(message = RespCode.RES_0012)
	@Pattern(regexp = "(^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$)|(^((13[0-9])|(14[5,7])|(15[^4,\\D])|(170)|(18[^4,\\D]))\\d{8}$)", message = RespCode.RES_0102)
	private String uname;

	@NotBlank(message = RespCode.RES_0012)
	private String pwd;

	@NotBlank(message = RespCode.RES_0012)
	private String nick;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Digits(integer = 18, fraction = 0, message = RespCode.RES_0002)
//	private String userId;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Enum(enumClass = UserSexEnum.class)
//	private String sex;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@com.xjd.ct.utl.valid.constraints.Enum(enumClass = UserSexEnum.class)
//	private String babySex;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@com.xjd.ct.utl.valid.constraints.Enum(enumClass = IdolOperEnum.class)
//	private String idolOper;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@DecimalMin(value = "1", message = RespCode.RES_0002)
//	@Digits(integer = 18, fraction = 0, message = RespCode.RES_0002)
//	private String offset;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@DecimalMin(value = "1", message = RespCode.RES_0004)
//	@DecimalMax(value = "100", message = RespCode.RES_0004)
//	@Digits(integer = 3, fraction = 0, message = RespCode.RES_0002)
//	private String count;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Digits(integer = 18, fraction = 0, message = RespCode.RES_0002)
//	private String objectId;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Pattern(regexp = "0|1", message = RespCode.RES_0003)
//	private String like;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Pattern(regexp = "0|1", message = RespCode.RES_0003)
//	private String favor;
//
//	@NotBlank(message = RespCode.RES_0012)
//	private String comment;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Digits(integer = 18, fraction = 0, message = RespCode.RES_0002)
//	private String toCommentId;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Digits(integer = 18, fraction = 0, message = RespCode.RES_0002)
//	private String commentId;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Digits(integer = 18, fraction = 0, message = RespCode.RES_0002)
//	private String subscribeId;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Pattern(regexp = "0|1", message = RespCode.RES_0003)
//	private String subscribe;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Digits(integer = 18, fraction = 0, message = RespCode.RES_0002)
//	private String ageBracketIdL;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Pattern(regexp = "0|1", message = RespCode.RES_0003)
//	private String set;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@com.xjd.ct.utl.valid.constraints.Enum(enumClass = OrderByEnum.class)
//	private String orderBy;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Pattern(regexp = "0|1", message = RespCode.RES_0003)
//	private String range;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Date(pattern = DatePattern.yyyyMMdd)
//	private String date;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Timestamp
//	private String lastTime;
//
//	@NotBlank(message = RespCode.RES_0012)
//	private String feedbackContent;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@com.xjd.ct.utl.valid.constraints.Enum(enumClass = ReportReasonEnum.class)
//	private String reasonType;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@com.xjd.ct.utl.valid.constraints.Enum(enumClass = EntityTypeEnum.class)
//	private String refType;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Digits(integer = 18, fraction = 0, message = RespCode.RES_0002)
//	private String refId;
//
//	@NotBlank(message = RespCode.RES_0012)
//	@Pattern(regexp = "0|1", message = RespCode.RES_0003)
//	private String turn;


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
}
