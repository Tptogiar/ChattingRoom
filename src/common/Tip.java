package common;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @author Tptogiar
 * @Descripiton: 弹出提示框
 * @creat 2021/05/15-12:12
 */


public class Tip {

    /**
     * @Author: Tptogiar
     * @Description: 由于调用此函数的大多都不是在javafx线程，
     * 而界面事件，每个函数都交给javafx线程执行
     * @Date: 2021/5/18-20:06
     */




    public static void  error(String contain){
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(contain);
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            }
//        });
    }


    public static void info(String info){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(info);
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void froceOffline(String tipString) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("服务器："+tipString);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get()==ButtonType.OK){
                        System.exit(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
