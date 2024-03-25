package com.example.food_application;

public class Engaged {
    String name,contactNo,foodname,foodquantity;

    public Engaged(){}

    public Engaged(String name, String contactNo, String foodname, String foodquantity) {
        this.name = name;
        this.contactNo = contactNo;
        this.foodname = foodname;
        this.foodquantity = foodquantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodquantity() {
        return foodquantity;
    }

    public void setFoodquantity(String foodquantity) {
        this.foodquantity = foodquantity;
    }
}
