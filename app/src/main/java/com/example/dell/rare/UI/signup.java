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
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.balysv.materialripple.MaterialRippleLayout;
import com.example.dell.rare.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    private static final int RESOLVE_HINT = 1011;
    String mobNumber;

    AutoCompleteTextView number_layout;
    AutoCompleteTextView name_layout,password_layout;
    MaterialRippleLayout signup;
    TextView login;
    ProgressBar loading;

    String signupapi = "https://samarth-rare-app.herokuapp.com/users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Master.class));
        }

        getPhone();

        loading = findViewById(R.id.loading_signup);
        number_layout = findViewById(R.id.text_input_number);
        name_layout = findViewById(R.id.text_input_name);
        password_layout = findViewById(R.id.text_password);

        signup = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_button);



        number_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhone();
            }
        });

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
              if(validateName() && validateNumber() && validatePassword()){
                  name_layout.setError(null);
                  name_layout.setError(null);
                  password_layout.setError(null);
                  createAccount(name_layout.getText().toString(),number_layout.getText().toString(),password_layout.getText().toString());
              }else{
                  loading.setVisibility(View.GONE);
                  signup.setVisibility(View.VISIBLE);
                  Toast.makeText(signup.this, "please fill all the details correctly", Toast.LENGTH_SHORT).show();
              }
            }
        });
    }

    public void phone(View view) {
        getPhone();
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
                    number_layout.setText(newString);
                }else{
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
        String number = number_layout.getText().toString().trim();

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
            number_layout.setError(null);
            return true;
        }
    }

    private Boolean validateName(){
        String name = name_layout.getText().toString().trim();

        if(name.isEmpty()){
            name_layout.setError("Field can't be empty");
            return false;
        }else{
            name_layout.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String name = password_layout.getText().toString().trim();

        if(name.isEmpty()){
            password_layout.setError("Field can't be empty");
            return false;
        }else if(name.length() < 6) {
            password_layout.setError("password should be atleast of six words");
            return false;
        }
        else{
            password_layout.setError(null);
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
