package com.bg;

import com.bg.dao.CommentDAO;
import com.bg.dao.LoginTicketDAO;
import com.bg.dao.NewsDAO;
import com.bg.dao.UserDAO;
import com.bg.model.*;
import com.bg.util.JedisAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/7/2.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
public class JedisTests {

    @Autowired
    JedisAdapter jedisAdapter;


    @Test
    public void testObject(){
        //redis test
        User user = new User();
        user.setHeadUrl("http://image.nowcoder.com/head/100t.png");
        user.setName("username");
        user.setPassword("pwd");
        user.setSalt("salt");
        jedisAdapter.setObeject("user1xx", user);


        User u = jedisAdapter.getObject("user1xx", User.class);
        System.out.println(ToStringBuilder.reflectionToString(u));

    }
}
