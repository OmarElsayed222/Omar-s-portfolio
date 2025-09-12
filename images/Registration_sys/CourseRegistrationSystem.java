package org.example.demo;

import com.example.student.*;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class CourseRegistrationSystem extends Application {

     Connection conn = null;
     ResultSet res = null;
    PreparedStatement pst = null;
   // private ObservableList<INFO> data;
    LoginScene ls=new LoginScene();
    Logadmin la=new Logadmin();


    private CourseManager courseManager = new CourseManager();
    private Student student = new Student("S01", "Ali", "ali@student.com");
    private RegistrationSystem regSys = new RegistrationSystem(courseManager);
    private TextArea registeredCoursesArea = new TextArea();

    private void updateRegisteredCourses() {
        StringBuilder sb = new StringBuilder();
        for (Course c : student.getCourses()) {
            sb.append(c.toString()).append("\n");
        }
        registeredCoursesArea.setText(sb.toString());
    }


    private  Tab adminTab(Scene startScene, Stage stage) {
        VBox root = new VBox(8);
        root.setStyle("-fx-padding: 20;");


        TextField codeField = new TextField("CS301");
        TextField nameField = new TextField("Software Engineering");
        TextField hoursField = new TextField("3");
        TextField capacityField = new TextField("30");
        TextField searchField = new TextField();
        searchField.setPromptText("Search by Code");

        TableView<Course> courseTable = new TableView<>();


        courseTable.getColumns().addAll(
                createColumn("Code", Course::getCode),
                createColumn("Name", Course::getName),
                createIntColumn("Hours", Course::getCreditHours),
                createIntColumn("Capacity", Course::getCapacity)

        );

        courseTable.setItems(courseManager.getCourses());

        courseTable.setPrefHeight(200);
        courseTable.setPrefWidth(200);
        Button addBtn = new Button("Add Course");
        Button delBtn = new Button("Delete Course");
        Button sortBtn = new Button("Sort by Code");
        Button searchBtn = new Button("Search");
        Button backBtn = new Button("Back");
        Label msg = new Label();
        Label title=new Label("Courses Table:");

        addBtn.setOnAction(e -> {
            try {
                Course c = new Course(
                        codeField.getText(),
                        nameField.getText(),
                        Integer.parseInt(hoursField.getText()),
                        Integer.parseInt(capacityField.getText())
                );
                courseManager.addCourse(c);
                msg.setText(" Course added");

            } catch (Exception ex) {
                msg.setText(" Invalid input");
            }
        });

        delBtn.setOnAction(e -> {
            courseManager.removeCourse(codeField.getText());
            msg.setText(" Course deleted");

        });

        sortBtn.setOnAction(e -> {
            courseManager.sortByCode();
            courseTable.refresh();
            msg.setText(" Sorted by Code");
        });


        searchBtn.setOnAction(e -> {
            Course found = courseManager.searchByCode(searchField.getText());
            msg.setText(found != null ? " Found: " + found.toString() : " Not found");
        });
        backBtn.setOnAction(e -> stage.setScene(startScene));

        root.getChildren().addAll(codeField, nameField, hoursField, capacityField,
                addBtn, delBtn, sortBtn, searchField, searchBtn, msg,title
                , courseTable,backBtn);
        return new Tab("Admin Panel", root);
    }




     private Tab studentTab(Scene startScene, Stage stage) {
        VBox root = new VBox(8);
        root.setStyle("-fx-padding: 20;");



        TextField codeField = new TextField();
        Button registerBtn = new Button("Register Course");
        Button dropBtn = new Button("Drop Course");
        Button showBtn = new Button("Show All");
        Button backBtn = new Button("Back");
        Label statusLabel = new Label();

        TextArea availableCourses = new TextArea();
        availableCourses.setEditable(false);
        availableCourses.setPrefHeight(80);
        availableCourses.setPrefWidth(50);


        registeredCoursesArea.setEditable(false);
        registeredCoursesArea.setPrefHeight(80);

        registerBtn.setOnAction(e -> {
            if (regSys.registerStudent(student, codeField.getText())) {
                statusLabel.setText(" Registered successfully");
                updateRegisteredCourses();
            } else {
                statusLabel.setText(" Course not found or full");
            }
        });

        dropBtn.setOnAction(e -> {
            Course c = courseManager.searchByCode(codeField.getText());
            if (c != null && student.getCourses().contains(c)) {
                student.dropCourse(c);
                statusLabel.setText(" Dropped");
                updateRegisteredCourses();
            } else {
                statusLabel.setText(" Not registered");
            }
        });

        showBtn.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            for (Course c : courseManager.getCourses()) {
                sb.append(c.toString()).append("\n");
            }
            availableCourses.setText(sb.toString());
        });
         backBtn.setOnAction(e -> stage.setScene(startScene));
        root.getChildren().addAll(codeField, registerBtn, dropBtn, showBtn,backBtn, statusLabel,
                new Label("Available Courses:"), availableCourses,
                new Label("Registered Courses:"), registeredCoursesArea);
        return new Tab("Student Panel", root);
    }


    private TableColumn<Course, String> createColumn(String title, Function<Course, String> getter) {
        TableColumn<Course, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cell -> new SimpleStringProperty(getter.apply(cell.getValue())));
        return col;
    }

    private TableColumn<Course, Integer> createIntColumn(String title, ToIntFunction<Course> getter) {
        TableColumn<Course, Integer> col = new TableColumn<>(title);
        col.setCellValueFactory(cell -> new SimpleIntegerProperty(getter.applyAsInt(cell.getValue())).asObject());
        return col;

    }

    @Override
    public void start(Stage stage) {
        courseManager.addCourse(new Course("CS101", "Intro to CS", 3, 40));
        courseManager.addCourse(new Course("CS102", "OOP", 3, 30));
        courseManager.addCourse(new Course("CS221", "Data Structures", 4, 35));

        Label l0 = new Label("Log in as ");
        Button b0 = new Button("Student");
        Button b1 = new Button("Admin");
        Button b2 = new Button("Sign up");

        GridPane G1 = new GridPane();
        G1.add(l0, 0, 0, 2, 1);
        G1.add(b0, 0, 1);
        G1.add(b1, 1, 1);
        G1.add(b2, 1, 2);
        G1.setAlignment(Pos.CENTER);
        G1.setHgap(15);
        G1.setVgap(15);
        b0.setPrefWidth(100);
        b1.setPrefWidth(100);
        b2.setPrefWidth(100);

        Scene startScene = new Scene(G1, 800, 800);
        startScene.getStylesheets().add(getClass().getResource("/sh.css").toExternalForm());
        TabPane studentPane = new TabPane(studentTab(startScene, stage));
        TabPane adminPane = new TabPane(adminTab(startScene, stage));




        Scene studentScene = new Scene(studentPane, 800, 800);
        studentScene.getStylesheets().add(getClass().getResource("/sh.css").toExternalForm());

        Scene adminScene = new Scene(adminPane, 800, 800);
        adminScene.getStylesheets().add(getClass().getResource("/sh.css").toExternalForm());

        ls.initActive(studentScene, stage);
        la.initActive(adminScene, stage);





        Signup ss = new Signup(stage, ls.getScene(), startScene);


        b0.setOnAction(e -> stage.setScene(ls.getScene()));
        b1.setOnAction(e -> stage.setScene(la.getScene()));
        b2.setOnAction(e -> stage.setScene(ss.getScene()));

        ls.back.setOnAction(e -> stage.setScene(startScene));
        la.back.setOnAction(e -> stage.setScene(startScene));
        ss.back.setOnAction(e -> stage.setScene(startScene));


        stage.setScene(startScene);
        stage.setTitle("Course Registration System");
        stage.setMinWidth(800);
        stage.setMinHeight(800);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}