package com.cn.huidashi.service;

import com.cn.huidashi.entity.Menu;
import com.cn.huidashi.entity.User;

import java.util.List;

/**
 * Created by alvin on 2017/7/22.
 */
public interface ICommonService {

    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> getAllMenu();

    /**
     * 验证用户是否存在，信息正确则返回用户信息，信息错误则没有匹配结果
     * @param user
     * @return
     */
    public User validateUser(User user);

}
