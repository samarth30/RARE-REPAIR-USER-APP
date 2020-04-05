package com.example.dell.rare.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dell.rare.R;
import com.example.dell.rare.classes.ExampleItem;
import com.example.dell.rare.classes.search;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterSuggestionSearch extends RecyclerView.Adapter<AdapterSuggestionSearch.ExampleViewHolder> {


    private Context mContext;
    private ArrayList<search> mExampleList;

    public AdapterSuggestionSearch(Context mContext, ArrayList<search> mExampleList) {
        this.mContext = mContext;
        this.mExampleList = mExampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_suggestion,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        search currentItem = mExampleList.get(position);
        String title = currentItem.getSuggestion();

        holder.name.setText(title);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder {


            public TextView name;

            public ExampleViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.suggestion);
            }
        }
    }
