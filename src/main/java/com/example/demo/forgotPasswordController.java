package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


public class forgotPasswordController implements windowFunction{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_id;

    @FXML
    private Button forgot_password_button;

    @FXML
    private TextField phone_number_field;

    @FXML
    private TextField user_field;

    @FXML
    void initialize() {
        forgot_password_button.setOnAction(actionEvent -> {
            String login_text = user_field.getText().trim();
            String phone_email_text = phone_number_field.getText().trim();

            if(!login_text.equals("") && !phone_email_text.equals("")){
                checkUser(login_text, phone_email_text);
            } else {
                animationShake userLoginShake = new animationShake(user_field);
                animationShake userPhoneShake = new animationShake(phone_number_field);
                userLoginShake.playAnimation();
                userPhoneShake.playAnimation();
            }
        });
        back_id.setOnAction(actionEvent -> {
            back_id.getScene().getWindow().hide();
            openNewWindow("hello-view.fxml");
        });
    }
    private void checkUser(String username, String phoneNumOrEmail){
        DatabaseHandler databaseHandler = new DatabaseHandler();
        User user = new User();
        user.setUser_name(username);
        user.setPhone_number(phoneNumOrEmail);
        user.setUser_email(phoneNumOrEmail);
        ResultSet resultSet = databaseHandler.checkUser(user);
        try {
            if (resultSet.next()) {
                forgot_password_button.getScene().getWindow().hide();
                HelloApplication.max_user_name = username;
                openNewWindow("change_password_window.fxml");
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("User NOT FOUND");
                alert.setContentText("");

                alert.showAndWait();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public void openNewWindow(String window){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }



}
