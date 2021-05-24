package client.controller;

import client.model.server.ClientInterfaceEvent;
import client.model.server.ClientStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/15-09:22
 */


public class MainCtrl implements Initializable {

    @FXML
    public VBox chatList;
    public AnchorPane chatContain;
    public Button userProfile;
    public AnchorPane allUser;
    public AnchorPane publicChatWin;

    public UserProfileCtrl userProfileCtrl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String userName = ClientStatus.user.getUserName();
        Tooltip.install(userProfile,new Tooltip(userName));
    }

    public void handleMouseClicked(MouseEvent mouseEvent) {
        ClientInterfaceEvent.displayPublicChatWin();
    }


    public void handleButtonClicked(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource()==userProfile){
            ClientInterfaceEvent.displayUserProfile();
        }
    }

    public void setUserProfileCtrl(UserProfileCtrl userProfileCtrl){
        this.userProfileCtrl=userProfileCtrl;
    }

}
