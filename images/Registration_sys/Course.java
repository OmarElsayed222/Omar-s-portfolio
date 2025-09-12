package com.example.student;

public class Course {
    private String code, name;
    private int creditHours, capacity;

    public Course(String code, String name, int creditHours, int capacity) {
        this.code = code;
        this.name = name;
        this.creditHours = creditHours;
        this.capacity = capacity;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getCreditHours() { return creditHours; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return code + " - " + name + " (" + creditHours + " Hrs)";
    }
}