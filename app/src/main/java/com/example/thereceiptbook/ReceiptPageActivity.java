package com.example.thereceiptbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thereceiptbook.LoginSingleton.SharedPrefManager;
import com.example.thereceiptbook.VolleyClasses.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReceiptPageActivity extends AppCompatActivity {


    //Declaration of widgets
    private TextInputEditText customerName;
    private TextInputEditText customerPhoneNumber;
    private TextInputEditText itemPurchased;
    private TextInputEditText amountPaid;
    private TextInputEditText issuersNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_page);

        //Set the application toolbar for the registration activity
        Toolbar toolbar = findViewById(R.id.toolbarNav);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Definition of widgets
        customerName = findViewById(R.id.customer_full_name);
        customerPhoneNumber = findViewById(R.id.customer_phone_number);
        itemPurchased = findViewById(R.id.item_purchased);
        amountPaid = findViewById(R.id.amount_paid);
        issuersNumber = findViewById(R.id.issuers_number);

        //Progress Dialog definition
        progressDialog = new ProgressDialog(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logging_out:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return true;
    }

    private void issueTheReceipt(){
        progressDialog.setMessage("Sending receipt...");
        progressDialog.show();

        final String customer_name = customerName.getText().toString().trim();
        final String item_purchased = itemPurchased.getText().toString().trim();
        final String customer_phone_number = customerPhoneNumber.getText().toString().trim();
        final String amount_paid = amountPaid.getText().toString().trim();
        final String issuers_number = issuersNumber.getText().toString().trim();

        final RequestQueue requestQueue = MySingleton.getInstance(getApplicationContext()).
                getRequestQueue();
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject object = new JSONObject(response);
                            if(!object.getBoolean("error")){
                                Intent intent = new Intent(ReceiptPageActivity.this,
                                        HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }catch (JSONException e){
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
                params.put("customer_full_name",customer_name);
                params.put("customers_phone_number",customer_phone_number);
                params.put("purchased_item",item_purchased);
                params.put("amount_paid",amount_paid);
                params.put("receipt_issued_by",issuers_number);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


}
