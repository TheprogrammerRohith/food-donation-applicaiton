package com.example.food_application;

public class DonorPL {
    String name,contactNo,address,foodname,foodquantity,date;

    public DonorPL(){}

    public DonorPL(String name, String contactNo, String address, String foodname, String foodquantity, String date) {
        this.name = name;
        this.contactNo = contactNo;
        this.address = address;
        this.foodname = foodname;
        this.foodquantity = foodquantity;
        this.date = date;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
