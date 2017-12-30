package com.cn.huidashi.mapper;

import com.cn.huidashi.entity.MeetingRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeetingRoomMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(MeetingRoom record);

    int insertSelective(MeetingRoom record);

    MeetingRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeetingRoom record);

    int updateByPrimaryKey(MeetingRoom record);

    int batchInsertSelective(List<MeetingRoom> records);

    List<MeetingRoom> selectBySiteId(@Param("siteId") int siteId);

}