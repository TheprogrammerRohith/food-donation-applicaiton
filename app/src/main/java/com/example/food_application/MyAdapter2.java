package com.example.food_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {

    Context context;
    ArrayList<Engaged> list;

    String UserID;
    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_view2,parent,false);
        return new MyAdapter2.MyViewHolder2(v);
    }

    public MyAdapter2(Context context, ArrayList<Engaged> list,String userID) {
        this.context = context;
        this.list = list;
        UserID=userID;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Engaged obj=list.get(position);
        holder.f_name.setText(obj.getFoodname());
        holder.f_quantity.setText(obj.getFoodquantity());
        holder.c_name.setText(obj.getName());
        holder.c_phno.setText(obj.getContactNo());
        holder.c_address.setText(obj.getAddress());
        holder.donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference DRef= FirebaseDatabase.getInstance().getReference().child("Engaged").child(UserID);
                String id=obj.getId();
                DRef.child(id).removeValue();
                holder.donate.setText("Engaged");
                holder.donate.setEnabled(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView f_name,f_quantity,c_name,c_phno,c_address;
        Button donate;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            f_name=itemView.findViewById(R.id.f_name);
            f_quantity=itemView.findViewById(R.id.f_quantity);
            c_name=itemView.findViewById(R.id.c_name);
            c_phno=itemView.findViewById(R.id.c_phno);
            c_address=itemView.findViewById(R.id.c_address);
            donate=itemView.findViewById(R.id.donate_btn);
        }
    }
}
