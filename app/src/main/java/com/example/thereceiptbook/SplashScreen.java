package com.example.thereceiptbook;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.thereceiptbook.VolleyClasses.MySingleton;
import com.example.thereceiptbook.VolleyClasses.MyVolley;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreen extends AppCompatActivity {

    private CircleImageView logo;
    private RelativeLayout splashLayout;
    private String server_url = "http://192.168.137.1/thereceiptbook/Main.php";
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Class aClass = RegisterActivity.class;

        logo = findViewById(R.id.know_house_logo);
        splashLayout = findViewById(R.id.splash_layout);
        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        splashLayout.startAnimation(myAnim);
        logo.startAnimation(myAnim);

        /*final RequestQueue requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        requestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast toast = Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(SplashScreen.this, RegisterActivity.class);
                        startActivity(intent);
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG);
                toast.show();
                requestQueue.stop();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);*/

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                MyVolley myVolley = new MyVolley(SplashScreen.this);
                myVolley.volleyRequest(server_url,RegisterActivity.class);
            }
        },3000);

    }
}
