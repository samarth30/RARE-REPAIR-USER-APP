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

public class Login extends AppCompatActivity {

    TextInputLayout number_layout;
    TextInputLayout name_layout,password_layout;
    Button login,signup;
    RequestQueue requestQueue;
    public static String TokenFinal;
    String loginapi = "https://samarth-rare-app.herokuapp.com/users/login";

    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(Login.this, Master.class));
        } else {

            number_layout = findViewById(R.id.text_input_number);
            loading = findViewById(R.id.loading_login);
            signup = findViewById(R.id.signup_button);
            login = findViewById(R.id.login_button);
            password_layout = findViewById(R.id.text_password);

            requestQueue = Volley.newRequestQueue(this);

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this, signup.class));
                    finish();
                }
            });
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loading.setVisibility(View.VISIBLE);
                    login.setVisibility(View.GONE);
                    if (validateNumber() && validatePassword()) {
                        number_layout.setErrorEnabled(false);
                        password_layout.setErrorEnabled(false);
                        Loginuser(number_layout.getEditText().getText().toString(), password_layout.getEditText().getText().toString());
                    } else {
                        loading.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "please fill all the details correctly", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void Loginuser(final String number, final String password) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginapi, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        Boolean Logged = jsonObject.getBoolean("login");


                        if (Logged) {
                            String token = jsonObject.getString("token");
                            TokenFinal = token;
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(token,number);

                            loading.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            startActivity(new Intent(Login.this, Master.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();

                        } else {
                            loading.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        loading.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    login.setVisibility(View.VISIBLE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
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
            return true;
        }
    }

    private Boolean validateName(){
        String name = name_layout.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            name_layout.setError("Field can't be empty");
            return false;
        }else{
            return true;
        }
    }

    private Boolean validatePassword(){
        String name = password_layout.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            password_layout.setError("Field can't be empty");
            return false;
        }else{
            return true;
        }
    }
}
