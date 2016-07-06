package com.bg.service;


import com.bg.dao.UserDAO;
import com.bg.model.User;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.ToutiaoUtil;

import java.util.*;

/**
 * Created by Administrator on 2016/7/2.
 */
@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User getUser(int id){
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username, String password){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msgname", "用户名不能为空");
        }

        if(StringUtils.isBlank(password)){
            map.put("msgpwd", "密码不能为空");
        }

        User user = userDAO.selectByName(username);

        if(user != null){
            map.put("msgname", "用户名已经被注册");
        }

        //Sign up
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        //Sign in
        return map;
    }
}
