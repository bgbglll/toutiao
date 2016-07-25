package com.bg.controller;

import com.bg.model.*;
import com.bg.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bg.util.ToutiaoUtil;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String msgContent) {
        try {
            //html 过滤
            Message msg = new Message();
            msg.setContent(msgContent);
            msg.setCreatedDate(new Date());
            msg.setToId(toId);
            msg.setFromId(fromId);
            //msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.addMessage(msg);
            return ToutiaoUtil.getJSONString(msg.getId());
        } catch (Exception e){
            logger.error("增加消息失败" + e.getMessage());
            return ToutiaoUtil.getJSONString(1,"插入消息失败");
        }
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model,
                                     @RequestParam("conversationId") String conversationId, @RequestParam(value="curPage",defaultValue="1") int curPage) {
        try {
            int cur = (curPage - 1) * 5;
            List<Message> conversationList = messageService.getConversationDetail(conversationId,cur,5);
            //System.out.println(conversationList.get(0).getContent());
            List<ViewObject> messages = new ArrayList<>();
            for (Message msg : conversationList){
                ViewObject vo = new ViewObject();
                //System.out.println(msg.getId());
                messageService.readMessage(msg.getId());
                vo.set("message",msg);
                User user = userService.getUser(msg.getFromId());
                if(user == null){
                    continue;
                }
                vo.set("headUrl",user.getHeadUrl());
                vo.set("userId",user.getId());
                messages.add(vo);
            }
            String receiverId;
            String []id = conversationId.split("_");
            int localUserId = hostHolder.getUser().getId();
            //System.out.println(id[0]+" " +id[1]);
            if(!id[0].equals(localUserId)) {
                receiverId = id[0];
            }
            else {
                receiverId =id[1];
            }
            //System.out.println(receiverId);
            model.addAttribute("localUserId", localUserId);
            model.addAttribute("messages",messages);

            //System.out.println(userService.getUser(String.valueOf(receiverId)));
            model.addAttribute("receiverName", userService.getUser(Integer.parseInt(receiverId)).getName());
        } catch (Exception e) {
            logger.error("获取详细消息失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model, @RequestParam(value="curPage",defaultValue="1") int curPage) {
        try {
            int cur = (curPage - 1) * 5;
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationList(localUserId, cur, 5);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user",user);
                vo.set("unread",messageService.getConversationUnReadCount(localUserId,msg.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
            model.addAttribute("localUserId", localUserId);
            return "letter";
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/sendMessage"}, method = {RequestMethod.POST})
    public String sendMessage(@RequestParam("sender") int senderId,
                              @RequestParam("receiver") String receiverName,
                             @RequestParam("msgContent") String content) {
        try {
            //html 过滤
            User receiver = userService.getUser(receiverName);
            content = HtmlUtils.htmlEscape(content);
            Message msg = new Message();
            msg.setContent(content);
            msg.setCreatedDate(new Date());
            msg.setToId(receiver.getId());
            msg.setFromId(senderId);
            msg.setHasRead(1);
            //msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));

            messageService.addMessage(msg);
            return "redirect:/msg/detail?conversationId=" + msg.getConversationId();
            //异步
        } catch (Exception e) {
            logger.error("发送信息失败" + e.getMessage());
            return "错误";
        }
    }

    @RequestMapping(path = {"/msg/delete"}, method = {RequestMethod.POST})
    @ResponseBody
    public int delteMessage(@RequestParam("msgId") int msgId) {
        try {
            //html 过滤
            //System.out.print(msgId);
            messageService.deleteMessage(msgId);
            return 1;
        } catch (Exception e){
            logger.error("删除消息失败" + e.getMessage());
            return 0;
        }
    }

    @RequestMapping(path = {"/msg/detail/totalPages"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public int totalPages(Model model, @RequestParam("conversationId") String conversationId) {
        //System.out.println(conversationId);
        int count = messageService.detailCount(conversationId);
        int pages = count % 5 == 0 ? count/5 : count/5 + 1;
        return pages;
    }

    @RequestMapping(path = {"/msg/deleteList"}, method = {RequestMethod.POST})
    @ResponseBody
    public int delteList(@RequestParam("conversationId") String conversationId) {
        try {
            //html 过滤
            //System.out.print(conversationId);
            messageService.deleteMessageList(conversationId);
            return 1;
        } catch (Exception e){
            logger.error("删除消息失败" + e.getMessage());
            return 0;
        }
    }

    @RequestMapping(path = {"/msg/list/totalPages"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public int listTotalPages(Model model) {
        int localUserId = hostHolder.getUser().getId();
        int count = messageService.listCount(localUserId);
        //System.out.println(count);
        int pages = count % 5 == 0 ? count/5 : count/5 + 1;
        return pages;
    }
}

