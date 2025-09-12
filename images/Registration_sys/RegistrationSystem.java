package com.example.student;

public class RegistrationSystem {
    private CourseManager courseManager;

    public RegistrationSystem(CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    public boolean registerStudent(Student student, String courseCode) {
        Course c = courseManager.searchByCode(courseCode);
        if (c != null && c.getCapacity() > 0) {
            student.registerCourse(c);
            return true;
        }
        return false;
    }
}
