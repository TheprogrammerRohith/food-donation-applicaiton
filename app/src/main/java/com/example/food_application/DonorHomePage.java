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

public class DonorHomePage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private ImageView logout;
    private Donor_Dashboard donorDashboard;
    private Donor_AddPage donorAddPage;
    private DonorPastListings donorPastListings;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_homepage);
        logout=findViewById(R.id.logout_btn);
        bottomNavigationView=findViewById(R.id.btmnav);
        frameLayout=findViewById(R.id.main_frame);
        donorDashboard=new Donor_Dashboard();
        donorAddPage=new Donor_AddPage();
        donorPastListings=new DonorPastListings();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(DonorHomePage.this, MainActivity.class);
                startActivity(intent);
            }
        });
        setFragment(donorDashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId==R.id.dashboard){
                    setFragment(donorDashboard);
                    return true;
                }
                else if (itemId==R.id.add_items) {
                    setFragment(donorAddPage);
                    return true;
                }
                else if(itemId==R.id.past_list){
                    setFragment(donorPastListings);
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
