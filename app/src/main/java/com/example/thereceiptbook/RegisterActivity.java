package com.example.thereceiptbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.example.thereceiptbook.LoginSingleton.SharedPrefManager;
import com.example.thereceiptbook.VolleyClasses.MySingleton;
import com.example.thereceiptbook.VolleyClasses.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button signUp;
    private TextInputEditText fullName;
    private TextInputEditText companyName;
    private TextInputEditText phoneNumber;
    private TextInputEditText userPassword;
    private TextInputEditText confirmUserPassword;
    private ProgressDialog progressDialog;
    private String result = "Already Registered";
    //private final Class aClass = RegisterActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize the various fields
        fullName = findViewById(R.id.full_name);
        companyName = findViewById(R.id.company_name);
        phoneNumber = findViewById(R.id.phone_number);
        userPassword = findViewById(R.id.password);
        confirmUserPassword = findViewById(R.id.confirm_password);
        progressDialog = new ProgressDialog(this);
        signUp = findViewById(R.id.sign_up_button);


        //Create an onClick Listener for button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPassword.getText().toString().trim()
                        .equals(confirmUserPassword.getText().
                                toString().trim())){
                    createUser();
                }else{
                    Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void createUser() {

        progressDialog.setMessage("Registering User ...");
        progressDialog.show();

        //Save user input in string
        final String userFullName = fullName.getText().toString().trim();
        final String userCompanyName = companyName.getText().toString().trim();
        final String userPhoneNumber = phoneNumber.getText().toString().trim();
        final String userPasswordValue = userPassword.getText().toString().trim();
        final String userConfirmedPassword = confirmUserPassword.getText().toString().trim();

        final RequestQueue requestQueue = MySingleton.getInstance(getApplicationContext()).
                getRequestQueue();
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject object = new JSONObject(response);
                            if(!object.getBoolean("error")){
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        "Please Check Your Credentials",Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                requestQueue.stop();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("full_name", userFullName);
                params.put("company", userCompanyName);
                params.put("phone_number", userPhoneNumber);
                params.put("user_pass", userPasswordValue);
                params.put("confirm_password", userConfirmedPassword);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
