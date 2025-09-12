package com.example.student;

import database.data;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Signup {
    Label FirstName;
    Label SecondName;
    Label password;
    Label id;
    TextField t3;
    TextField t1;
    TextField t2;
    PasswordField passT;
    Stage stage;
    Scene first;
    Scene second;
    Label Ge;
    RadioButton rb1 = new RadioButton("Male ");
    RadioButton rb2 = new RadioButton("Female ");
    public Button signup;
    public Button back;
    final int WIDTH = 500;
    final int HEIGHT = 500;
    Connection conn = null;
    ResultSet res = null;
    PreparedStatement pst = null;
    ToggleGroup tg = new ToggleGroup();


    public Signup(Stage stage, Scene previousScene , Scene backscene) {
        this.stage = stage;
        this.first = previousScene;
        this.second=backscene;
        this.initControl();
        this.initActive();

    }

    void initActive() {
        signup.setOnAction(event -> {
            conn = data.dbConnection();
            if (conn == null) {
                System.out.println("Connection failed");
                return;
            }

            if (t3.getText().isEmpty() || passT.getText().isEmpty()) {
                System.out.println("Fields cannot be empty");
                return;
            }

            String checkSql = "SELECT COUNT(*) FROM INFO WHERE ID = ?";
            String insertSql = "INSERT INTO INFO (ID, PASSWORD) VALUES (?, ?)";

            try {

                pst = conn.prepareStatement(checkSql);
                pst.setInt(1, Integer.parseInt(t3.getText()));
                ResultSet rs = pst.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("ID already exists. Please use a different one.");
                    return;
                }


                pst = conn.prepareStatement(insertSql);
                pst.setInt(1, Integer.parseInt(t3.getText()));
                pst.setString(2, passT.getText());

                int i = pst.executeUpdate();
                if (i == 1) {
                    System.out.println("Data inserted successfully");
                    Stage currentStage = (Stage) signup.getScene().getWindow();
                    currentStage.setScene(second);
                }
                else {
                    System.out.println("Insertion failed");
                }


            } catch (NumberFormatException nfe) {
                System.out.println("Invalid number format: " + nfe.getMessage());
            } catch (SQLException sqlEx) {
                System.out.println("SQL Error: " + sqlEx.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });





    }

    void initControl() {
        this.FirstName = new Label("First Name :");
        this.SecondName = new Label("Second Name :");
        this.password = new Label("Password :");
        this.signup = new Button("Sign Up");
        this.id = new Label("id :");
        this.t1 = new TextField();
        this.t2 = new TextField();
        this.t3 = new TextField();
        this.back = new Button("Back");
        this.passT = new PasswordField();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
    }

    public Scene getScene() {
        GridPane g2 = new GridPane();
        g2.add(this.FirstName, 0, 0);
        g2.add(this.SecondName, 0, 1);
        g2.add(this.password, 0, 3);
        g2.add(this.id, 0, 2);
        g2.add(this.t1, 1, 0);
        g2.add(this.t2, 1, 1);
        g2.add(this.t3, 1, 2);
        g2.add(this.passT, 1, 3);
        g2.add(rb1,1,4);
        g2.add(rb2,2,4);
        g2.add(this.signup, 1, 5);
        g2.add(back, 1, 6);
        g2.setAlignment(Pos.CENTER);
        g2.setHgap(15);
        g2.setVgap(15);
        Scene ss=new Scene(g2,800,800);
        ss.getStylesheets().add(getClass().getResource("/sh.css").toExternalForm());
        return ss;
    }
}
