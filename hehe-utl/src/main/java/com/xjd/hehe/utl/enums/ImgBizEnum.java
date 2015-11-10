package com.xjd.hehe.utl.enums;

/**
 * <pre>
 * 
 * </pre>
 * @author elvis.xu
 * @since 2015-9-6
 */
public enum ImgBizEnum {

	JOKE("[JOKE]", "笑话"), AVATAR("[AVATAR]", "头像");

	String code;
	String desc;

	ImgBizEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public String toString() {
		return ImgBizEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
	}

	public static ImgBizEnum valueOfCode(String code) {
		if (code == null) {
			return null;
		}
		for (ImgBizEnum e : ImgBizEnum.values()) {
			if (e.getCode().equals(code)) {
				return e;
			}
		}
		return null;
	}

	public static boolean validCode(String code) {
		if (valueOfCode(code) == null) {
			return false;
		}
		return true;
	}

}
