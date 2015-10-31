package com.xjd.hehe.utl.enums;

/**
 *
 */
public enum JokeStatusEnum {

	NOT_AUDIT((byte) 0, "待审核"), AUDIT_SUCCESS((byte) 1, "审核通过"), AUDIT_FAIL((byte) 2, "审核拒绝"), NOT_PUBLISH((byte) 3, "待发布");

	byte code;
	String desc;

	JokeStatusEnum(byte code, String desc) {
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
		return JokeStatusEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static JokeStatusEnum valueOfCode(Byte code) {
		if (code == null) {
			return null;
		}
		for (JokeStatusEnum e : JokeStatusEnum.values()) {
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
