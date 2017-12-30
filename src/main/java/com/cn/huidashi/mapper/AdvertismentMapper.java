package com.cn.huidashi.mapper;

import com.cn.huidashi.entity.Advertisment;
import com.cn.huidashi.entity.Site;
import com.cn.huidashi.entity.pagedto.AdPageParam;
import com.cn.huidashi.entity.pagedto.SitePageParam;

import java.util.List;

public interface AdvertismentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisment record);

    int insertSelective(Advertisment record);

    Advertisment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertisment record);

    int updateByPrimaryKey(Advertisment record);

    List<Advertisment> selectByPageParam(AdPageParam app);

}