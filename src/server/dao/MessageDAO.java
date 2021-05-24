package server.dao;

import common.inter.containtype.MessageCon;
import server.model.entity.User;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: Tptogiar
 * @Description:
 * @Date: 2021/5/16-16:08
 */
public interface MessageDAO {

    int addTextMessage(Connection connection, MessageCon messageCon);

    List<MessageCon> getMessageByUser(Connection connection, int userID);
}
