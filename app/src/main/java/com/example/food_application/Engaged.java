package com.example.food_application;

public class Engaged {
    String name,contactNo,address,foodname,foodquantity,id;

    public Engaged(){}

    public Engaged(String name, String contactNo,String address, String foodname, String foodquantity,String id) {
        this.name = name;
        this.contactNo = contactNo;
        this.address=address;
        this.foodname = foodname;
        this.foodquantity = foodquantity;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
