package com.bg.async.handler;

import com.bg.async.EventHandler;
import com.bg.async.EventModel;
import com.bg.async.EventType;
import com.bg.model.Message;
import com.bg.service.MessageService;
import com.bg.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2016/7/18.
 */
@Component
public class LoginExceptionHandler implements EventHandler{

    @Autowired
    MessageService messageService;

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        //判断是否登录异常
        if (model.getActorId() != 12)return;
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("你上次的登陆ip地址异常");
        message.setFromId(3);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);


        Map<String, Object> map = new HashMap<>();
        map.put("username",model.getExt("username"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"),"登录异常", "mails/welcome.html", map);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }


}
