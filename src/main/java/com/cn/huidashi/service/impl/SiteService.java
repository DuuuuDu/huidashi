package com.cn.huidashi.service.impl;

import com.cn.huidashi.entity.Site;
import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.pagedto.SitePageParam;
import com.cn.huidashi.mapper.SiteMapper;
import com.cn.huidashi.service.ISiteService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by alvin on 2017/8/3.
 */
@Service
public class SiteService implements ISiteService {

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private Environment env;

    @Override
    public Site getSiteById(int id) {
        return siteMapper.selectByPrimaryKey(id);
    }

    @Override
    public Pager<Site> getSitePaged(SitePageParam spp) {

        PageHelper.startPage(spp.getPageOffSet(), spp.getPageSize());
        PageHelper.orderBy("create_time desc");
        List<Site> datas = siteMapper.selectByPageParam(spp);

        Pager<Site> pager = new Pager<Site>();
        pager.setDatas(datas);

        return pager;
    }


    @Override
    public int saveOrUpdateSite(Site site) {
        int modifiedCount = 0;
        //更新
        if (site.getId() != null) {
            site.setUpdateTime(new Date());
            modifiedCount = siteMapper.updateByPrimaryKeySelective(site);

            //TODO 更新房间明细数据
        } else {
            //新增
            site.setCreateTime(new Date());
            modifiedCount = siteMapper.insertSelective(site);
        }
        return modifiedCount;
    }

    @Override
    public int deleteSite(int id) {

        Site site = siteMapper.selectByPrimaryKey(id);

        //移除图片
        if (!ObjectUtils.isEmpty(site)) {
            try {

                String previewImageName = site.getPreviewImageUrl().substring(site.getPreviewImageUrl().lastIndexOf("/"));

                if (!StringUtils.isEmpty(site.getPdfBriefUrl())) {
                    String pdfBriefName = site.getPdfBriefUrl().substring(site.getPdfBriefUrl().lastIndexOf("/"));
                    String siteBriefFile = env.getProperty("image.siteBriefFile.path", String.class);
                    FileUtils.forceDelete(new File(siteBriefFile + File.separator + pdfBriefName));
                }

                String sitePath = env.getProperty("image.site.path", String.class);
                FileUtils.forceDelete(new File(sitePath + File.separator + previewImageName));

            } catch (IOException e) {
                e.printStackTrace();
            }

            //移除数据
            return siteMapper.deleteByPrimaryKey(id);
        } else {
            return 0;
        }


    }

}
