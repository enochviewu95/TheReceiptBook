package com.example.thereceiptbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.thereceiptbook.FragmentActivities.HomeFragment;
import com.example.thereceiptbook.LoginSingleton.SharedPrefManager;
import com.example.thereceiptbook.VolleyClasses.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private TextInputEditText phoneNumber;
    private TextInputEditText password;
    private Button loginButton;
    private ProgressDialog progressDialog;
    //private TextView launchRegistrationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,HomeActivity.class));
        }

        //Initializing the widgets
        phoneNumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

    }

    private void userLogin(){

        progressDialog.setMessage("Logging In User ...");
        progressDialog.show();

        final String userPhoneNumber = phoneNumber.getText().toString().trim();
        final String userPassword = password.getText().toString().trim();

        final RequestQueue requestQueue = MySingleton.getInstance(getApplicationContext()).
                getRequestQueue();
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getInt("phone_number"),
                                                obj.getString("full_name")
                                        );
                                Toast.makeText(getApplicationContext(),
                                        "Login Successful",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);

                                int id = SharedPrefManager.getInstance(getApplicationContext()).getUserID();
                                int phoneNumber = SharedPrefManager.getInstance(getApplicationContext()).getUserPhoneNumber();
                                String fullName = SharedPrefManager.getInstance(getApplicationContext()).getUserFullName();

                                intent.putExtra(HomeFragment.USERID,id);
                                intent.putExtra(HomeFragment.PHONE_NUMBER,phoneNumber);
                                intent.putExtra(HomeFragment.FULL_NAME,fullName);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj .getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }catch (JSONException e){
                            Log.i("Error","Login Unsuccessful");
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        error.getMessage(),
                        Toast.LENGTH_LONG).show();;
                requestQueue.stop();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone_number", userPhoneNumber);
                params.put("confirm_password", userPassword);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void launchRegistration(View v){
        Intent intent = new Intent(this,RegisterActivity.class );
        startActivity(intent);
        finish();
    }
}
