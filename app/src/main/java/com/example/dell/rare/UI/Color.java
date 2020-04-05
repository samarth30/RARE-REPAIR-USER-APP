package com.example.dell.rare.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.rare.Adapter.BrandAdapter;
import com.example.dell.rare.Adapter.PhoneAdapter;
import com.example.dell.rare.Adapter.RecyclerItemClickListener;
import com.example.dell.rare.R;
import com.example.dell.rare.classes.ExampleItem;
import com.example.dell.rare.classes.search;
import com.example.dell.rare.utils.Tools;
import com.example.dell.rare.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Color extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<search> list;
    ProgressBar progressBar;
    PhoneAdapter adapter;
    String modelsapi = "https://samarth-rare-app.herokuapp.com/models";
    RequestQueue requestQueue;
    TextView BrandTextView;
    String brand,model,color1,color2,color3,color4,color5,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);


        brand= getIntent().getExtras().getString("brand");
        model = getIntent().getExtras().getString("model");
        color1 = getIntent().getExtras().getString("color1");
        color2 = getIntent().getExtras().getString("color2");
        color3 = getIntent().getExtras().getString("color3");
        color4 = getIntent().getExtras().getString("color4");
        color5 = getIntent().getExtras().getString("color5");
        image = getIntent().getExtras().getString("image");


        list = new ArrayList<search>();
        progressBar = findViewById(R.id.progresscolor);
        progressBar.setVisibility(View.VISIBLE);
        parseData();
        recyclerView = findViewById(R.id.recyclerViewcolor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this,2), true));
        recyclerView.setHasFixedSize(true);
        adapter = new PhoneAdapter(this,list);
        recyclerView.setAdapter(adapter);
        BrandTextView = findViewById(R.id.textViewphone);




        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        search currentItem = list.get(position);
                        Intent intent = new Intent(Color.this, FinalPage.class);
                        intent.putExtra("brand",brand);
                        intent.putExtra("model",model);
                        intent.putExtra("color",currentItem.getModel());
                        intent.putExtra("defects","screen");
                        startActivity(intent);
                    }
                })
        );
    }

    private void parseData() {
        if(color1.length() != 0){
            list.add(new search(color1,image));
        }

        if(color2.length() != 0){
            list.add(new search(color2,image));
        }
        if(color3.length() != 0){
            list.add(new search(color3,image));
        }
        if(color4.length() != 0){
            list.add(new search(color4,image));
        }
        if(color5.length() != 0){
            list.add(new search(color5,image));
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);
    }
}
