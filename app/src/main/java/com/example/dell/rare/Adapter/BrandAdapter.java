package com.example.dell.rare.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dell.rare.R;
import com.example.dell.rare.classes.ExampleItem;

import java.util.ArrayList;

// playlist adapter
public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ExampleViewHolder> {

    private Context mContext;
    private ArrayList<ExampleItem> mExampleList;
    RequestOptions options;
    public BrandAdapter(Context context, ArrayList<ExampleItem> exampleList){
        mContext = context;
        mExampleList = exampleList;

        options = new RequestOptions().centerCrop().placeholder(R.drawable.layout_shape).error(R.drawable.layout_shape);
    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_product1,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        String title = currentItem.getTitle();
        String image = currentItem.getImageView();

        holder.name.setText(title);
        Glide.with(mContext).load(image).apply(options).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    public class ExampleViewHolder extends RecyclerView.ViewHolder{


        public TextView name;
        public ImageView imageView;
        public View view;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageviewMain);
        }
    }
}
