package com.xjd.hehe.spider.haha.saver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.CommentDao;
import com.xjd.hehe.dal.mongo.ent.CommentEntity;
import com.xjd.hehe.dal.mongo.ent.JokeEntity;
import com.xjd.hehe.dal.mongo.ent.UserEntity;
import com.xjd.hehe.spider.haha.bean.Comment;

@Service
public class SaverComment {
	private static Logger log = LoggerFactory.getLogger(SaverComment.class);

	@Autowired
	CommentDao commentDao;
	@Autowired
	SaverUser saverUser;
	@Autowired
	SaverJoke saverJoke;

	public void save(List<List<Comment>> comments, JokeEntity jokeEntity) {
		for (List<Comment> commentGroup : comments) {
			try {
				saveGroup(commentGroup, jokeEntity);
			} catch (Exception e) {
				log.error("处理comment组失败: jid=" + jokeEntity.getId() + ", cid=" + commentGroup.get(0).getId(), e);
			}
		}
	}

	public void saveGroup(List<Comment> cmtGroup, JokeEntity jokeEntity) {
		CommentEntity cmtEntity = null;
		for (int i = cmtGroup.size() - 1; i >= 0; i--) {
			cmtEntity = saveComment(cmtGroup.get(i), cmtEntity != null ? cmtEntity.getId() : null, jokeEntity);
		}
	}

	public CommentEntity saveComment(Comment cmt, String pcid, JokeEntity jokeEntity) {
		CommentEntity cmtEnt = commentDao.getByRefId(cmt.getId() + "");

		if (cmtEnt != null) {
			update(cmt, cmtEnt);

		} else {
			cmtEnt = create(cmt, pcid, jokeEntity);
		}
		return cmtEnt;
	}

	private void update(Comment cmt, CommentEntity cmtEnt) {
		int incGood = cmt.getLight() - cmtEnt.getRef().getGood();
		if (incGood > 0) {
			commentDao.incRefGood(cmtEnt.getId(), incGood);
			log.info("增长哈哈CMT点赞数: {}, {}", cmtEnt.getId(), incGood);
		}
	}

	private CommentEntity create(Comment cmt, String pcid, JokeEntity jokeEntity) {
		// 用户
		UserEntity userEnt = saverUser.save(cmt.getUser_id() + "", cmt.getUser_name(), cmt.getUser_pic());

		// 评论信息
		CommentEntity cmtEnt = new CommentEntity();
		cmtEnt.setUid(userEnt.getId());
		cmtEnt.setJid(jokeEntity.getId());
		cmtEnt.setTxt(cmt.getContent());
		cmtEnt.setNgood(cmt.getLight());
		cmtEnt.setFrom((byte)10);
		
		CommentEntity.Ref ref = new CommentEntity.Ref();
		ref.setId(cmt.getId() + "");
		ref.setUid(cmt.getUser_id() + "");
		ref.setGood(cmt.getLight());
		cmtEnt.setRef(ref);
		
		commentDao.save(cmtEnt);
		log.info("新增哈哈评论: {}, {}", cmtEnt.getId(), cmtEnt.getRef().getId());
		
		// 把Joke里的评论数提到这里来加入
		saverJoke.incRefCmt(jokeEntity.getId());
		
		return cmtEnt;
	}

}
