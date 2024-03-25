package com.example.food_application;

import java.util.ArrayList;

public class FoodDetails {

    String name,contactNo,foodName,foodQuantity,donorId;

    FoodDetails(){}

    public FoodDetails(String foodName, String foodQuantity, String name, String contactNo,String userId) {
        this.foodName = foodName;
        this.foodQuantity = foodQuantity;
        this.name = name;
        this.contactNo = contactNo;
        donorId=userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(String foodQuantity) {
        this.foodQuantity = foodQuantity;
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

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }
}
