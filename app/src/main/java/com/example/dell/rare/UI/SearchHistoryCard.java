package com.example.dell.rare.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.rare.Adapter.AdapterSuggestionSearch;
import com.example.dell.rare.Adapter.RecyclerItemClickListener;
import com.example.dell.rare.R;
import com.example.dell.rare.classes.ExampleItem;
import com.example.dell.rare.classes.search;
import com.example.dell.rare.utils.Tools;
import com.example.dell.rare.utils.ViewAnimation;
import com.google.android.gms.identity.intents.UserAddressRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SearchHistoryCard extends AppCompatActivity {

    RequestQueue requestQueue;

    private ProgressBar progress_search;
    private EditText editsearch;
    private RecyclerView recyclerSuggestion;
    private AdapterSuggestionSearch mAdapterSuggestion;
    private ImageButton btback;
    String searchapi = "https://samarth-rare-app.herokuapp.com/search";
    ArrayList<search> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history_card);
        initComponent();

        recyclerSuggestion.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        search currentItem = list.get(position);
                        Intent intent = new Intent(SearchHistoryCard.this, Color.class);
                        intent.putExtra("brand",currentItem.getBrand());
                        intent.putExtra("model",currentItem.getModel());
                        intent.putExtra("color1",currentItem.getColor1());
                        intent.putExtra("color2",currentItem.getColor2());
                        intent.putExtra("color3",currentItem.getColor3());
                        intent.putExtra("color4",currentItem.getColor4());
                        intent.putExtra("color5",currentItem.getColor5());
                        intent.putExtra("image",currentItem.getImage());
                        editsearch.setText(currentItem.getSuggestion());
                        startActivity(intent);
                    }
                })
        );
    }


    private void initComponent() {
        progress_search = findViewById(R.id.editprogress);

        editsearch = findViewById(R.id.editSearch);

        editsearch.addTextChangedListener(textWatcher);

        recyclerSuggestion = (RecyclerView) findViewById(R.id.recyclerSuggestion);
        btback = findViewById(R.id.btback);
        recyclerSuggestion.setLayoutManager(new LinearLayoutManager(this));
        recyclerSuggestion.setHasFixedSize(true);

        list = new ArrayList<>();

        //set data and list adapter suggestion
        mAdapterSuggestion = new AdapterSuggestionSearch(this,list);
        recyclerSuggestion.setAdapter(mAdapterSuggestion);


        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchHistoryCard.super.onBackPressed();
            }
        });


    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence c, int i, int i1, int i2) {
            if (c.toString().trim().length() > 0) {
                    progress_search.setVisibility(View.VISIBLE);
                    list.clear();
                    parsedata(c.toString());
            }
            if(c.toString().trim().length() == 0){
                list.clear();
                mAdapterSuggestion.notifyDataSetChanged();
            }
        }
        @Override
        public void beforeTextChanged(CharSequence c, int i, int i1, int i2) {
            list.clear();
            mAdapterSuggestion.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        list.clear();
            mAdapterSuggestion.notifyDataSetChanged();
        }
    };

    private void parsedata(String c) {
        list.clear();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, searchapi+"?phone="+c, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        list.clear();
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String suggestion = jsonObject.getString("brandmodel");
                            String brand = jsonObject.getString("brand");
                            String model = jsonObject.getString("model");
                            String color1 = jsonObject.getString("color1");
                            String color2 = jsonObject.getString("color2");
                            String color3 = jsonObject.getString("color3");
                            String color4 = "";
                            String color5 = "";
                            String id = jsonObject.getString("_id");
                            String image1 = "https://samarth-rare-app.herokuapp.com/modelss/" + id;
                            list.add(new search(suggestion,brand,model,color1,color2,color3,color4,color5,image1));
                        }
                        mAdapterSuggestion.notifyDataSetChanged();
                        progress_search.setVisibility(View.GONE);
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

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
