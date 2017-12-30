package com.cn.huidashi.entity.paramDto;

import com.cn.huidashi.entity.MeetingRoom;
import com.cn.huidashi.entity.Site;

import java.util.List;

/**
 * Created by alvin on 2017/12/16.
 */
public class SiteDto{

    private Site site;

    private List<MeetingRoom> meetingRoom;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<MeetingRoom> getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(List<MeetingRoom> meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

}
