package com.bg;

import com.bg.dao.CommentDAO;
import com.bg.dao.LoginTicketDAO;
import com.bg.dao.NewsDAO;
import com.bg.dao.UserDAO;
import com.bg.model.*;
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
@Sql("/init-schema.sql")
public class InitDatabaseTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    NewsDAO newsDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    CommentDAO commentDAO;

    @Test
    public void initData() {
        Random random = new Random();
        User admin = new User();
        admin.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
        admin.setName("系统");
        admin.setPassword("");
        admin.setSalt("");
        userDAO.addUser(admin);

        String []url = {"http://oa3lacpjg.bkt.clouddn.com/18683af4a0314473ba2e30a04032c64b.jpg"
                ,"http://oa3lacpjg.bkt.clouddn.com/4d72c8e8f9da4724913e254609a5c22b.jpg"
                ,"http://oa3lacpjg.bkt.clouddn.com/4eceb1b49c444b559611d8f015af8cb7.jpg"
                ,"http://oa3lacpjg.bkt.clouddn.com/56ac4e7958d54d118d9f6b0a33fa4dc2.jpg"};

        for (int i = 0; i < 4; i++) {
            News news = new News();
            news.setCommentCount(0);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * 0);
            news.setCreatedDate(date);
            news.setImage(url[i]);
            news.setLikeCount(i + 1);
            news.setUserId(i + 1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(url[i]);
            newsDAO.addNews(news);
        }

        for (int i = 5; i < 16; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() - 1000 * 3600 * 5 * i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i + 1);
            news.setUserId(i + 1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDAO.addNews(news);


            // 给每个资讯插入3个评论
            for (int j = 0; j < 3; ++j) {
                Comment comment = new Comment();
                comment.setUserId(i + 1);
                comment.setCreatedDate(new Date());
                comment.setStatus(0);
                comment.setContent("这里是一个评论啊！" + String.valueOf(j));
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                commentDAO.addComment(comment);
            }





        }

    }
}
