package com.cn.huidashi.service.impl;


import com.cn.huidashi.entity.Apply;
import com.cn.huidashi.entity.Menu;
import com.cn.huidashi.entity.Site;
import com.cn.huidashi.entity.page.Pager;
import com.cn.huidashi.entity.pagedto.ApplyPageParam;
import com.cn.huidashi.entity.pagedto.SitePageParam;
import com.cn.huidashi.mapper.ApplyMapper;
import com.cn.huidashi.mapper.MenuMapper;
import com.cn.huidashi.service.IApplyService;
import com.cn.huidashi.service.ICommonService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by alvin on 2017/7/22.
 */

@Service
public class ApplyService implements IApplyService {

    @Autowired
    private ApplyMapper applyMapper;

    /**
     * 列表查询场地数据
     * @param app
     * @return
     */
    public Pager<Apply> getApplyPaged(ApplyPageParam app){
        PageHelper.startPage(app.getPageOffSet(),app.getPageSize());
        List<Apply> datas =  applyMapper.selectByPageParam(app);

        Pager<Apply> pager = new Pager<Apply>();
        pager.setDatas(datas);

        return pager;
    }

    @Override
    public int save(Apply apply) {
        return applyMapper.insertSelective(apply);
    }

    @Override
    public int markReaded(int id) {
        return applyMapper.markReaded(id);
    }

    @Override
    public int getReadedCount() {
        return applyMapper.getReadedCount();
    }

    @Override
    public int getUnReadedCount() {
        return applyMapper.getUnReadedCount();
    };
}
