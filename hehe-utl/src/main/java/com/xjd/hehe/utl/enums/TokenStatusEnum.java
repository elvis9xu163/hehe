package com.xjd.hehe.utl.enums;

/**
 *
 */
public enum TokenStatusEnum {

	NORMAL((byte) 0, "正常"), INVALID((byte) 1, "无效");

	byte code;
	String desc;

	TokenStatusEnum(byte code, String desc) {
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
		return TokenStatusEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static TokenStatusEnum valueOfCode(Byte code) {
		if (code == null) {
			return null;
		}
		for (TokenStatusEnum e : TokenStatusEnum.values()) {
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
