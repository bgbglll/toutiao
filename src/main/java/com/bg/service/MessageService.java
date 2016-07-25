package com.bg.service;

import com.bg.dao.MessageDAO;
import com.bg.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        // conversation的总条数存在id里
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConversationUnReadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnReadCount(userId, conversationId);
    }

    public void deleteMessage(int id) {
        messageDAO.updateDeleted(id,1);
    }

    public void readMessage(int id) {
        messageDAO.updateRead(id, 1);
    }

    public int detailCount(String conversationId) {
        return messageDAO.detailMessageCount(conversationId);
    }

    public void deleteMessageList(String conversationId) {
        messageDAO.updateDeletedByConversationId(conversationId,1);
    }

    public int listCount(int userId) {
        return messageDAO.listCount(userId);
    }
}
