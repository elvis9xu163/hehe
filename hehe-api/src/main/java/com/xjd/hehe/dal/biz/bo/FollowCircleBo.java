package com.xjd.hehe.dal.biz.bo;

public class FollowCircleBo extends BaseBo {
	private String circleId;

	// 根据circleId查询而来
	private CircleBo circle;

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public CircleBo getCircle() {
		return circle;
	}

	public void setCircle(CircleBo circle) {
		this.circle = circle;
	}

}
