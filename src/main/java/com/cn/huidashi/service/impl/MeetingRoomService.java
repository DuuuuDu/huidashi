package com.cn.huidashi.service.impl;

import com.cn.huidashi.entity.MeetingRoom;
import com.cn.huidashi.mapper.MeetingRoomMapper;
import com.cn.huidashi.service.IMeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by alvin on 2017/12/27.
 */
@Service
public class MeetingRoomService implements IMeetingRoomService {

    @Autowired
    private MeetingRoomMapper meetingRoomMapper;

    @Override
    public int batchInsert(List<MeetingRoom> rooms) {
        return meetingRoomMapper.batchInsertSelective(rooms);
    }

    @Override
    public List<MeetingRoom> selectBySiteId(int siteId) {
        return meetingRoomMapper.selectBySiteId(siteId);
    }

    @Override
    public int updateBySelective(MeetingRoom meetingRoom) {
        return meetingRoomMapper.updateByPrimaryKeySelective(meetingRoom);
    }

    @Override
    public int deleteBySiteId(int siteId) {
        return meetingRoomMapper.deleteBySiteId(siteId);
    }


}
