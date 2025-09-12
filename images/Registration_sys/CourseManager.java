package com.example.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;

public class CourseManager {
    private ObservableList<Course> courses = FXCollections.observableArrayList();


    public void addCourse(Course c) {
        courses.add(c);
    }

    public void removeCourse(String code) {
        courses.removeIf(c -> c.getCode().equalsIgnoreCase(code));
    }

    public Course searchByCode(String code) {
        for (Course c : courses) {
            if (c.getCode().equalsIgnoreCase(code)) return c;
        }
        return null;
    }

    public void sortByCode() {
        int n = courses.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String code1 = courses.get(j).getCode();
                String code2 = courses.get(j + 1).getCode();
                if (code1.compareToIgnoreCase(code2) > 0) {

                    Course temp = courses.get(j);
                    courses.set(j, courses.get(j + 1));
                    courses.set(j + 1, temp);
                }
            }
        }


        FXCollections.observableArrayList(courses);
    }




    public ObservableList<Course> getCourses() {
        return courses;
    }

}