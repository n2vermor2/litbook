package com.example.demo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class loginController implements windowFunction{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableView<Book> table_view;
    @FXML
    private TableView<Book> your_book_table;
    @FXML
    private TableColumn<Book, Integer> book_age_limit;

    @FXML
    private TableColumn<Book, Integer> book_age_limit1;

    @FXML
    private TableColumn<Book, String> book_author;

    @FXML
    private TableColumn<Book, String> book_author1;

    @FXML
    private TableColumn<Book, String> book_genre;

    @FXML
    private TableColumn<Book, String> book_genre1;

    @FXML
    private TableColumn<Book, Integer> book_id;

    @FXML
    private TableColumn<Book, Integer> book_id1;

    @FXML
    private TableColumn<Book, String> book_name;

    @FXML
    private TableColumn<Book, String> book_name1;

    @FXML
    private TableColumn<Book, Integer> book_year;

    @FXML
    private TableColumn<Book, Integer> book_year1;
    @FXML
    private Button logout_button_id;
    @FXML
    private Button buy_button;
    @FXML
    private Button select_book;
    ObservableList<Book> books;
    ObservableList<Book> user_books;
    int index = -1;
    int returnIndex = 0;
    String currentUserName = HelloApplication.rem_user_name;
    @FXML
    void initialize() {
        logout_button_id.setOnAction(actionEvent -> {
            logout_button_id.getScene().getWindow().hide();
            openNewWindow("hello-view.fxml");
        });
        updateUserTable();
        updateTable();
        select_book.setOnAction(actionEvent -> {
            DatabaseHandler.deleteData(returnIndex, currentUserName);
            updateUserTable();
            updateTable();
        });
        buy_button.setOnAction(actionEvent -> {
            DatabaseHandler.truncateTable(currentUserName);
            updateUserTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Thank you for your Purchase");
            alert.setContentText("");

            alert.showAndWait();
        });
    }
    public void updateTable(){
        book_id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        book_name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
        book_author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        book_genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        book_year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("release_year"));
        book_age_limit.setCellValueFactory(new PropertyValueFactory<Book, Integer>("age_limit"));
        books = DatabaseHandler.createBooks();
        table_view.setItems(books);
    }
    public void updateUserTable(){
        book_id1.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        book_name1.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
        book_author1.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        book_genre1.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        book_year1.setCellValueFactory(new PropertyValueFactory<Book, Integer>("release_year"));
        book_age_limit1.setCellValueFactory(new PropertyValueFactory<Book, Integer>("age_limit"));
        user_books = DatabaseHandler.createUserBooks(currentUserName);
        your_book_table.setItems(user_books);
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
    @FXML
    public void getSelected(javafx.scene.input.MouseEvent mouseEvent) {
        index = table_view.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        returnIndex = book_id.getCellData(index);
    }
}
