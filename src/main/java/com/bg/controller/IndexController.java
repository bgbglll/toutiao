package com.bg.controller;

import com.bg.model.User;
import com.bg.service.ToutiaoService;
import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;



/**
 * Created by Administrator on 2016/6/27.
 */

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping(path={"/","/index"})
    @ResponseBody
    public String index(HttpSession session){
        logger.info("Visit Index");
        return "Hello Bg!" + session.getAttribute("msg") + "<br> Say: " + toutiaoService.say();
    }
    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value="type",defaultValue = "1") int type,
                          @RequestParam(value="key",defaultValue = "bg") String key){
        return String.format("GID:{%s},UID:{%d},TYPE:{%d},KEY:{%s}",groupId,userId,type,key);
    }

    @RequestMapping(value={"/vm"})
    public String news(Model model){
        model.addAttribute("value1","vv1");
        List<String> colors= Arrays.asList(new String[] {"RED","BLUE","GREEN"});
        Map<String,String> map=new HashMap<>();
        for(int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("colors",colors);
        model.addAttribute("map",map);
        model.addAttribute("user", new User("Jim"));
        return "news";
    }

    @RequestMapping(value={"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            //System.out.println(name);
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        for(Cookie cookie : request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append("value:");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }

        sb.append("getMethod:" + request.getMethod() + "<br>");
        sb.append("getPathInfo:" + request.getPathInfo() + "<br>");
        sb.append("getQueryString:" + request.getQueryString() + "<br>");
        sb.append("getRequestURI:" + request.getRequestURI() + "<br>");


        return sb.toString();
    }

    @RequestMapping(value={"/response"})
    @ResponseBody
    public String response2(@CookieValue(value = "id", defaultValue = "a") String id,
                           @RequestParam(value = "key", defaultValue = "key") String key,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           HttpServletResponse response){
        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);
        return "Id From Cookie:" + id;
    }

    @RequestMapping(value={"/redirect/{code}"})
    public String redirect(@PathVariable("code") int code, HttpSession session){
        /*
        RedirectView red = new RedirectView("/", true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        */
        session.setAttribute("msg", "Jump from redirect");
        return "redirect:/";
    }

    @RequestMapping(value={"/admin"})
    @ResponseBody
    public String admin(@RequestParam (value= "key", required = false) String key){
        if ("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("Key 错误");
    }

    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error: " + e.getMessage();
    }



    @RequestMapping(path={"/login"})
    @ResponseBody
    public String profile(@RequestParam(value="username") String username,
                          @RequestParam(value="password") String password){
        if(username.equals("libogu")&&password.equals("8230889")){
            return "Login!";
        }
        else
            return "Error!";
    }

    @RequestMapping(path={"/myhome"})
    public String home(){
        return "myhome";
    }
}
