package com.bg.model;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/7/6.
 */
@Component
public class HostHolder {
    private static  ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUsers(User user){
        users.set(user);
    }

    public void cler(){
        users.remove();
    }
}
