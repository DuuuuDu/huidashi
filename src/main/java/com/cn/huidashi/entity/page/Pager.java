package com.cn.huidashi.entity.page;

import java.util.List;

/**
 * Created by alvin on 2017/8/3.
 */
public class Pager<T> {

    /**
     * 当前分页页数
     */
    private int pageOffSet;

    /**
     * 总分页
     */
    private int totalPageCount;

    /**
     * 当前分页的记录数
     */
    private int curPageRecordCount;

    /**
     * 每个分页的大小，默认是10
     */
    private int perPageSize = 10;

    /**
     * 总记录数
     */
    private int totalRecordCount;

    /**
     * 分页数据
     */
    private List<T> datas;

    public int getPageOffSet() {
        return pageOffSet;
    }

    public void setPageOffSet(int pageOffSet) {
        this.pageOffSet = pageOffSet;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getCurPageRecordCount() {
        return curPageRecordCount;
    }

    public void setCurPageRecordCount(int curPageRecordCount) {
        this.curPageRecordCount = curPageRecordCount;
    }

    public int getPerPageSize() {
        return perPageSize;
    }

    public void setPerPageSize(int perPageSize) {
        this.perPageSize = perPageSize;
    }

    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
