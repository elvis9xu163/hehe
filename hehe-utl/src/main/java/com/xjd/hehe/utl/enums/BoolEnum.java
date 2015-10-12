package com.xjd.hehe.utl.enums;

/**
 * <pre>
 * 
 * </pre>
 * @author elvis.xu
 * @since 2015-9-6
 */
public enum BoolEnum {

	YES((byte) 1, "YES"), NO((byte) 0, "NO");

	byte code;
	String desc;

	BoolEnum(byte code, String desc) {
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
		return BoolEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static BoolEnum valueOfCode(Byte code) {
		if (code == null) {
			return null;
		}
		for (BoolEnum e : BoolEnum.values()) {
			if (e.getCode() == code.byteValue()) {
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
