package com.example.thereceiptbook.FragmentActivities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.thereceiptbook.Adapters.ActivityFeedAdapter;
import com.example.thereceiptbook.Constants;
import com.example.thereceiptbook.DummyClass;
import com.example.thereceiptbook.R;
import com.example.thereceiptbook.TransactionsClass;
import com.example.thereceiptbook.VolleyClasses.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    //public TransactionsClass transactionsClass;
    //public ActivityFeedAdapter adapter;
    public static final String USERID = "id";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String FULL_NAME = "fullName";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView activityFeedRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_home, container, false);

/*
        int max = DummyClass.dummy_users.length;

        String[] fullNames = new String[max];
        int[] applicationText = new int[max];
        int[] userImage = new int[max];
        int[] customerImage = new int[max];

        for (int i = 0; i <max ; i++) {
            fullNames[i] = DummyClass.dummy_users[i].getName();
            applicationText[i] = DummyClass.dummy_users[i].getDummy_text();
            userImage[i] = DummyClass.dummy_users[i].getProfileImageId();
            customerImage[i] = DummyClass.dummy_users[i].getCustomerImageId();
        }*/

        loadTransactions(activityFeedRecycler);
        return activityFeedRecycler;
    }

    private void loadTransactions(final RecyclerView activityFeedRecycler){


        Intent intent = getActivity().getIntent();
        final int userid = intent.getExtras().getInt(USERID);
        final int phoneNumber = intent.getExtras().getInt(PHONE_NUMBER);
        final String  fullName = intent.getExtras().getString(FULL_NAME);
        final RequestQueue requestQueue = MySingleton.getInstance(getContext()).getRequestQueue();
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_HOMEFRAGMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray obj = new JSONArray(response);        //JSon Array to get array from php
                            int max = obj.length();                         //the maximum length of the array
                            int[] id = new int[max];                        //Getting the id of the from the database
                            //String[] fullNames = new String[max];           //Getting the full name of the issuer of receipt
                            //String[] phoneNumber = new String[max];         //Phone number of issuer
                            String[] date = new String[max];            //Time the receipt was issued
                            String[] applicationText = new String[max];     //Receipt text
                            JSONObject transObject;     //Declaration of JSonObject

                            /*For loop the get the details from the database*/
                            for (int i = 0; i <obj.length() ; i++) {
                                transObject = obj.getJSONObject(i);
                                id[i] = transObject.getInt("id");
                                //fullNames[i] = transObject.getString("full_name");
                                applicationText[i] = transObject.getString("transactions");
                                date[i] = transObject.getString("date");
                            }

                            //Transaction class to handle the information from the database

                            TransactionsClass transactionsClass = new TransactionsClass(id,fullName,String.valueOf(phoneNumber),
                                    applicationText,date);
                            ActivityFeedAdapter adapter = new ActivityFeedAdapter(transactionsClass);
                            activityFeedRecycler.setAdapter(adapter);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            activityFeedRecycler.setLayoutManager(linearLayoutManager);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                requestQueue.stop();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("users_credential_id", String.valueOf(userid));
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
