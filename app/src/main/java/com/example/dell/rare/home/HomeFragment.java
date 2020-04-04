package com.example.dell.rare.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dell.rare.*;
import com.example.dell.rare.Adapter.BrandAdapter;
import com.example.dell.rare.UI.BrandSelect;
import com.example.dell.rare.UI.PhoneSelect;
import com.example.dell.rare.UI.SearchHistoryCard;
import com.example.dell.rare.UI.SharedPrefManager;


public class HomeFragment extends Fragment {
    View view;
    Context context;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

        Button getstarted = view.findViewById(R.id.getstarted);
        Button search = view.findViewById(R.id.search);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BrandSelect.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchHistoryCard.class);
                startActivity(intent);
            }
        });
        return  view;
    }

    }

