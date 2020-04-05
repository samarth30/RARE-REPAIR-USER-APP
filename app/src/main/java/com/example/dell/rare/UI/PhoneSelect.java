package com.example.dell.rare.UI;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.rare.Adapter.AutoCompleteCountryAdapter;
import com.example.dell.rare.Adapter.BrandAdapter;
import com.example.dell.rare.Adapter.PhoneAdapter;
import com.example.dell.rare.Adapter.RecyclerItemClickListener;
import com.example.dell.rare.R;
import com.example.dell.rare.classes.CountryItem;
import com.example.dell.rare.classes.ExampleItem;
import com.example.dell.rare.classes.search;
import com.example.dell.rare.utils.Tools;
import com.example.dell.rare.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneSelect extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<search> list;
    ProgressBar progressBar;
    PhoneAdapter adapter;
    String modelsapi = "https://samarth-rare-app.herokuapp.com/models";
    RequestQueue requestQueue;
    TextView BrandTextView;
    String brandname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_select);
        list = new ArrayList<search>();
        progressBar = findViewById(R.id.progressBarPhoneSelect);
        progressBar.setVisibility(View.VISIBLE);
        parseData();
        recyclerView = findViewById(R.id.recyclerViewphone);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this,2), true));
        recyclerView.setHasFixedSize(true);
        adapter = new PhoneAdapter(this,list);
        recyclerView.setAdapter(adapter);
        BrandTextView = findViewById(R.id.textViewphone);

        brandname = getIntent().getExtras().getString("brandname");
        BrandTextView.setText(brandname);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        search currentItem = list.get(position);
                        Intent intent = new Intent(PhoneSelect.this, Color.class);
                        intent.putExtra("brand",currentItem.getBrand());
                        intent.putExtra("model",currentItem.getModel());
                        intent.putExtra("color1",currentItem.getColor1());
                        intent.putExtra("color2",currentItem.getColor2());
                        intent.putExtra("color3",currentItem.getColor3());
                        intent.putExtra("color4",currentItem.getColor4());
                        intent.putExtra("color5",currentItem.getColor5());
                        intent.putExtra("image",currentItem.getImage());
                        startActivity(intent);
                    }
                })
        );
    }

    private void parseData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, modelsapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i<jsonArray.length(); i++) {
                        if(jsonArray.getJSONObject(i).getString("brand").equalsIgnoreCase(brandname)) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String brand = jsonObject.getString("brand");
                            String model = jsonObject.getString("model");
                            String color1 = jsonObject.getString("color1");
                            String color2 = jsonObject.getString("color2");
                            String color3 = jsonObject.getString("color3");
                            String color4 = "";
                            String color5 = "";
                            String suggestion = jsonObject.getString("brandmodel");
                            String id = jsonObject.getString("_id");
                            String image1 = "https://samarth-rare-app.herokuapp.com/modelss/" + id;
                            list.add(new search(suggestion,brand,model,color1,color2,color3,color4,color5, image1));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
//                headers.put("", "Bearer " + token);
                return headers;
            }
        };


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);
    }
}
