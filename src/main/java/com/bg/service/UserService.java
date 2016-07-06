package com.bg.service;


import com.bg.dao.LoginTicketDAO;
import com.bg.dao.UserDAO;
import com.bg.model.LoginTicket;
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

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public User getUser(int id){
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username, String password){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if(user != null){
            map.put("msgname", "用户名已经被注册");
            return map;
        }

        //Sign up
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));
        //System.out.println(user.getName() + " " + user.getPassword());
        userDAO.addUser(user);

        //Sign in auto
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);


        return map;
    }

    public Map<String, Object> login(String username, String password){
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msgname", "用户名不能为空");
            return map;
        }

        if(StringUtils.isBlank(password)){
            map.put("msgpwd", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        if(user == null){
            map.put("msgname", "用户名不存在");
            return map;
        }
        //System.out.println(password);
        //System.out.println(user.getSalt());
        //System.out.println(ToutiaoUtil.MD5(password + user.getSalt()));
        if(!ToutiaoUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd", "密码不正确");
            return map;
        }


        //Sign in

        //ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() +  1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }


    public void logout(String ticket){
        loginTicketDAO.updateStatus(ticket,1);
    }
}
