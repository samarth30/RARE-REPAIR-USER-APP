package com.example.dell.rare.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.rare.Adapter.BrandAdapter;
import com.example.dell.rare.R;
import com.example.dell.rare.classes.ExampleItem;
import com.example.dell.rare.classes.ExampleItemVerticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrandSelect extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ExampleItem> list;
    ProgressBar progressBar;
    BrandAdapter adapter;
    String brandapi = "https://samarth-rare-app.herokuapp.com/brands";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_select);
        list = new ArrayList<>();
        progressBar = findViewById(R.id.progressBarBrandSelect);
        progressBar.setVisibility(View.VISIBLE);
        parseData();
        recyclerView = findViewById(R.id.recyclerViewBrand);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new BrandAdapter(this,list);
        recyclerView.setAdapter(adapter);

        TextView brand = findViewById(R.id.textView);
        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrandSelect.this, PhoneSelect.class));
            }
        });
    }

    private void parseData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, brandapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String brand = jsonObject.getString("brand");
                        String image = jsonObject.getString("brandImgUrl");
                        String image1 = "https://samarth-rare-app.herokuapp.com/"+image;
                        list.add(new ExampleItem(brand,image1));
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


}
