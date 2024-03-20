package com.example.food_application;

public class Users {

    String name, phonenumber, address, username;

    public Users() {

    }

    public Users(String name, String phonenumber, String address, String username) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.address = address;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
