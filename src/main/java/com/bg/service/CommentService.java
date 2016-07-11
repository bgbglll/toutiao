package com.bg.service;

import com.bg.dao.CommentDAO;
import com.bg.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/11.
 */
@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;


    public List<Comment> getCommentsByEntity(int entityId, int entityType){
        return commentDAO.selectByEntity(entityId,entityType);
    }

    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId,entityType);
    }

    public void deleteComment(int entityId, int entityType){
        commentDAO.updateStatus(entityId,entityType,1);
    }
}
