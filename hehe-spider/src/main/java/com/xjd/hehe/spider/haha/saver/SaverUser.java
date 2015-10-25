package com.xjd.hehe.spider.haha.saver;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.UserDao;
import com.xjd.hehe.dal.mongo.ent.UserEntity;
import com.xjd.hehe.utl.DateUtil;

@Service
public class SaverUser {
	private static Logger log = LoggerFactory.getLogger(SaverUser.class);

	@Autowired
	UserDao userDao;

	public UserEntity save(String hid, String hname, String havatar) {
		UserEntity entity = userDao.getByRefIdAndRefFrom(hid, (byte) 10);
		if (entity != null) { // 找到, 则返回
			return entity;

		} else { // 未找到, 创建一个虚拟的用户与之对应
			entity = new UserEntity();
			entity.setName(hname);
			entity.setAvatar(havatar);
			entity.setType((byte) 2);
			entity.setPwd("29522c68ffbd2f2e62b0a8a0da56f1d287210cf6");
			UserEntity.Ref ref = new UserEntity.Ref();
			ref.setId(hid);
			ref.setName(hname);
			ref.setAvatar(havatar);
			ref.setFrom((byte) 10);
			ref.setCtime(DateUtil.now());
			entity.setRefs(Arrays.asList(ref));
			userDao.save(entity);
			log.info("增加虚拟用户: {}, {}, {}", entity.getId(), hid, hname);
			return entity;
		}
	}
}
