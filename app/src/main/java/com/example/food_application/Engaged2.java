package com.example.food_application;

public class Engaged2 {

    String name,contactNo,foodname,foodquantity,collectorId,id;

    public Engaged2(){}
    public Engaged2(String name, String contactNo, String foodname, String foodquantity, String collectorId, String id) {
        this.name = name;
        this.contactNo = contactNo;
        this.foodname = foodname;
        this.foodquantity = foodquantity;
        this.collectorId = collectorId;
        this.id = id;
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

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
