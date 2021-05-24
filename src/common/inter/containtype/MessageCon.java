package common.inter.containtype;

import common.inter.Contain;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Tptogiar
 * @Descripiton: 封装客户端发送给服务端的消息内容
 * 以及服务端给客户端的响应内容
 * @creat 2021/05/15-22:10
 */


public class MessageCon implements Contain , Serializable {

    private int sendUserID;
    private int receiverUserID;
    private String sendUserName;
    private String messageText;
    private LocalDateTime transferTime;
    private File image;
    private File vedio;
    private byte[] fileBytes;
    private String fileName;



    public MessageCon() {
    }

    public MessageCon(int sendUserID, String sendUserName,int receiverUserID,String messageText,
                      LocalDateTime transferTime) {
        this.sendUserID = sendUserID;
        this.receiverUserID = receiverUserID;
        this.sendUserName=sendUserName;
        this.messageText = messageText;
        this.transferTime=transferTime;
    }

    public MessageCon(int sendUserID, String sendUserName,int receiverUserID,byte[] fileBytes,String fileName) {
        this.sendUserID = sendUserID;
        this.sendUserName = sendUserName;
        this.receiverUserID=receiverUserID;
        this.fileBytes=fileBytes;
        this.fileName=fileName;
    }

    public MessageCon(String messageText) {
        this.messageText = messageText;
    }

    public int getSendUserID() {
        return sendUserID;
    }

    public int getReceiverUserID() {
        return receiverUserID;
    }

    public String getMessageText() {
        return messageText;
    }

    public LocalDateTime getTransferTime() {
        return transferTime;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public String getFileName() {
        return fileName;
    }
}
