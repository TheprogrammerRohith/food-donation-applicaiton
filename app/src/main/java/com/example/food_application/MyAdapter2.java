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

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {

    Context context;
    ArrayList<Engaged> list;

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_view2,parent,false);
        return new MyAdapter2.MyViewHolder2(v);
    }

    public MyAdapter2(Context context, ArrayList<Engaged> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Engaged obj=list.get(position);
        holder.f_name.setText(obj.getFoodname());
        holder.f_quantity.setText(obj.getFoodquantity());
        holder.c_name.setText(obj.getName());
        holder.c_phno.setText(obj.getContactNo());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView f_name,f_quantity,c_name,c_phno;
        Button engage;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            f_name=itemView.findViewById(R.id.f_name);
            f_quantity=itemView.findViewById(R.id.f_quantity);
            c_name=itemView.findViewById(R.id.c_name);
            c_phno=itemView.findViewById(R.id.c_phno);
            engage=itemView.findViewById(R.id.revert_btn);
        }
    }
}
