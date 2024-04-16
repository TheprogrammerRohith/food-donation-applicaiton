package com.example.food_application;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Collector_ViewItems extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    FirebaseAuth auth;
    FirebaseUser user;
    private DatabaseReference dRef;
    private RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<FoodDetails> list;
    ArrayList<String> list2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

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

    // Adapter Class
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        Context context;
        private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
        ArrayList<FoodDetails> list;
        ArrayList<String> list2;
        private String userId;
        FoodDetails fd;

        public MyAdapter(Context context, ArrayList<FoodDetails> list,ArrayList<String> list2,String userId) {
            this.context = context;
            this.list = list;
            this.list2=list2;
            this.userId=userId;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(context).inflate(R.layout.recycler_view,parent,false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            fd=list.get(position);
            holder.f_name.setText(fd.getFoodName());
            holder.f_quantity.setText(fd.getFoodQuantity());
            holder.d_name.setText(fd.getName());
            holder.d_phno.setText(fd.getContactNo());
            holder.d_addr.setText(fd.getAddress());
            holder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setMap();
                }
            });

            holder.engage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String donorId=fd.getDonorId();
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Engaged").child(donorId);
                    String id=userRef.push().getKey();
                    Engaged obj=new Engaged(list2.get(1),list2.get(2),list2.get(0),fd.getFoodName(),fd.getFoodQuantity(),id);
                    userRef.child(id).setValue(obj);
                    Toast.makeText(context, "engaged", Toast.LENGTH_SHORT).show();
                    DatabaseReference dref=FirebaseDatabase.getInstance().getReference().child("FoodDetails");
                    String itemId = fd.getId();
                    dref.child(itemId).removeValue();
                    DatabaseReference dRef=FirebaseDatabase.getInstance().getReference().child("CollectorEngaged").child(userId);
                    String id2=dRef.push().getKey();
                    Engaged2 obj2=new Engaged2(fd.getName(),fd.getContactNo(),fd.getAddress(),fd.getFoodName(),fd.getFoodQuantity(),userId,id2);
                    dRef.child(id2).setValue(obj2);
                    holder.engage.setText("Engaged");
                    holder.engage.setEnabled(false);
                }
            });

        }
        public void setMap(){
            if (ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                // Permission has already been granted, open Google Maps
                openGoogleMaps();
            }
        }
        private void openGoogleMaps() {
            String address=fd.getAddress();
            String[] parts=address.split(",");
            StringBuilder location=new StringBuilder(100);
            location.append("geo:0,0?q=");
            for(String a:parts){
                location.append(a);
                location.append("+");
            }
            location.deleteCharAt(location.length()-1);
            String value=location.toString();
            Uri gmmIntentUri = Uri.parse(value);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(context, "Google Maps app not found", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView f_name,f_quantity,d_name,d_phno,d_addr;
            Button engage;
            ImageView location;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                f_name=itemView.findViewById(R.id.f_name);
                f_quantity=itemView.findViewById(R.id.f_quantity);
                d_name=itemView.findViewById(R.id.d_name);
                d_phno=itemView.findViewById(R.id.d_phno);
                d_addr=itemView.findViewById(R.id.d_addr);
                engage=itemView.findViewById(R.id.engage_btn);
                location=itemView.findViewById(R.id.location);
                location.setVisibility(View.VISIBLE);
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to open Google Maps
                myAdapter.openGoogleMaps();
            } else {
                // Permission denied, show a message or handle the denied permission case
                Toast.makeText(getContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
