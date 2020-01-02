package com.example.dell.rare.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dell.rare.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    TextInputLayout number_layout;
    TextInputLayout name_layout,password_layout;
    Button signup,login;
    ProgressBar loading;
    String signupapi = "https://samarth-rare-app.herokuapp.com/users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loading = findViewById(R.id.loading_signup);
        number_layout = findViewById(R.id.text_input_number);
        name_layout = findViewById(R.id.text_input_name);
        password_layout = findViewById(R.id.text_password);

        signup = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this, Login.class));
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                signup.setVisibility(View.GONE);
              if(validateNumber() && validatePassword()){
                  name_layout.setErrorEnabled(false);
                  password_layout.setErrorEnabled(false);
                  createAccount(name_layout.getEditText().getText().toString(),number_layout.getEditText().getText().toString(),password_layout.getEditText().getText().toString());
              }else{
                  loading.setVisibility(View.GONE);
                  signup.setVisibility(View.VISIBLE);
                  Toast.makeText(signup.this, "please fill all the details correctly", Toast.LENGTH_SHORT).show();
              }
            }
        });
    }

    private void createAccount(final String name,final String number, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, signupapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean UserCreated = jsonObject.getBoolean("created");


                    if (UserCreated) {
                        loading.setVisibility(View.GONE);
                        signup.setVisibility(View.VISIBLE);
                        Toast.makeText(signup.this, "Successfully Created your account", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(signup.this, Login.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(signup.this, "this account allready exists", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    loading.setVisibility(View.GONE);
                    signup.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                    Toast.makeText(signup.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                    loading.setVisibility(View.GONE);
//                    btnjoin_signup.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                signup.setVisibility(View.VISIBLE);
                Toast.makeText(signup.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
//                loading.setVisibility(View.GONE);
//                btnjoin_signup.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("number", number);
                params.put("password", password);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private Boolean validateNumber(){
        String number = number_layout.getEditText().getText().toString().trim();

        if(number.isEmpty()){
            number_layout.setError("Field can't be empty");
            return false;
        }else if(number.length() < 10){
            number_layout.setError("Phone number not valid");
            return false;
        }else if(number.length() >=11){
            number_layout.setError("Phone number not valid");
            return false;
        }
        else{
            number_layout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateName(){
        String name = name_layout.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            name_layout.setError("Field can't be empty");
            return false;
        }else{
            number_layout.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String name = password_layout.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            password_layout.setError("Field can't be empty");
            return false;
        }else{
            number_layout.setErrorEnabled(false);
            return true;
        }
    }
}
