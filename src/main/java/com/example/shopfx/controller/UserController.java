package com.example.shopfx.controller;

import com.example.shopfx.constants.ImageConstants;
import com.example.shopfx.constants.ViewConstants;
import com.example.shopfx.datamodel.User;
import com.example.shopfx.exceptions.ObjectExistException;
import com.example.shopfx.persistence.DataHolder;
import com.example.shopfx.persistence.DataPersistenceService;
import com.example.shopfx.utility.SceneService;
import com.example.shopfx.utility.SimpleAlarm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class UserController extends WindowController implements Initializable {


    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> userColumn;

    @FXML
    private ListView<String> listView;

    @FXML
    private Pane topPane;

    @FXML
    private Button addUserButton;

    @FXML
    private Button removeUserButton;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane anchorPane;

    private final SceneService sceneService = new SceneService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setWindow(anchorPane, topPane, cancelButton);
        refreshTable();

        sceneService.setButtonToChangeScene(addUserButton, ViewConstants.ADD_NEW_USER_VIEW);
        removeUserButton.setOnAction(e -> removeUser());

        setContextMenu();
    }

    private void setContextMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem item1 = new MenuItem("Remove");
        contextMenu.getItems().add(item1);
        userTable.setContextMenu(contextMenu);
        item1.setOnAction(e -> removeUser());
    }

    private void refreshTable(){
        Map<String, User> usersMap = DataHolder.getUsersMap();
        List<User> values = new ArrayList<>(usersMap.values());

        userColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        ObservableList<User> observableListItem = FXCollections.observableList(values);
        userTable.setItems(observableListItem);
        userTable.refresh();


        List<String> loginList = values.stream().map(User::getLogin).collect(Collectors.toList());
        listView.getItems().setAll(loginList);
        ObservableList<String> observableListItem2 = FXCollections.observableList(new ArrayList<>(usersMap.keySet()));
        listView.setItems(observableListItem2);
        listView.refresh();

        setListViewWithImage();
    }

    private void setListViewWithImage(){
        listView.setCellFactory(param -> new ListCell<String>() {
            @Override
            public void updateItem(String name, boolean empty) {
                ImageView imageView = new ImageView();
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image closeImage = new Image(ImageConstants.USER_ICON_PNG);
                    imageView.setImage(closeImage);
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    }

    private void removeUser() {
        User user = userTable.getSelectionModel().getSelectedItem();

        if (user == null) {
            SimpleAlarm.showAlarm(Alert.AlertType.ERROR, "Nothing is selected");
        } else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete item");
                alert.setHeaderText("Are you sure?");
                alert.setContentText(String.format("Are you sure you want to delete User %s ?", user.getLogin()));

                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.isPresent() && buttonType.get().equals(ButtonType.OK)) {
                    new DataPersistenceService().deleteUser(user);
                    refreshTable();
                }
            } catch (ObjectExistException ex) {
                SimpleAlarm.showAlarm(Alert.AlertType.ERROR, String.format("Cannot remove user %s because user not exist!", user.getLogin()));
            }
        }
    }

}
