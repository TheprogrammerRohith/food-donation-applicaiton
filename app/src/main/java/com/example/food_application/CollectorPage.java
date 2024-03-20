package com.example.food_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CollectorPage extends AppCompatActivity {

    private Button button;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collector_page);

        button=findViewById(R.id.button2);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();

        if(user==null){
            Intent intent=new Intent(CollectorPage.this,CollectorLogin.class);
            startActivity(intent);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(CollectorPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
