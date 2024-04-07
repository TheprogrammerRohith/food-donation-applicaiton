package com.example.food_application;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Donor_AddPage extends Fragment {

    ImageView post;
    FirebaseAuth auth;
    FirebaseUser user;
    Button submit;
    EditText name,quantity;
    LinearLayout linear_layout;
    String userId;
    ArrayList<String> list=new ArrayList<>();
    final ArrayList<String> foodnames = new ArrayList<>();
    final ArrayList<String> foodquantities = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView= inflater.inflate(R.layout.fragment_donor_add_page, container, false);
        post=myView.findViewById(R.id.button);
        name=myView.findViewById(R.id.name);
        quantity=myView.findViewById(R.id.quantity);
        submit=myView.findViewById(R.id.l_l_button);

        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        if(user!=null){
            userId = user.getUid();
            Log.d("User id",userId);
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Donors").child(userId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String value = (String) ds.getValue();
                                list.add(value);
                            }
                        }
                    }
                    else{
                        Log.d("Firebase","something went wrong");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase", "Error fetching user details: " + error.getMessage());
                }
            });

        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food_name=name.getText().toString();
                String food_quantity=quantity.getText().toString();
                if(food_name.equals("")){
                    name.setError("required..");
                    return;
                }
                if(food_quantity.equals("")){
                    quantity.setError("required..");
                    return;
                }
                foodnames.add(food_name);
                foodquantities.add(food_quantity);
                linear_layout=myView.findViewById(R.id.linear_layout);
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(100,50,100,50);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.border_layout);

                LinearLayout.LayoutParams del_button_layoutparams=new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                ImageView delete=new ImageView(getContext());
                delete.setImageResource(R.drawable.baseline_delete_24);
                delete.setLayoutParams(del_button_layoutparams);
                del_button_layoutparams.topMargin=20;
                del_button_layoutparams.leftMargin=350;
                linearLayout.addView(delete);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < linearLayout.getChildCount(); i++) {
                            View childView = linearLayout.getChildAt(i);
                            // Check if the child view is a TextView
                            if (childView instanceof TextView) {
                                TextView textView = (TextView) childView;
                                // Check the text of the TextView to determine if it contains food name or quantity
                                String text = textView.getText().toString();
                                if (text.startsWith("Food Name")) {
                                    String foodName = text.substring(text.indexOf("-") + 2).trim();
                                    // Remove the food name from the ArrayList
                                    foodnames.remove(foodName);
                                } else if (text.startsWith("Food Quantity")) {
                                    String foodQuantity = text.substring(text.indexOf("-") + 2).trim();
                                    // Remove the food quantity from the ArrayList
                                    foodquantities.remove(foodQuantity);
                                }
                            }
                        }
                        // Remove the parent layout (the LinearLayout containing the TextViews and Button)
                        ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
                    }
                });

                LinearLayout.LayoutParams textview1_layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                TextView textViewFoodName = new TextView(getContext());
                textViewFoodName.setText("Food Name - "+food_name);
                textViewFoodName.setGravity(Gravity.CENTER);
                textViewFoodName.setTextSize(20);
                textViewFoodName.setLayoutParams(textview1_layoutParams);
                //textview1_layoutParams.topMargin=50;
                linearLayout.addView(textViewFoodName);

                LinearLayout.LayoutParams textview2_layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                TextView textViewFoodQuantity = new TextView(getContext());
                textViewFoodQuantity.setText("Food Quantity - "+food_quantity);
                textViewFoodQuantity.setGravity(Gravity.CENTER);
                textViewFoodQuantity.setTextSize(20);
                textViewFoodQuantity.setLayoutParams(textview2_layoutParams);
                textview2_layoutParams.topMargin=50;
                textview2_layoutParams.bottomMargin=50;
                linearLayout.addView(textViewFoodQuantity);

                linear_layout.addView(linearLayout);
                name.setText("");
                quantity.setText("");
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<foodnames.size();i++){
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("FoodDetails");
                    String id=userRef.push().getKey();
                    FoodDetails fd=new FoodDetails(foodnames.get(i),foodquantities.get(i),list.get(1), list.get(2),userId,id);
                    userRef.child(id).setValue(fd);
                    Toast.makeText(getContext(),"successfully posted",Toast.LENGTH_SHORT).show();
                    foodnames.clear();
                    foodquantities.clear();
                }

            }
        });

        return myView;
    }
}