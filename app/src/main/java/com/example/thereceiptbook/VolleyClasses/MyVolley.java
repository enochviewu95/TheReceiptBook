package com.example.thereceiptbook.VolleyClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thereceiptbook.RegisterActivity;
import com.example.thereceiptbook.SplashScreen;

public class MyVolley {
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private Context context;

    public MyVolley(Context context){
        this.context = context;
    }

    public void volleyRequest(String server_url,final Class classValue){
        requestQueue = MySingleton.getInstance(context.getApplicationContext()).
                getRequestQueue();
        requestQueue.start();
        stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,classValue);
                        context.startActivity(intent);
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
