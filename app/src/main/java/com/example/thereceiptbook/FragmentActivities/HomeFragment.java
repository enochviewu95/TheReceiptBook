package com.example.thereceiptbook.FragmentActivities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thereceiptbook.Adapters.ActivityFeedAdapter;
import com.example.thereceiptbook.DummyClass;
import com.example.thereceiptbook.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView activityFeedRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_home, container, false);

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
        }

        ActivityFeedAdapter adapter = new ActivityFeedAdapter(
                fullNames,userImage,customerImage,applicationText
        );
        activityFeedRecycler.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        activityFeedRecycler.setLayoutManager(linearLayoutManager);
        return activityFeedRecycler;
    }

}
