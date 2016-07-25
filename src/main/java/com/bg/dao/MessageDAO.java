package com.bg.dao;

import com.bg.model.Comment;
import com.bg.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/11.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read,conversation_id, created_date, deleted";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate},#{deleted})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} and deleted = 0 order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from (  select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt where deleted = 0 group by  conversation_id order by id desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read = 0 and to_id=#{userId} and conversation_id=#{conversationId} and deleted = 0"})
    int getConversationUnReadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Update({"update ", TABLE_NAME, "set deleted=#{deleted} where id=#{id}"})
    void updateDeleted(@Param("id") int id, @Param("deleted") int deleted);

    @Update({"update ", TABLE_NAME, "set has_read=#{hasRead} where id=#{id}"})
    void updateRead(@Param("id") int id, @Param("hasRead") int hasRead);

    @Select({"select count(*) ", " from ",TABLE_NAME, " where conversation_id=#{conversationId} and deleted = 0"})
    int detailMessageCount(@Param("conversationId") String conversationId);

    @Update({"update ", TABLE_NAME, "set deleted=#{deleted} where conversation_id=#{conversationId} and deleted = 0"})
    void updateDeletedByConversationId(@Param("conversationId") String conversationId, @Param("deleted") int deleted);

    @Select({"select count(*) from (select count(*) from (  select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by id desc) tt where deleted = 0 group by  conversation_id) ttt"})
    int listCount(@Param("userId") int userId);
}
