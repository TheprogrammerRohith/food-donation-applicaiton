package com.example.food_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CollectorHomePage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private ImageView logout;
    private Collector_Dashboard collectorDashboard;
    private Collector_ViewItems collectorViewItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collector_homepage);
        logout=findViewById(R.id.logout_btn);
        bottomNavigationView=findViewById(R.id.btmnav);
        frameLayout=findViewById(R.id.main_frame);
        collectorDashboard=new Collector_Dashboard();
        collectorViewItems=new Collector_ViewItems();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(CollectorHomePage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        setFragment(collectorDashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId==R.id.c_dashboard){
                    setFragment(collectorDashboard);
                    return true;
                }
                else if (itemId==R.id.view_items) {
                    setFragment(collectorViewItems);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }
}
