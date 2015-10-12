package com.xjd.hehe.utl.enums;

/**
 * 对象类型
 * 
 * @author elvis.xu
 * @since 2014-9-18
 */
public enum AppTypeEnum {

	IOS((byte) 1, "IOS"), ANDROID((byte) 2, "Android");

	byte code;
	String desc;

	AppTypeEnum(byte code, String desc) {
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
		return AppTypeEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static AppTypeEnum valueOfCode(Byte code) {
		if (code == null) {
			return null;
		}
		for (AppTypeEnum e : AppTypeEnum.values()) {
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
