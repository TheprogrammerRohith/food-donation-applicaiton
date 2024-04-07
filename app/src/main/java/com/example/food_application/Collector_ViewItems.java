package com.example.food_application;

import android.app.Notification;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Collector_ViewItems extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    private DatabaseReference dRef;
    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<FoodDetails> list;
    ArrayList<String> list2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_collector_view_items, container, false);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        if(user!=null){
            list2=new ArrayList<>();
            String userId = user.getUid();
            Log.d("User id",userId);
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Collectors").child(userId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String value = (String) ds.getValue();;
                                list2.add(value);
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
        recyclerView=v.findViewById(R.id.collector_recycler_view);
        dRef= FirebaseDatabase.getInstance().getReference().child("FoodDetails");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list=new ArrayList<>();
        String UserId= user.getUid();
        recyclerView.setAdapter(myAdapter);
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    FoodDetails obj=ds.getValue(FoodDetails.class);
                    list.add(obj);
                }
                if (list.isEmpty()) {
                    // Display a message when no items are available
                    LinearLayout.LayoutParams textview_layout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    TextView textView = new TextView(getContext());
                    textView.setText("No items available.");
                    textView.setLayoutParams(textview_layout);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(30);
                    textview_layout.setMargins(30,50,30,20);
                    recyclerView.setVisibility(View.GONE); // Hide the RecyclerView
                    // Add the TextView to your layout
                    // For example, if you have a LinearLayout:
                    FrameLayout frameLayout=v.findViewById(R.id.cv_frame_layout);
                    frameLayout.addView(textView);
                } else {
                    recyclerView.setVisibility(View.VISIBLE); // Show the RecyclerView
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myAdapter=new MyAdapter(getContext(),list,list2,UserId);
        return v;
    }
}