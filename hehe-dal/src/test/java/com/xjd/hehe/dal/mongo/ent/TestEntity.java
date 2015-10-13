package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class TestEntity extends BaseEntity {
	private Integer testFlag = 1;

	public Integer getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(Integer testFlag) {
		this.testFlag = testFlag;
	}

}
