package com.cn.huidashi.service.impl;


import com.cn.huidashi.entity.Advertisment;
import com.cn.huidashi.entity.Apply;
import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.pagedto.AdPageParam;
import com.cn.huidashi.entity.pagedto.ApplyPageParam;
import com.cn.huidashi.mapper.AdvertismentMapper;
import com.cn.huidashi.mapper.ApplyMapper;
import com.cn.huidashi.service.IAdService;
import com.cn.huidashi.service.IApplyService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by alvin on 2017/7/22.
 */

@Service
public class AdService implements IAdService {

    @Autowired
    private AdvertismentMapper advertismentMapper;

    @Override
    public Pager<Advertisment> getAdPaged(AdPageParam app) {
        PageHelper.startPage(app.getPageOffSet(),app.getPageSize());
        List<Advertisment> datas =  advertismentMapper.selectByPageParam(app);

        Pager<Advertisment> pager = new Pager<Advertisment>();
        pager.setDatas(datas);

        return pager;
    }

    @Override
    public int saveOrUpdateAd(Advertisment ad) {

        int modifiedCount = 0;
        //更新
        if(ad.getId() !=null){
            modifiedCount = advertismentMapper.updateByPrimaryKeySelective(ad);
        }else{
            //新增
            modifiedCount = advertismentMapper.insertSelective(ad);
        }

        return modifiedCount;
    }
}
