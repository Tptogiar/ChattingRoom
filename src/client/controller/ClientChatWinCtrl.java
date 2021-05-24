package client.controller;

import client.model.server.ClientInterfaceEvent;
import client.model.server.ClientStatus;
import common.Tip;
import common.inter.containtype.MessageCon;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import server.model.entity.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * @author Tptogiar
 * @Descripiton: 客户端子界面：聊天界面 的控制器
 * @creat 2021/05/19-14:43
 */


public class ClientChatWinCtrl {






    public Button userName;
    public Button emoji;
    public Button file;
    public Button history;
    public Button send;
    public TextArea text;
    public AnchorPane root;
    public VBox contain;
    public ScrollPane scroll;
    boolean toPublic=false;
    public User user;

    public void init(User user) {
        this.user=user;
        String userNameText = user.getUserName();
        userName.setText(userNameText);
    }


    public void init(String user){
        userName.setText(user);
        toPublic=true;
    }

    public void handleButtonClick(ActionEvent actionEvent) throws IOException {
        int sendID = ClientStatus.user.getUserID();
        String sendUserName = ClientStatus.user.getUserName();
        if (actionEvent.getSource()==send){
            if(text.getText().isEmpty()){
                Tip.info("内容不能为空");
                return;
            }
            if (text.getText().length()>2048){
                Tip.info("单次发送文本长度不能超过2048");
                return;
            }
            if (toPublic){
                toOnlines(sendID,sendUserName);
                return;
            }
            toSomeOne(sendID,sendUserName);



        }

        else if(actionEvent.getSource()==file){
            int receiverID = user.getUserID();
            ClientInterfaceEvent.choseFile(sendID,sendUserName,receiverID);
        }


    }
    /**
     * @Author Tptogiar
     * @Description 发给对应用户
     * @Date 2021/5/22-22:41
     * @param sendID
     * @param sendUserName
     */
    private void toSomeOne(int sendID, String sendUserName) throws IOException {
        int receiverID = user.getUserID();
        String messageText = this.text.getText();
        LocalDateTime transferTime = LocalDateTime.now();
        text.setText(null);
        ClientInterfaceEvent.sendTextMessage(sendID,sendUserName,receiverID,messageText,transferTime);
        if(receiverID !=ClientStatus.user.getUserID()){
            //在本地也自己发的话也展示到界面上
            MessageCon messageCon = new MessageCon(sendID, sendUserName, receiverID, messageText, transferTime);
            displayNewMessage(messageCon);
        }
    }
    /**
     * @Author Tptogiar
     * @Description 发给所用在线用户
     * @Date 2021/5/22-22:41
     * @param sendID
     * @param sendUserName
     */

    private void toOnlines(int sendID, String sendUserName) throws IOException {
        //接收索引为0（PUBLIC），所有客户端都有此索引
        int receiverID = ClientInterfaceEvent.PUBLIC;
        String messageText = this.text.getText();
        LocalDateTime transferTime = LocalDateTime.now();
        text.setText(null);

        ClientInterfaceEvent.sendTextMessageToOnline(sendID,sendUserName,receiverID,messageText,transferTime);

    }



    /**
     * @Author Tptogiar
     * @Description 拿到新的message的后将其展示在窗口上
     * @Date 2021/5/20-13:09
     */
    public void displayNewMessage(MessageCon messageCon) throws IOException {
        MessageCellCtrl messageCellCtrl = ClientInterfaceEvent.interfaceMessageCell();
        messageCellCtrl.init(messageCon);
        contain.getChildren().add(messageCellCtrl.root);
        scroll.setVvalue(scroll.getVmax());
    }

}
