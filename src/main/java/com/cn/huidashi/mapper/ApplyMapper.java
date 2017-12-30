package com.cn.huidashi.mapper;

import com.cn.huidashi.entity.Apply;
import com.cn.huidashi.entity.pagedto.ApplyPageParam;

import java.util.List;

public interface ApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Apply record);

    int insertSelective(Apply record);

    Apply selectByPrimaryKey(Integer id);

    List<Apply> selectByPageParam(ApplyPageParam spp);

    int updateByPrimaryKeySelective(Apply record);

    int updateByPrimaryKey(Apply record);

    /**
     * 获取已读信息个数
     * @return
     */
    public int getReadedCount();

    /**
     * 获取未读信息个数
     * @return
     */
    public int getUnReadedCount();

    /**
     * 标记申请为已读
     * @param id
     * @return
     */
    public int markReaded(int id);
}