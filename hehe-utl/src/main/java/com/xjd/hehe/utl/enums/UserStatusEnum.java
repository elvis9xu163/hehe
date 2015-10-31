package com.xjd.hehe.utl.enums;

/**
 *
 */
public enum UserStatusEnum {

	NORMAL((byte) 0, "正常"), INVALID((byte) 1, "无效");
	
	byte code;
	String desc;

	UserStatusEnum(byte code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public byte getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String toString() {
		return UserStatusEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static UserStatusEnum valueOfCode(Byte code) {
		if (code == null) {
			return null;
		}
		for (UserStatusEnum e : UserStatusEnum.values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		return null;
	}

	public static boolean validCode(String code) {
		Byte val = null;
		try {
			val = Byte.valueOf(code);
		} catch (NumberFormatException e) {
			return false;
		}
		if (valueOfCode(val) == null) {
			return false;
		}
		return true;
	}

}
