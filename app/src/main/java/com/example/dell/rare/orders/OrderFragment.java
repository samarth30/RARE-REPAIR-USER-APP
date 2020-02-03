package com.example.dell.rare.orders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.rare.Adapter.ExampleAdapter;
import com.example.dell.rare.R;
import com.example.dell.rare.UI.Firstpage;
import com.example.dell.rare.UI.SharedPrefManager;
import com.example.dell.rare.classes.ExampleItemVerticle;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {
    View view;
    Context context;

    public OrderFragment() {
        // Required empty public constructor
    }

    String ordermeapi = "https://samarth-rare-app.herokuapp.com/orders/me";
    RecyclerView recyclerView;
    List<ExampleItemVerticle> list;
    ProgressBar progressBar;
    ExampleAdapter adapter;
    RequestQueue requestQueue;
    ShimmerFrameLayout mShimmerViewContainer;
    BackgroundTask backgroundTask;
    SwipeRefreshLayout swipeRefreshOrders;
    ConstraintLayout constraintLayoutProgress;
    boolean x=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        context = view.getContext();
        constraintLayoutProgress = view.findViewById(R.id.progressBarconst);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        progressBar = view.findViewById(R.id.progressBarOrders);
        if (x) {
            mShimmerViewContainer.setVisibility(View.GONE);
            constraintLayoutProgress.setVisibility(View.VISIBLE);
        }else{
            mShimmerViewContainer.startShimmerAnimation();
        }


//        backgroundTask = new BackgroundTask(context);

//        swipeRefreshOrders = view.findViewById(R.id.swipeRefreshOrders);

//        swipeRefreshOrders.setOnRefreshListener(this);
//        swipeRefreshOrders.setColorScheme(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        if(!x) {
            parseData();
        }else{
            parseData();
        }

//        swipeRefreshOrders.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        parseData();
//                    }
//                }
//        );

        recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new ExampleAdapter(getContext(), (ArrayList<ExampleItemVerticle>) list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);



//        PushDownAnim.setPushDownAnimTo( recyclerView )
//                .setOnClickListener( new View.OnClickListener(){
//                    @Override
//                    public void onClick( View view ){
//
//                    }
//
//                } );


        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<ExampleItemVerticle>();

//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//        list.add(new ExampleItemVerticle("samarth", "samarth", "samarth", "samarth", "samarth", "samarth"));
//    }
    }
//
//    @Override
//    public void onRefresh() {
//         parseData();
//    }

    private void parseData() {
        x = true;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ordermeapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    int x = jsonArray.length() - 1;
                    for (int i = x; i >= 0; i--) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String completed = jsonObject.getString("completed");
                        String brand = jsonObject.getString("phoneBrand");
                        String model = jsonObject.getString("phoneModel");
                        String color = jsonObject.getString("phoneColor");
                        String defects = jsonObject.getString("phoneDefects");
                        String id = jsonObject.getString("_id");
                        list.add(new ExampleItemVerticle(completed, brand, model, color, defects, id));
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.setVisibility(View.GONE);
                    constraintLayoutProgress.setVisibility(View.GONE);
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
                SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
                String token = sharedPrefManager.loadToken();
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };


        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}
