package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHandler {
    public static Connection connect_to_db(){
        Connection con = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EndTerm", "postgres", "0000");
        }catch (Exception e){
            System.out.println(e);
        }
        return con;
    }
    public void signUpUser(User user){
        String insert = "INSERT INTO users(user_name,user_password, user_email,phone_number, gender) VALUES(?,?,?,?,?)";

        try{
            PreparedStatement ps = connect_to_db().prepareStatement(insert);
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getUser_password());
            ps.setString(3, user.getUser_email());
            ps.setString(4, user.getPhone_number());
            ps.setString(5, user.getGender());

            ps.executeUpdate();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public ResultSet getUser(User user){
        ResultSet result = null;
        String select = "SELECT * FROM users WHERE user_name =? AND user_password =?";

        try{
            PreparedStatement ps = connect_to_db().prepareStatement(select);
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getUser_password());

            result = ps.executeQuery();
        } catch (Exception e){
            System.out.println(e);
        }
        return result;
    }
    public ResultSet checkUser(User user){
        ResultSet result = null;
        String select = "SELECT * FROM users WHERE user_name =? AND (phone_number =? OR user_email =?)";
        try{
            PreparedStatement ps = connect_to_db().prepareStatement(select);
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getPhone_number());
            ps.setString(3, user.getUser_email());
            result = ps.executeQuery();
        }catch (Exception e){
            System.out.println(e);
        }

        return result;
    }
    public void updateUser(User user){
        String update = "UPDATE users SET user_password = ? WHERE user_name = ?";
        try{
            PreparedStatement ps = connect_to_db().prepareStatement(update);
            ps.setString(1, user.getUser_password());
            ps.setString(2, user.getUser_name());
            ps.executeUpdate();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public static ObservableList<Book> createBooks(){
        ObservableList<Book> books = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = connect_to_db().prepareStatement("SELECT * FROM books");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                books.add(new Book(rs.getInt("id"), rs.getString("book_name"), rs.getString("author"), rs.getString("genre"), rs.getInt("release_year"), rs.getInt("age_limit")));
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return books;
    }
    public static ObservableList<Book> createUserBooks(String currentUserName){
        ObservableList<Book> userBooks = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = connect_to_db().prepareStatement("SELECT * FROM " + currentUserName + "_table");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                userBooks.add(new Book(rs.getInt("id"), rs.getString("book_name"), rs.getString("author"), rs.getString("genre"), rs.getInt("release_year"), rs.getInt("age_limit")));
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return userBooks;
    }
    public static void deleteData(int id, String currentUserName){
        Connection conn = DatabaseHandler.connect_to_db();
        try{
            PreparedStatement ps2 = connect_to_db().prepareStatement("SELECT * FROM books WHERE id = ?");
            ps2.setInt(1, id);
            ResultSet rs = ps2.executeQuery();

            PreparedStatement ps3 = connect_to_db().prepareStatement("INSERT INTO " + currentUserName + "_table" + " (id, book_name, author, genre, release_year, age_limit) VALUES(?,?,?,?,?,?)");
            rs.next();
            ps3.setInt(1, rs.getInt("id"));
            ps3.setString(2, rs.getString("book_name"));
            ps3.setString(3, rs.getString("author"));
            ps3.setString(4, rs.getString("genre"));
            ps3.setInt(5, rs.getInt("release_year"));
            ps3.setInt(6, rs.getInt("age_limit"));
            ps3.executeUpdate();

            PreparedStatement ps = conn.prepareStatement("DELETE FROM books where id = ?");
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public void createTable(User user){
        Connection conn = DatabaseHandler.connect_to_db();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + user.getUser_name() + "_table("
                + "id INTEGER NOT NULL,"
                + "book_name VARCHAR(50) NOT NULL,"
                + "author VARCHAR(50) NOT NULL,"
                + "genre VARCHAR(50) NOT NULL,"
                + "release_year INTEGER NOT NULL,"
                + "age_limit INTEGER NOT NULL,"
                + "PRIMARY KEY (id))";
        try{
            PreparedStatement ps = conn.prepareStatement(createTableSQL);
            ps.execute();
            System.out.println("created");
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public static void truncateTable(String currentUserName){
        Connection conn = DatabaseHandler.connect_to_db();
        String truncateTableSQL = "TRUNCATE " + currentUserName + "_table";
        try{
            PreparedStatement ps = conn.prepareStatement(truncateTableSQL);
            ps.execute();
            System.out.println("truncated");
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
