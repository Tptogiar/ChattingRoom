package server.controller;

import common.Tip;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import server.model.entity.User;

import java.io.IOException;

/**
 * @author Tptogiar
 * @Descripiton: 弹窗提醒
 * @creat 2021/05/22-15:58
 */


public class PopupTipCtrl {


    public AnchorPane root;
    public TextField textField;
    public Button send;
    public User user;


    public void handleButtonClicked(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource()==send){
            String text = textField.getText();
            if (text.isEmpty() || text.length()>20){
                Tip.info("文本消息不能为空且长度不能大于20");
                return;
            }
            textField.clear();
            SvrEventList.sendTip(user,text);
        }
    }


    public void init(User user){
        this.user=user;
    }

}
