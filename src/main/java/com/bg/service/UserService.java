package com.bg.service;


import com.bg.dao.UserDAO;
import com.bg.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
