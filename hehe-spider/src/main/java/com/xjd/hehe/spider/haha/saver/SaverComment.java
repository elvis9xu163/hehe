package com.xjd.hehe.spider.haha.saver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.ent.JokeEntity;
import com.xjd.hehe.spider.haha.bean.Comment;

@Service
public class SaverComment {
	private static Logger log = LoggerFactory.getLogger(SaverComment.class);


	public void save(List<List<Comment>> comments, JokeEntity jokeEntity) {
		// FIXME
	}

}
