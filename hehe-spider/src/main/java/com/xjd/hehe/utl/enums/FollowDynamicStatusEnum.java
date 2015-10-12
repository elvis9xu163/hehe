package com.xjd.hehe.utl.enums;

/**
 * @author elvis.xu
 * @since 2015-9-6
 */
public enum FollowDynamicStatusEnum {

	INVALID(0, "无效"), VALID(1, "有效");

	int code;
	String desc;

	FollowDynamicStatusEnum(int code, String desc) {
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
		return FollowDynamicStatusEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static FollowDynamicStatusEnum valueOfCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (FollowDynamicStatusEnum e : FollowDynamicStatusEnum.values()) {
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
