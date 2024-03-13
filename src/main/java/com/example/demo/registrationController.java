package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class registrationController implements windowFunction{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_id;

    @FXML
    private Button create_account_button;

    @FXML
    private TextField email_field;

    @FXML
    private RadioButton female_button;

    @FXML
    private RadioButton male_button;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField phone_number_field;

    @FXML
    private TextField user_field;

    @FXML
    void initialize() {
        //выбор мужчины или женщины
        ToggleGroup group = new ToggleGroup();
        male_button.setToggleGroup(group);
        female_button.setToggleGroup(group);

        back_id.setOnAction(actionEvent -> {
            back_id.getScene().getWindow().hide();
            openNewWindow("hello-view.fxml");
        });

        create_account_button.setOnAction(actionEvent -> {
            signUpNewUser();
        });
    }
    private void signUpNewUser(){
        DatabaseHandler databaseHandler = new DatabaseHandler();

        String user_name = user_field.getText().trim();
        String user_password = password_field.getText().trim();
        String user_email = email_field.getText().trim();
        String phone = phone_number_field.getText().trim();
        String gender = "";

        if(male_button.isSelected()){
            gender = "Male";
        } else gender = "Female";

        User user = new User(user_name, user_password, user_email, phone, gender);

        if(!user_name.equals("") && !user_password.equals("") && !user_email.equals("") && !phone.equals("") && !gender.equals("")){
            if(isValidPassword(user_password) && isValidEmail(user_email) && isValidUsername(user_name) && isValidNumber(phone)) {
                databaseHandler.signUpUser(user);
                create_account_button.getScene().getWindow().hide();
                databaseHandler.createTable(user);
                HelloApplication.rem_user_name = user.getUser_name();
                openNewWindow("login_window.fxml");
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Check again your entered data");
                alert.setContentText("");

                alert.showAndWait();
            }
        } else {
            animationShake userLoginShake = new animationShake(user_field);
            animationShake userPasswordShake = new animationShake(password_field);
            animationShake userEmailShake = new animationShake(email_field);
            animationShake userPhoneShake = new animationShake(phone_number_field);
            animationShake maleButtonShake = new animationShake(male_button);
            animationShake femaleButtonShake = new animationShake(female_button);

            userLoginShake.playAnimation();
            userPasswordShake.playAnimation();
            userEmailShake.playAnimation();
            userPhoneShake.playAnimation();
            maleButtonShake.playAnimation();
            femaleButtonShake.playAnimation();
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
    public static boolean isValidUsername(String login) {
        if (!Character.isLetter(login.charAt(0))) {
            return false;
        }
        return login.length() >= 5 && login.length() <= 30;
    }
    public static boolean isValidNumber(String number) {
        char firstChar = number.charAt(0);
        boolean numLen = number.length() == 11;
        if (firstChar == '8' && numLen) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');

        if (atIndex <= 0 || dotIndex <= 0 || dotIndex <= atIndex || dotIndex == email.length() - 1) {
            return false;
        }

        return true;
    }
}
