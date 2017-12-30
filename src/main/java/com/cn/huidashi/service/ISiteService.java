package com.cn.huidashi.service;

import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.Site;
import com.cn.huidashi.entity.pagedto.SitePageParam;


public interface ISiteService {

    /**
     * 列表查询场地数据
     * @param spp
     * @return
     */
    public Pager<Site> getSitePaged(SitePageParam spp);

    /**
     * 根据场地id获取明细信息
     * @param id
     * @return
     */
    public Site getSiteById(int id);

    /**
     * 保存或更新场地信息
     * @param site
     * @return
     */
    public int  saveOrUpdateSite(Site site);

    /**
     * 移除场地信息，场地信息包含的图片信息，文档信息，以及所包含的房间信息
     * @param id
     * @return
     */
    public int deleteSite(int id);

}
