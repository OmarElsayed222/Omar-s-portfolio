package com.example.student;

abstract class User {
    protected String id, name, email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public abstract void displayInfo();
    public String getId() { return id; }
    public String getName() { return name; }
}
