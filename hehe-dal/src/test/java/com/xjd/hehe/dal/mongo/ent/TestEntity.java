package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "Test", noClassnameStored = true)
public class TestEntity extends BaseEntity {
	public static final String CODE = "TEST1";
	private String code = CODE;
	private Integer testFlag = 1;

	public Integer getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(Integer testFlag) {
		this.testFlag = testFlag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
