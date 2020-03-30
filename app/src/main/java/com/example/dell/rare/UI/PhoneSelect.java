package com.example.dell.rare.UI;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.dell.rare.R;
import com.example.dell.rare.classes.CountryItem;
import com.example.dell.rare.classes.ExampleItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneSelect extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ExampleItem> list;
    ProgressBar progressBar;
    BrandAdapter adapter;
    String modelsapi = "https://samarth-rare-app.herokuapp.com/models";
    RequestQueue requestQueue;
    TextView BrandTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_select);
        list = new ArrayList<>();
        progressBar = findViewById(R.id.progressBarPhoneSelect);
        progressBar.setVisibility(View.VISIBLE);
        parseData();
        recyclerView = findViewById(R.id.recyclerViewphone);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new BrandAdapter(this,list);
        recyclerView.setAdapter(adapter);
        BrandTextView = findViewById(R.id.textViewphone);
    }

    private void parseData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, modelsapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String brand = null;
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        brand = jsonObject.getString("brand");
                        String model = jsonObject.getString("model");
                        String image = jsonObject.getString("modelImgUrl");
                        String image1 = "https://samarth-rare-app.herokuapp.com/"+image;
                        list.add(new ExampleItem(model,image1));
                    }
                    adapter.notifyDataSetChanged();
                    BrandTextView.setText(brand);
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


}
