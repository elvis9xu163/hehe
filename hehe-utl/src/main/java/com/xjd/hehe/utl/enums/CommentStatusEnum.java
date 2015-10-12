package com.xjd.hehe.utl.enums;

/**
 * @author elvis.xu
 * @since 2015-9-6
 */
public enum CommentStatusEnum {

	INIT(0, "待处理"), PASS(1, "审核通过"), REFUSE(2, "审核不通过"), DELETE(3, "用户删除");

	int code;
	String desc;

	CommentStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String toString() {
		return CommentStatusEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static CommentStatusEnum valueOfCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (CommentStatusEnum e : CommentStatusEnum.values()) {
			if (e.getCode() == code.intValue()) {
				return e;
			}
		}
		return null;
	}

	public static boolean validCode(String code) {
		Integer val = null;
		try {
			val = Integer.valueOf(code);
		} catch (NumberFormatException e) {
			return false;
		}
		if (valueOfCode(val) == null) {
			return false;
		}
		return true;
	}

}
