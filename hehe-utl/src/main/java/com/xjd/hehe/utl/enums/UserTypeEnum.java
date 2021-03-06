package com.xjd.hehe.utl.enums;

/**
 *
 */
public enum UserTypeEnum {

	NORMAL((byte) 0, "正常"), VISITOR((byte) 1, "游客"), FAKE((byte) 1, "虚拟");

	byte code;
	String desc;

	UserTypeEnum(byte code, String desc) {
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
		return UserTypeEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static UserTypeEnum valueOfCode(Byte code) {
		if (code == null) {
			return null;
		}
		for (UserTypeEnum e : UserTypeEnum.values()) {
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
