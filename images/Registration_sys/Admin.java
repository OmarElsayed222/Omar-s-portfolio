package com.example.student;

import java.util.ArrayList;

public class Admin extends User {
    private ArrayList<Course> managedCourses = new ArrayList<>();

    public Admin(String id, String name, String email) {
        super(id, name, email);
    }

    public void addCourse(Course c) {
        managedCourses.add(c);
    }

    public void removeCourse(Course c) {
        managedCourses.remove(c);
    }

    public ArrayList<Course> getManagedCourses() {
        return managedCourses;
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + name + " | ID: " + id + " | Email: " + email);
    }
}
