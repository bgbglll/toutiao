package com.bg.model;

/**
 * Created by Administrator on 2016/6/27.
 */
public class User {
    public String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public User(String name){
        this.name=name;
    }
}
