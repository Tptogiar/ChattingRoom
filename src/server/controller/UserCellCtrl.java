package server.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.model.entity.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Tptogiar
 * @Descripiton:
 * @creat 2021/05/22-10:18
 */


public class UserCellCtrl implements Initializable {


    public AnchorPane root;
    public Label id;
    public Label userName;
    public ImageView onlineIcon;
    public ImageView isBlockIcon;
    private User user;
    //为使得多次点击只展示出一个窗口，所以这里将Stage作为属性，并只new一次
    private Stage userDetailStage =new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onlineIcon.setImage(new Image("File:src\\resources\\image\\onlineIcon.png"));
        isBlockIcon.setImage(new Image("File:src\\resources\\image\\isBlock.png"));
    }


    public void initUser(User user) throws IOException {
        this.user=user;
        int userID = user.getUserID();
        boolean online = user.isOnline();
        boolean isBlock = user.isBlock();

        if(online){
            onlineIcon.setVisible(true);
        }else{
            onlineIcon.setVisible(false);
        }

        if (isBlock){
            isBlockIcon.setVisible(true);
        }else{
            isBlockIcon.setVisible(false);
        }

        String userNameText = user.getUserName();
        id.setText(String.valueOf(userID));
        userName.setText(userNameText);
        userDetailStage.setResizable(false);
        userDetailStage.setScene(new Scene(SvrEventList.interfaceUserDetail(user,userDetailStage).root) );
        userDetailStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oldValue==true){
                    userDetailStage.hide();
                }
            }
        });
    }

    public void handleMouseClicked(MouseEvent mouseEvent) {
        userDetailStage.show();
    }
}
