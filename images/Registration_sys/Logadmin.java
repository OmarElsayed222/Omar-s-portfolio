package com.example.student;


import database.data;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logadmin {
    Connection conn = null;
    ResultSet res = null;
    PreparedStatement pst = null;
    Label admin;
    Label UserName;
    Label Password;
    private Scene scene;

    public TextField Ti1;
    public PasswordField Tp1;
    public Button login;
    public Button back;
    final int WIDTH = 600;
    final int HEIGHT = 600;

    public Logadmin() {
        this.initControl();
    }
    public void initActive(Scene nextScene, javafx.stage.Stage stage) {
        login.setOnAction(event -> {
            try {
                int id = Integer.parseInt(Ti1.getText());
                String password = Tp1.getText();

                if (isValidLogin(id, password)) {
                    stage.setScene(nextScene);
                } else {
                    javafx.scene.control.Alert error = new javafx.scene.control.Alert(
                            javafx.scene.control.Alert.AlertType.ERROR, "Invalid login");
                    error.show();
                }
            } catch (NumberFormatException ex) {
                javafx.scene.control.Alert error = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.ERROR, "ID must be a number!");
                error.show();
            }
        });
    }

    private boolean isValidLogin(int id,String password) {
        String query = "SELECT * FROM ADMINS WHERE ID = ? AND PASSWORD = ?";

        try (Connection conn = data.dbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    void initControl() {
        this.admin=new Label("You are logging in as Admin");
        this.UserName = new Label("ID :");
        this.Password = new Label("Password :");
        this.Ti1 = new TextField();
        this.Tp1 = new PasswordField();
        this.login = new Button("Log In");
        this.back=new Button("Back");
    }
    public Scene getScene() {
        if (scene == null) {
            GridPane g3 = new GridPane();
            g3.add(admin, 0, 0, 4, 1);
            g3.add(this.UserName, 0, 1);
            g3.add(this.Ti1, 1, 1);
            g3.add(this.Password, 0, 2);
            g3.add(this.Tp1, 1, 2);
            g3.add(this.login, 0, 3);
            g3.add(this.back, 1, 3);


            Ti1.setPrefWidth(200);
            Tp1.setPrefWidth(200);
            login.setPrefWidth(100);
            back.setPrefWidth(100);

            g3.setAlignment(Pos.CENTER);
            g3.setHgap(15);
            g3.setVgap(15);

            scene = new Scene(g3,800,800);
            scene.getStylesheets().add(getClass().getResource("/sh.css").toExternalForm());
        }
        return scene;
    }


}
