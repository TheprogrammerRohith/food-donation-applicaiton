package com.example.food_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CollectorPage extends AppCompatActivity {

    private ImageView logout;
    FirebaseAuth auth;
    FirebaseUser user;
    private DatabaseReference dRef;
    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<FoodDetails> list;
    ArrayList<String> list2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collector_page);
        logout=findViewById(R.id.logout);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        if(user==null){
            Intent intent=new Intent(CollectorPage.this,CollectorLogin.class);
            startActivity(intent);
        }
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
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(CollectorPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView=findViewById(R.id.collector_recycler_view);
        dRef= FirebaseDatabase.getInstance().getReference().child("FoodDetails");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myAdapter=new MyAdapter(this,list,list2);
        recyclerView.setAdapter(myAdapter);
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    FoodDetails obj=ds.getValue(FoodDetails.class);
                    list.add(obj);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
