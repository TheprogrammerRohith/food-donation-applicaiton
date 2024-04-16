package com.example.food_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder3> {

    Context context;
    ArrayList<Engaged2> list;
    static String id,userid;

    private static Engaged2 obj;
    public MyAdapter3(Context context, ArrayList<Engaged2> list,String userid) {
        this.context = context;
        this.list = list;
        this.userid=userid;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_view,parent,false);
        return new MyAdapter3.MyViewHolder3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        obj=list.get(position);
        id=obj.getId();
        holder.f_name.setText(obj.getFoodname());
        holder.f_quantity.setText(obj.getFoodquantity());
        holder.d_name.setText(obj.getName());
        holder.d_phno.setText(obj.getContactNo());
        holder.d_addr.setText(obj.getAddress());
        holder.engage.setText("engaged");
        holder.engage.setEnabled(false);
        holder.location.setVisibility(View.INVISIBLE);
    }
    public static void removeItems(){
        DatabaseReference dRef= FirebaseDatabase.getInstance().getReference().child("CollectorEngaged").child(userid);
        dRef.child(id).removeValue();
    }
    public static void addItems(){
        DatabaseReference dRef=FirebaseDatabase.getInstance().getReference().child("c_pastlistings").child(userid);
        String id2=dRef.push().getKey();
        String date= DateFormat.getDateInstance().format(new Date());
        CollectorPL cpl=new CollectorPL(obj.getName(),obj.getContactNo(),obj.getAddress(),obj.getFoodname(),obj.getFoodquantity(),date);
        dRef.child(id2).setValue(cpl);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder{

        TextView f_name,f_quantity,d_name,d_phno,d_addr;
        Button engage;
        ImageView location;

        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            f_name=itemView.findViewById(R.id.f_name);
            f_quantity=itemView.findViewById(R.id.f_quantity);
            d_name=itemView.findViewById(R.id.d_name);
            d_phno=itemView.findViewById(R.id.d_phno);
            d_addr=itemView.findViewById(R.id.d_addr);
            engage=itemView.findViewById(R.id.engage_btn);
            location=itemView.findViewById(R.id.location);
        }
    }
}
