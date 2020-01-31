package com.example.dell.rare.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.dell.rare.R;
import com.example.dell.rare.R.*;
import com.example.dell.rare.UI.SharedPrefManager;
import com.thekhaeng.pushdownanim.PushDownAnim;


public class Dashboard extends Fragment {
    View view;
    Context context;
    public Dashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(layout.fragment_dashboard, container, false);
        context = view.getContext();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        String email = sharedPrefManager.loadEmail();
        TextView textView = view.findViewById(R.id.textviewuser);
        textView.setText(email);

        Button Log_out = (Button) view.findViewById(R.id.log_out);

        PushDownAnim.setPushDownAnimTo( Log_out )
        .setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View view ){
                SharedPrefManager.getInstance(context).logout();

                getActivity().finishAffinity();

            }

        } );



//        Log_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPrefManager.getInstance(context).logout();
//
//                getActivity().finishAffinity();
//
//            }
//        });

        return  view;
    }
}
