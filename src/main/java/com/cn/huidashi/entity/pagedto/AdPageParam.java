package com.cn.huidashi.entity.pagedto;

import com.cn.huidashi.entity.Advertisment;

/**
 * 分页查询实体
 */
public class AdPageParam extends Advertisment {

    /**
     * 当前查询分页下标，默认查询第1页
     */
    private int pageOffSet = 1;

    /**
     * 当前查询分页大小，默认是10
     */
    private int pageSize = 5;

    public int getPageOffSet() {
        return pageOffSet;
    }

    public void setPageOffSet(int pageOffSet) {
        this.pageOffSet = pageOffSet;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
