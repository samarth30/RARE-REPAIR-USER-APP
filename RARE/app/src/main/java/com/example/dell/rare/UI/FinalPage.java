package com.example.dell.rare.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.rare.R;
import com.example.dell.rare.model.User;
import com.example.dell.rare.services.UserClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinalPage extends AppCompatActivity {

    String rare = "https://samarth-task-manager.herokuapp.com/rare";
    TextView brand,color,defects,model,name,number;
    String brandP,modelP,defectsP,colorP,nameP,numberP;
    Button submit;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);
        requestQueue = Volley.newRequestQueue(this);

        brand = findViewById(R.id.brand_final);
        model = findViewById(R.id.model_final);
        color = findViewById(R.id.color_final);
        defects = findViewById(R.id.defects_final);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        submit = findViewById(R.id.submit);

        brandP = getIntent().getExtras().getString("brand");
        modelP = getIntent().getExtras().getString("model");
        colorP = getIntent().getExtras().getString("color");
        defectsP = getIntent().getExtras().getString("defects");
        nameP = getIntent().getExtras().getString("name");
        numberP = getIntent().getExtras().getString("number");

        brand.setText(brandP);
        model.setText(modelP);
        color.setText(colorP);
        defects.setText(defectsP);
        name.setText(nameP);
        number.setText(numberP);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              User user = new User(nameP,numberP,brandP,modelP,colorP,defectsP);
//                sendNetworkRequest(user);
                parseData(nameP,numberP,brandP,modelP,colorP,defectsP);

            }

        });


    }


    private void parseData(final String nameP, final String numberP, final String brandP, final String modelP, final String colorP, final String defectsP) {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, rare, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean created = jsonObject.getBoolean("created");
                    if (created) {
                        Toast.makeText(FinalPage.this, "your order succesfully created", Toast.LENGTH_SHORT).show();
                        submit.setText("your order is places succesfully enter the button to go to home page");
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(FinalPage.this, Firstpage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(FinalPage.this, "Error" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name",nameP);
                params.put("number",numberP);
                params.put("phoneBrand",brandP);
                params.put("phoneModel",modelP);
                params.put("phoneColor",colorP);
                params.put("phoneDefects",defectsP);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void sendNetworkRequest(User user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://samarth-task-manager.herokuapp.com/rare")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UserClient client = retrofit.create(UserClient.class);
        Call<User> call =  client.createAccount(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FinalPage.this, "success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(FinalPage.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
