package com.xjd.hehe.utl.enums;

/**
 *
 */
public enum JokeCtypeEnum {

	TEXT((byte) 0, "文字"), PIC((byte) 1, "图片"), TEXT_PIC((byte) 2, "图片+文字"), AUDIO((byte) 3, "语音"), TEXT_AUDIO((byte) 4, "文字+语音");

	byte code;
	String desc;

	JokeCtypeEnum(byte code, String desc) {
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
		return JokeCtypeEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static JokeCtypeEnum valueOfCode(Byte code) {
		if (code == null) {
			return null;
		}
		for (JokeCtypeEnum e : JokeCtypeEnum.values()) {
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
