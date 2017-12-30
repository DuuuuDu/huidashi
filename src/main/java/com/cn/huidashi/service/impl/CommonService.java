package com.cn.huidashi.service.impl;


import com.cn.huidashi.entity.Menu;
import com.cn.huidashi.entity.User;
import com.cn.huidashi.mapper.MenuMapper;
import com.cn.huidashi.mapper.UserMapper;
import com.cn.huidashi.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by alvin on 2017/7/22.
 */

@Service
public class CommonService implements ICommonService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.selectAll();
    }

    @Override
    public User validateUser(User user) {
        return userMapper.validateUser(user);
    }
}
