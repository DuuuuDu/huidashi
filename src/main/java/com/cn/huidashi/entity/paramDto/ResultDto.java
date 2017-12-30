package com.cn.huidashi.entity.paramDto;

import java.util.List;
import java.util.Map;

/**
 * 一般场景返回结果Dto
 * Created by alvin on 2017/12/26.
 */
public class ResultDto {


    public ResultDto() {
        flag = 1;
    }

    /**
     * 请求结果标识，常用定义，1：成功，0：失败
     */
    private int flag;

    /**
     * 请求结果的说明信息
     */
    private String message;

    /**
     * 待返回数据，根据实际情况进行填充
     */
    private List listData;

    /**
     * 待返回数据，根据实际情况进行填充
     */
    private Map mapData;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getListData() {
        return listData;
    }

    public void setListData(List listData) {
        this.listData = listData;
    }

    public Map getMapData() {
        return mapData;
    }

    public void setMapData(Map mapData) {
        this.mapData = mapData;
    }
}
