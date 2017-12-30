package com.cn.huidashi.mapper;

import com.cn.huidashi.entity.Site;
import com.cn.huidashi.entity.pagedto.SitePageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SiteMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Site record);

    int insertSelective(Site record);

    Site selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Site record);

    int updateByPrimaryKey(Site record);

    List<Site> selectByPageParam(SitePageParam spp);

    Integer selectByPageParamCount(SitePageParam spp);

}