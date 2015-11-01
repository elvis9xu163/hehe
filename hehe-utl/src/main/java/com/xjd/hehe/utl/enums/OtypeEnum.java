package com.xjd.hehe.utl.enums;

/**
 *
 */
public enum OtypeEnum {

	JOKE((byte) 1, "笑话"), COMMENT((byte) 2, "评论");

	byte code;
	String desc;

	OtypeEnum(byte code, String desc) {
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
		return OtypeEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static OtypeEnum valueOfCode(Byte code) {
		if (code == null) {
			return null;
		}
		for (OtypeEnum e : OtypeEnum.values()) {
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
