package com.example.food_application;

import java.util.ArrayList;

public class FoodDetails {

    ArrayList<String> foodNames=new ArrayList<>();
    ArrayList<String> foodQuantities=new ArrayList<>();
    String name,contactNo;

    FoodDetails(){}

    public FoodDetails(ArrayList<String> foodNames, ArrayList<String> foodQuantities, String name, String contactNo) {
        this.foodNames = foodNames;
        this.foodQuantities = foodQuantities;
        this.name = name;
        this.contactNo = contactNo;
    }

    public ArrayList<String> getFoodNames() {
        return foodNames;
    }

    public void setFoodNames(ArrayList<String> foodNames) {
        this.foodNames = foodNames;
    }

    public ArrayList<String> getFoodQuantities() {
        return foodQuantities;
    }

    public void setFoodQuantities(ArrayList<String> foodQuantities) {
        this.foodQuantities = foodQuantities;
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
}
