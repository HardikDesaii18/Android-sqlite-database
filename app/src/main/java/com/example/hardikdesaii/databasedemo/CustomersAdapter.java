package com.example.hardikdesaii.databasedemo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HardikDesaii on 18/01/17.
 */

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder> implements View.OnClickListener {
    Context context;
    ArrayList<Customer> customerList;

    CustomersAdapter(Context context, ArrayList<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
    }

    @Override
    public CustomersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CustomersAdapter.MyViewHolder holder, int position) {
        Customer customer_data = customerList.get(position);
        holder.name.setText(customer_data.getName().toString());
        holder.email.setText(customer_data.getEmail().toString());
        holder.mobile.setText(customer_data.getMobile().toString());

        Log.e("customer_photo", " " + customer_data.getPhoto());
        // to get image from SD card or from destination
        Uri uriFromPath = Uri.fromFile(new File(customer_data.getPhoto()));

        // To display selected image
        Glide.with(context).load(uriFromPath).into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    @Override
    public void onClick(View v) {

    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, mobile;
        ImageView photo;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.cardview_name);
            email = (TextView) itemView.findViewById(R.id.cardview_email);
            mobile = (TextView) itemView.findViewById(R.id.cardview_mobile);
            photo = (ImageView) itemView.findViewById(R.id.cardview_thumbnail);

        }
    }
}
