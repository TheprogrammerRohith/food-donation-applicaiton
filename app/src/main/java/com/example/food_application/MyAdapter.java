package com.example.food_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<FoodDetails> list;
    ArrayList<String> list2;
    public MyAdapter(Context context, ArrayList<FoodDetails> list,ArrayList<String> list2) {
        this.context = context;
        this.list = list;
        this.list2=list2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_view,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FoodDetails fd=list.get(position);
        holder.f_name.setText(fd.getFoodName());
        holder.f_quantity.setText(fd.getFoodQuantity());
        holder.d_name.setText(fd.getName());
        holder.d_phno.setText(fd.getContactNo());
        holder.engage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String donorId=fd.getDonorId();
                Engaged obj=new Engaged(list2.get(1),list2.get(2),fd.getFoodName(),fd.getFoodQuantity());
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Engaged").child(donorId);
                userRef.push().setValue(obj);
                Toast.makeText(context, "engaged", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView f_name,f_quantity,d_name,d_phno;
        Button engage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            f_name=itemView.findViewById(R.id.f_name);
            f_quantity=itemView.findViewById(R.id.f_quantity);
            d_name=itemView.findViewById(R.id.d_name);
            d_phno=itemView.findViewById(R.id.d_phno);
            engage=itemView.findViewById(R.id.engage_btn);
        }
    }
}
