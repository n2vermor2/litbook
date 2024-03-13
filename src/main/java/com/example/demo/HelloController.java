package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Node;

public class HelloController implements windowFunction{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Hyperlink dont_have_account_link;

    @FXML
    private Hyperlink forgot_password_link;

    @FXML
    private Button login_button;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField user_field;

    @FXML
    void initialize() {
        forgot_password_link.setOnAction(actionEvent -> {
            forgot_password_link.getScene().getWindow().hide();

            openNewWindow("forgot_password_window.fxml");
        });
        login_button.setOnAction(actionEvent -> {
            String login_text = user_field.getText().trim();
            String password_text = password_field.getText().trim();

            if(!login_text.equals("") && !password_text.equals("")){
                loginUser(login_text, password_text);
            } else {
                animationShake userLoginShake = new animationShake(user_field);
                animationShake userPasswordShake = new animationShake(password_field);
                userLoginShake.playAnimation();
                userPasswordShake.playAnimation();
            }
        });

        dont_have_account_link.setOnAction(actionEvent -> {
            dont_have_account_link.getScene().getWindow().hide();
            openNewWindow("registration_window.fxml");
        });
    }

    private void loginUser(String loginText, String passwordText) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        User user = new User();
        user.setUser_name(loginText);
        user.setUser_password(passwordText);
        ResultSet resultSet = databaseHandler.getUser(user);

        try {
            if (resultSet.next()) {
                login_button.getScene().getWindow().hide();
                HelloApplication.rem_user_name = loginText;
                openNewWindow("login_window.fxml");
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
