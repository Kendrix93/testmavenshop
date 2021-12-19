package com.example.shopfx.utility;

import com.example.shopfx.persistence.DataHolder;
import javafx.scene.control.Alert;

public class SimpleAlarm {

    private SimpleAlarm() {
    }

//    public static void showAlarm(Alert.AlertType alertType, String message){
//        Alert alert = new Alert(alertType);
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getStylesheets().add("style/alarm.css");
////        dialogPane.getStyleClass().add("dialog");
//        alert.setTitle(message);
//        alert.setHeaderText(message);
//        alert.show();
//    }

    public static void showAlarm(Alert.AlertType alertType, String message){
        DataHolder.setAlarmMessage(message);

        new SceneService().openInNewWindow("alarm-view.fxml");

    }

}
