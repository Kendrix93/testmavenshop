package com.example.shopfx;

import com.example.shopfx.persistence.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ShopApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FileReader fileReader = new FileReader();
        fileReader.readUsersFromFile();
        fileReader.readItemsFromFile();

        FXMLLoader fxmlLoader = new FXMLLoader(ShopApplication.class.getResource("controller/login-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(ShopApplication.class.getResource("controller/user-actions-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("style/loginViewStyle.css");
//        scene.getStylesheets().add("style/all.css");
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();


    }


    public static void main(String[] args) {
        launch();
    }
}