package com.example.dell.rare.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class Login extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private static final int RESOLVE_HINT = 1011;
    String mobNumber;

    TextInputLayout number_layout;
    TextInputLayout name_layout,password_layout;
    FloatingActionButton login;
    TextView signup;
    String NameFinal;
    AutoCompleteTextView number_auto,password_auto;
    public static String TokenFinal;
    String loginapi = "https://samarth-rare-app.herokuapp.com/users/login";
    String usermeapi = "https://samarth-rare-app.herokuapp.com/users/me";

    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Master.class));
        }

        getPhone();
            password_auto = findViewById(R.id.password_auto);
            number_auto = findViewById(R.id.number_auto);
            number_layout = findViewById(R.id.text_input_number);
            loading = findViewById(R.id.loading_login);
            signup = findViewById(R.id.signup_button);
            login = findViewById(R.id.login_button);
            password_layout = findViewById(R.id.text_password);

            number_auto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPhone();
                }
            });

            number_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPhone();
                }
            });

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
                    login.setAlpha(0f);
                    if (validateNumber() && validatePassword()) {
                        number_auto.setError(null);
                        password_auto.setError(null);
                        Loginuser(number_layout.getEditText().getText().toString(), password_layout.getEditText().getText().toString());
                    } else {
                        loading.setVisibility(View.GONE);
                        login.setAlpha(1f);
                        Toast.makeText(Login.this, "please fill all the details correctly", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    private void getPhone() {
       GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
               .addApi(Auth.CREDENTIALS_API)
               .addConnectionCallbacks(this)
               .addOnConnectionFailedListener(this)
               .build();
       googleApiClient.connect();
        HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(new CredentialPickerConfig.Builder()
                        .setShowCancelButton(true)
                        .build())
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent =
                Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0,new Bundle());
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
                if(cred != null){
                    mobNumber = cred.getId();
                    String newString = mobNumber.replace("+91","");
                    number_auto.setText(newString);
                }else{
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
            }
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
                            GetUserName(token);
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(token,number);
                            loading.setVisibility(View.GONE);
                            login.setAlpha(1f);
                            startActivity(new Intent(Login.this, Master.class));
                            finish();

                        } else {
                            loading.setVisibility(View.GONE);
                            login.setAlpha(1f);
                            Toast.makeText(Login.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        loading.setVisibility(View.GONE);
                        login.setAlpha(1f);
                        Toast.makeText(Login.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    login.setAlpha(1f);
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }

    private void GetUserName(final String authtoken) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, usermeapi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String name = jsonObject.getString("name");

                    if (!name.isEmpty()) {
                      NameFinal = name;
                        SharedPrefManager.getInstance(getApplicationContext()).username(name);
                    }


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
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+ authtoken);
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
            number_auto.setError("Field can't be empty");
            return false;
        }else if(number.length() < 10){
            number_auto.setError("Phone number not valid");
            return false;
        }else if(number.length() >=11){
            number_auto.setError("Phone number not valid");
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
            password_auto.setError("Field can't be empty");
            return false;
        }
        else if(name.length() < 6){
            password_auto.setError("password should be atleast 6 words");
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);
    }
}
