package com.example.food_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder3> {

    Context context;
    ArrayList<Engaged2> list;


    public MyAdapter3(Context context, ArrayList<Engaged2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_view,parent,false);
        return new MyAdapter3.MyViewHolder3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        Engaged2 obj=list.get(position);
        holder.f_name.setText(obj.getFoodname());
        holder.f_quantity.setText(obj.getFoodquantity());
        holder.d_name.setText(obj.getName());
        holder.d_phno.setText(obj.getContactNo());
        holder.engage.setText("engaged");
        holder.engage.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder{

        TextView f_name,f_quantity,d_name,d_phno;
        Button engage;

        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            f_name=itemView.findViewById(R.id.f_name);
            f_quantity=itemView.findViewById(R.id.f_quantity);
            d_name=itemView.findViewById(R.id.d_name);
            d_phno=itemView.findViewById(R.id.d_phno);
            engage=itemView.findViewById(R.id.engage_btn);

        }
    }
}
