package com.xjd.hehe.dal.mongo.ent.config;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import com.xjd.hehe.dal.mongo.ent.BaseEntity;

/**
 * @author elvis.xu
 * @since 2015-10-31 20:41
 */

@Entity(value = "Config")
public class ConfigEntity extends BaseEntity {
	@Indexed(unique = true)
	/** 配置编码，主键 */
	protected String code;

	public String getCode() {
		return code;
	}
}
