package com.xjd.hehe.biz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.biz.bo.CommentBo;
import com.xjd.hehe.biz.bo.UserBo;
import com.xjd.hehe.biz.utl.BeanTrans;
import com.xjd.hehe.dal.mongo.dao.CommentDao;
import com.xjd.hehe.dal.mongo.ent.CommentEntity;

/**
 * @author elvis.xu
 * @since 2015-10-25 09:40
 */
@Service
public class CommentService {
	@Autowired
	CommentDao commentDao;
	@Autowired
	UserService userService;

	public CommentBo get(String cid) {
		CommentEntity commentEntity = commentDao.get(cid);
		CommentBo bo = BeanTrans.trans(commentEntity);
		return bo;
	}

	public void consummate(CommentBo commentBo, boolean user, boolean pcmt) {
		if (commentBo == null) return;

		if (user && commentBo.getUid() != null && commentBo.getUser() == null) {
			UserBo userBo = userService.getUser(commentBo.getUid());
			commentBo.setUser(userBo);
		}

		if (pcmt && commentBo.getCid() != null && commentBo.getPcmt() == null) {
			CommentBo cmt = get(commentBo.getCid());
			consummate(cmt, true, true); // TODO 限制在三层里
			commentBo.setPcmt(cmt);
		}
	}

	public List<CommentBo> listHotCmt(String jid) {
		// FIXME 热点评论设置方式实现
		return null;
	}

	public List<CommentBo> listCmt(String jid, Date time) {
		List<CommentEntity> commentEntityList = commentDao.getNew(jid, time, 20);
		List<CommentBo> boList = BeanTrans.transComment(commentEntityList);
		if (boList != null) {
			for (CommentBo commentBo : boList) {
				consummate(commentBo, true, true);
			}
		}
		return boList;
	}

	public CommentBo addCmt(String uid, String txt, String jid, String cid) {
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setUid(uid);
		commentEntity.setTxt(txt);
		commentEntity.setJid(jid);
		commentEntity.setCid(cid);
		commentDao.save(commentEntity);

		CommentBo bo = BeanTrans.trans(commentEntity);
		return bo;
	}
}
