package com.example.dell.rare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    BrandAdapter adapter;
    String brandapi = "https://samarth-rare-app.herokuapp.com/brands";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_select);
        list = new ArrayList<>();
        parseData();
        recyclerView = findViewById(R.id.recyclerViewBrand);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new BrandAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }

//    private void parseData() {
//        list.add(new ExampleItem("samsung",R.drawable.meal));
//        list.add(new ExampleItem("Realme",R.drawable.flower));
//        list.add(new ExampleItem("Nokia",R.drawable.driver));
//        list.add(new ExampleItem("Sony",R.drawable.meal));
//        list.add(new ExampleItem("Geonie",R.drawable.driver));
//        list.add(new ExampleItem("MI",R.drawable.flower));
//        list.add(new ExampleItem("One Plus",R.drawable.meal));
//        list.add(new ExampleItem("VIVO",R.drawable.bookfive));
//        list.add(new ExampleItem("samsung",R.drawable.meal));
//        list.add(new ExampleItem("Realme",R.drawable.flower));
//        list.add(new ExampleItem("Nokia",R.drawable.driver));
//        list.add(new ExampleItem("Sony",R.drawable.meal));
//        list.add(new ExampleItem("Geonie",R.drawable.driver));
//        list.add(new ExampleItem("MI",R.drawable.flower));
//        list.add(new ExampleItem("One Plus",R.drawable.meal));
//        list.add(new ExampleItem("VIVO",R.drawable.bookfive));
//    }

    private void parseData() {
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(BrandSelect.this, "in here " + i, Toast.LENGTH_SHORT).show();
                        list.add(new ExampleItem(brand,image1));
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
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
