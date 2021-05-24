package server.model.server;

import common.inter.containtype.MessageCon;
import server.dao.MessageDAO;
import server.dao.impl.MessageImpl;
import server.model.entity.User;
import server.model.pool.MyDataSource;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Tptogiar
 * @Descripiton: 操作数据表message的服务
 * @creat 2021/05/21-16:22
 */


public class MessageSvr {

    private static MessageDAO messageImpl =new MessageImpl();
    Connection connection= MyDataSource.getConnection();

    public MessageSvr() {
    }

    public static MessageSvr getInstance(){
        return new MessageSvr();
    }

    /**
     * @Author Tptogiar
     * @Description 将文本消息添加到数据库
     * @Date 2021/5/21-16:15
     */
    public int addMessageTextTODataBase(MessageCon messageCon){
        return messageImpl.addTextMessage(connection,messageCon);
    }


    /**
     * @Author Tptogiar
     * @Description 从数据库查询用户对应的聊天记录后返回给用户
     * @Date 2021/5/21-21:12
     */
    public List<MessageCon> getMessageByUser(int userID){
        return messageImpl.getMessageByUser(connection,userID);
    }


}
