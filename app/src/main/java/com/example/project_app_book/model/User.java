package com.example.project_app_book.model;

import java.util.List;

public class User {
    private String address;
    private List<Boolean> collection;
    private String dateOfBirth;
    private String email;
    private String name;
    private String password;
    private String phone;

    // No-argument constructor
    public User() {
    }

    // Parameterized constructor
    public User(String address, List<Boolean> collection, String dateOfBirth, String email, String name, String password, String phone) {
        this.address = address;
        this.collection = collection;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    // Getters and setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Boolean> getCollection() {
        return collection;
    }

    public void setCollection(List<Boolean> collection) {
        this.collection = collection;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
