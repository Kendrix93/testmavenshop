package com.example.shopfx.utility;

import com.example.shopfx.ShopApplication;
import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.persistence.DataHolder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneService {

    private static final String CONTROLLER = "controller/";
    private static final String CANNOT_REFRESH_SCENE = "Cannot refresh scene! ";
    private FXMLLoader fxmlLoader;

    private double initialX;
    private double initialY;

    public void changeToScene(ActionEvent event, String sceneName) {
        try {
            fxmlLoader = new FXMLLoader(ShopApplication.class.getResource(CONTROLLER + sceneName));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            scene.setFill(Color.TRANSPARENT);
            setStyleSheet(scene, sceneName);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            SimpleAlarm.showAlarm(Alert.AlertType.ERROR, CANNOT_REFRESH_SCENE + sceneName);
            ex.printStackTrace();
        }
    }

    public void openInNewWindow(String sceneName) {
        try {
            FXMLLoader fxmlLoaderSecond = new FXMLLoader(ShopApplication.class.getResource(CONTROLLER + sceneName));
            Scene scene = new Scene(fxmlLoaderSecond.load());
            setStyleSheet(scene, "");
            Stage stage = new Stage();
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            SimpleAlarm.showAlarm(Alert.AlertType.ERROR, CANNOT_REFRESH_SCENE + sceneName);
            e.printStackTrace();
        }
    }

    public void closeApplication(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setPane(Pane pane, String viewName) {
        try {
            fxmlLoader = new FXMLLoader(ShopApplication.class.getResource(CONTROLLER + viewName));
            Pane load = fxmlLoader.load();
            ObservableList<Node> children = pane.getChildren();
            pane.getChildren().removeAll(children);
            pane.getChildren().add(load);
        } catch (IOException e) {
            SimpleAlarm.showAlarm(Alert.AlertType.ERROR, "Cannot set " + viewName);
            e.printStackTrace();
        }
    }

    public void setButtonToChangeScene(Button button, String sceneName) {
        button.setOnAction(event -> changeToScene(event, sceneName));
    }

    public void changeToSceneWithLoading(String sceneName) {
        DataHolder.setLoadingScene(sceneName);
        openInNewWindow(ViewConstants.LOADING_VIEW);
    }

    public void setStyleSheet(Scene scene, String viewName) {
        ObservableList<String> stylesheets = scene.getStylesheets();

        switch (viewName) {
            case ViewConstants.LOGIN_VIEW:
                stylesheets.add("style/loginViewStyle.css");
                break;
            case ViewConstants.MAIN_MENU_VIEW:
                stylesheets.add("style/main-menu.css");
                break;
            case ViewConstants.ITEM_VIEW:
                stylesheets.add("style/table.css");
                stylesheets.add("style/all.css");
                break;
            default:
                stylesheets.add("style/all.css");
                break;
        }

    }

    public void addDraggableNode(final Node node) {
        node.setOnMousePressed(me -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                initialX = me.getSceneX();
                initialY = me.getSceneY();
            }
        });

        node.setOnMouseDragged(me -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                node.getScene().getWindow().setX(me.getScreenX() - initialX);
                node.getScene().getWindow().setY(me.getScreenY() - initialY);
            }
        });
    }

}
