package client.controller;

import client.model.server.ClientInterfaceEvent;
import client.model.server.ClientStatus;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import server.model.entity.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tptogiar
 * @Descripiton: 聊天列表的用户卡片
 * @creat 2021/05/19-00:22
 */


public class UserCellCtrl implements Initializable {


    public AnchorPane root;
    public Label userName;
    public ImageView onlineIcon;
    public Label detail;

    private User user;
    private ClientChatWinCtrl clientChatWinCtrl;

    public void init(User user, ClientChatWinCtrl clientChatWinCtrl) {
        this.user=user;
        String userName = user.getUserName();
        boolean online = user.isOnline();


        if (userName.equals(ClientStatus.user.getUserName())){
            this.userName.setText(userName+"(我)");
        }else {
            this.userName.setText(userName);
        }


        if(online){
            onlineIcon.setVisible(true);
        }else{
            onlineIcon.setVisible(false);
        }

        this.clientChatWinCtrl=clientChatWinCtrl;
    }


    public void init(ClientChatWinCtrl clientChatWinCtrl){
        this.clientChatWinCtrl=clientChatWinCtrl;
    }


    public void handleMouseClicked(MouseEvent mouseEvent) throws Exception {
        ClientInterfaceEvent.displayChatWin(clientChatWinCtrl,user.getUserID());
    }


    public ClientChatWinCtrl getClientChatWinCtrl() {
        return clientChatWinCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onlineIcon.setImage(new Image("File:src\\resources\\image\\onlineIcon.png"));
    }
}
