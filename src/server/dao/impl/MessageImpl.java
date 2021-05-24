package server.dao.impl;

import common.inter.containtype.MessageCon;
import server.dao.BaseDao;
import server.dao.MessageDAO;
import server.model.entity.User;


import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/16-16:08
 */


public class MessageImpl extends BaseDao<MessageCon> implements MessageDAO {

    /**
     * @Author Tptogiar
     * @Description 将文本消息插入数据库
     * @Date 2021/5/21-17:08
     */
    @Override
    public int addTextMessage(Connection connection, MessageCon messageCon) {
        int sendUserID = messageCon.getSendUserID();
        int receiverUserID = messageCon.getReceiverUserID();
        String sendUserName = messageCon.getSendUserName();
        LocalDateTime transferTime = messageCon.getTransferTime();
        String messageText = messageCon.getMessageText();
        String sqlMessage="insert into message(sendUserID,sendUserName,receiverUserID,transferTime) value(?,?,?,?)";
        String sqlContain="insert into contain(text) value(?)";
        update(connection, sqlMessage, sendUserID, sendUserName,receiverUserID, transferTime);
        return update(connection, sqlContain, messageText);
    }

    @Override
    public List<MessageCon> getMessageByUser(Connection connection, int userID) {
        String sql="select sendUserID,receiverUserID,transferTime,text as messageText,image,vedio " +
                "from message,contain where message.messageID=contain.message_id " +
                "and (sendUserID= ? or receiverUserID= ? );";
        List<MessageCon> messageList = getForList(connection, sql, userID, userID);
        return messageList;
    }

}


