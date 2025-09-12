package com.example.student;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Course> registeredCourses = new ArrayList<>();

    public Student(String id, String name, String email) {
        super(id, name, email);
    }

    public void registerCourse(Course c) {
        registeredCourses.add(c);
    }

    public void dropCourse(Course c) {
        registeredCourses.remove(c);
    }

    public ArrayList<Course> getCourses() {
        return registeredCourses;
    }

    @Override
    public void displayInfo() {
        System.out.println("Student: " + name + " | ID: " + id);
    }
}
