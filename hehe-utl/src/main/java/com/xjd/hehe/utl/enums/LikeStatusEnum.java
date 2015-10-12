package com.xjd.hehe.utl.enums;

/**
 * @author elvis.xu
 * @since 2015-9-6
 */
public enum LikeStatusEnum {

	NORMAL(0, "正常"), DELETE(1, "删除");

	int code;
	String desc;

	LikeStatusEnum(int code, String desc) {
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
		return LikeStatusEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static LikeStatusEnum valueOfCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (LikeStatusEnum e : LikeStatusEnum.values()) {
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
