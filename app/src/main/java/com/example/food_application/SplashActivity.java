package com.example.food_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference donorRef,collectorRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user!=null){
                    String uId=user.getUid();
                    donorRef= FirebaseDatabase.getInstance().getReference().child("Donors");
                    collectorRef=FirebaseDatabase.getInstance().getReference().child("Collectors");
                    donorRef.child(uId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot ds:snapshot.getChildren()){
                                    Users obj=ds.getValue(Users.class);
                                    if(uId.equals(obj.getUid())){
                                        Intent intent=new Intent(SplashActivity.this,DonorHomePage.class);
                                        startActivity(intent);
                                        return;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            return;
                        }
                    });
                    collectorRef.child(uId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot ds:snapshot.getChildren()){
                                    Users obj=ds.getValue(Users.class);
                                    if(uId.equals(obj.getUid())){
                                        Intent intent=new Intent(SplashActivity.this,CollectorHomePage.class);
                                        startActivity(intent);
                                        return;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            return;
                        }
                    });
                }
                else{
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        },2000);




    }
}