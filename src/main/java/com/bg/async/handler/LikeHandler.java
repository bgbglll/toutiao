package com.bg.async.handler;

import com.bg.async.EventHandler;
import com.bg.async.EventModel;
import com.bg.async.EventType;
import com.bg.model.Message;
import com.bg.model.User;
import com.bg.service.MessageService;
import com.bg.service.UserService;
import com.bg.util.StringToUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18.
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        //System.out.println("Liked");
        Message message = new Message();
        //“1”：系统账号id
        int fromId = 1;
        int toId = model.getEntityOwnerId();
        message.setFromId(fromId);
        User user = userService.getUser(model.getActorId());
        message.setToId(toId);
        String newsUrl = "http://127.0.0.1:8080/news/" + model.getEntityId();
        ///user/{userId}/
        String userUrl = "http://127.0.0.1:8080/user/" + user.getId() + "/";
        String userContent = "用户" + user.getName();
        String content = StringToUrlUtil.buildUrl(userUrl,userContent) + "赞了您的资讯," + StringToUrlUtil.buildUrl(newsUrl,newsUrl);
        message.setContent(content);
        //message.setContent("<a href='" + userUrl + "'>" + "用户" + user.getName() + "</a>" + "攒了您的咨询," + "<a href='" + newsUrl + "'>" + newsUrl + "</a>");
        message.setCreatedDate(new Date());

//      message.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
