package com.cn.huidashi.service;

import com.cn.huidashi.entity.Advertisment;
import com.cn.huidashi.entity.Site;
import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.pagedto.AdPageParam;
import com.cn.huidashi.entity.pagedto.SitePageParam;


public interface IAdService {

    /**
     * 列表查询广告图数据
     * @param app
     * @return
     */
    public Pager<Advertisment> getAdPaged(AdPageParam app);

    /**
     * 保存或更新广告图信息
     * @param ad
     * @return
     */
    public int  saveOrUpdateAd(Advertisment ad);

}
