package com.cn.huidashi.service;

import com.cn.huidashi.entity.MeetingRoom;

import java.util.List;

/**
 * Created by alvin on 2017/12/27.
 */
public interface IMeetingRoomService {

    /**
     * 批量插入会议室
     * @param rooms
     * @return
     */
    int batchInsert(List<MeetingRoom> rooms);

    /**
     * 根据会场id获取所属的房间信息
     * @param siteId
     * @return
     */
    List<MeetingRoom> selectBySiteId(int siteId);

}
