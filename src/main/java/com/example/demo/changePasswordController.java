package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class changePasswordController implements windowFunction{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_id;

    @FXML
    private TextField confirm_password_field;

    @FXML
    private TextField password_field;

    @FXML
    private Button reset_password_button;

    @FXML
    void initialize() {
        back_id.setOnAction(actionEvent -> {
            back_id.getScene().getWindow().hide();
            openNewWindow("forgot_password_window.fxml");
        });
        reset_password_button.setOnAction(actionEvent -> {
            changePassword();
        });

    }
    private void changePassword(){
        DatabaseHandler databaseHandler = new DatabaseHandler();
        User user = new User();
        String password_text = password_field.getText().trim();
        String correct_password_text = confirm_password_field.getText().trim();

        if(password_text.equals("") || correct_password_text.equals("")){
            animationShake userPasswordShake = new animationShake(password_field);
            animationShake userCorrectPasswordShake = new animationShake(confirm_password_field);

            userPasswordShake.playAnimation();
            userCorrectPasswordShake.playAnimation();
        } else if(password_text.equals(correct_password_text)){
            if(isValidPassword(correct_password_text)){
                user.setUser_password(password_text);
                user.setUser_name(HelloApplication.max_user_name);
                databaseHandler.updateUser(user);
                reset_password_button.getScene().getWindow().hide();
                openNewWindow("hello-view.fxml");
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Password");
                alert.setContentText("Password should start with an uppercase letter and contain at least one number");

                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Passwords don't match");
            alert.setContentText("");

            alert.showAndWait();
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
    public static boolean isValidPassword(String password) {
        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasLowerCase = false;
        if (password.length() < 6 || password.length() > 20) {
            return false;
        }
        if (Character.isUpperCase(password.charAt(0))) {
            hasUppercase = true;
        }
        for (int i = 1; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
            else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            }else {
                return false;
            }

        }
        if (hasUppercase && hasNumber && hasLowerCase) {
            return true;
        }
        return false;
    }
}