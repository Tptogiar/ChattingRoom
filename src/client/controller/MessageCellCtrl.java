package client.controller;

import client.model.server.ClientStatus;
import com.sun.org.apache.xml.internal.security.Init;
import common.inter.containtype.MessageCon;
import common.util.TimeUtil;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import javax.tools.Tool;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @author Tptogiar
 * @Descripiton: 用来展示消息的卡片
 * @creat 2021/05/19-15:14
 */


public class MessageCellCtrl implements Initializable {


    public Label transferTime;
    public Label text;
    public AnchorPane root;
    public ImageView photo;
    public Label userName;
    private Image otherUserPhoto;
    private Image myPhoto;

    public void init(MessageCon messageCon){
        LocalDateTime transferTime = messageCon.getTransferTime();
        String formatTransferTime = TimeUtil.formatLocalDateTime(transferTime);
        int sendUserID = messageCon.getSendUserID();
        String sendUserName = messageCon.getSendUserName();
        String messageText = messageCon.getMessageText();
        this.transferTime.setText(formatTransferTime);
        text.setText(messageText);
        Tooltip tooltip= new Tooltip(messageText);
        tooltip.setFont(new Font(14));
        Tooltip.install(text,tooltip);

        if (sendUserID== ClientStatus.user.getUserID()){
            photo.setLayoutX(495);
            photo.setImage(myPhoto);
            this.userName.setVisible(false);
        }else {
            photo.setLayoutX(10);
            photo.setImage(otherUserPhoto);
            this.userName.setText(sendUserName);
            this.userName.setVisible(true);
        }
        photo.setVisible(true);




    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        otherUserPhoto= new Image("File:src\\resources\\image\\otherUsersPhoto.png");
        myPhoto= new Image("File:src\\resources\\image\\myPhoto.png");
    }
}
