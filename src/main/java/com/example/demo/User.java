package com.example.demo;

public class User {
    private static String user_name;
    private static String user_password;
    private static String user_email;
    private static String phone_number;
    private static String gender;

    public User(String user_name, String user_password, String user_email, String phone_number, String gender) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_email = user_email;
        this.phone_number = phone_number;
        this.gender = gender;
    }

    public User() {

    }

    public static String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
