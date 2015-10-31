package com.xjd.hehe.api.view.body;

import java.util.List;

import com.xjd.hehe.api.view.ViewBody;
import com.xjd.hehe.api.view.vo.BannerVo;

/**
 * @author elvis.xu
 * @since 2015-10-31 19:03
 */
public class BannerListBody extends ViewBody {
	private List<BannerVo> banners;

	public List<BannerVo> getBanners() {
		return banners;
	}

	public void setBanners(List<BannerVo> banners) {
		this.banners = banners;
	}
}
