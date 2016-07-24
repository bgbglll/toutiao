package com.bg.controller;

import com.bg.model.EntityType;
import com.bg.model.HostHolder;
import com.bg.model.News;
import com.bg.model.ViewObject;
import com.bg.service.LikeService;
import com.bg.service.NewsService;
import com.bg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/2.
 */
@Controller
public class HomeController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    private List<ViewObject> getNews(int userId, int offset, int limit){
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for(News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            if (localUserId != 0) {
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        //if (curPage==)
        model.addAttribute("vos", getNews(0, 0, 5));
        return "home";
    }


    @RequestMapping(path = {"/test"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String test(Model model) {
        return "test";
    }

    @RequestMapping(path = {"/page"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String pages(Model model, @RequestParam("curPage") int curPage) {
        int cur = (curPage-1)*5;
        model.addAttribute("vos", getNews(0, cur, 5));
        return "home";
    }

    @RequestMapping(path = {"/news/totalPages"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public int totalPages(Model model) {
        int count = newsService.newsCount();
        int pages = count % 5 == 0 ? count/5 : count/5 + 1;
        return pages;
    }

    @RequestMapping(path = {"/user/{userId}/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId, @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        model.addAttribute("pop", pop);
        return "home";
    }

}
