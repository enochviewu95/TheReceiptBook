package com.example.thereceiptbook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.thereceiptbook.FragmentActivities.HomeFragment;
import com.example.thereceiptbook.FragmentActivities.RecordsFragment;
import com.example.thereceiptbook.FragmentActivities.SalesFragment;
import com.example.thereceiptbook.FragmentActivities.TransactionsFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Set the application toolbar for the registration activity
        Toolbar toolbar = findViewById(R.id.toolbarNav);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.nav_open_drawer,R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Register the activity with the navigation view as a listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Fragment support
        Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame,fragment);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        Intent intent = null;

        switch (id){
            case R.id.transactions:
                fragment = new TransactionsFragment();
                break;
            case R.id.sales:
                fragment = new SalesFragment();
                break;
            case R.id.records:
                fragment = new RecordsFragment();
                break;
            case R.id.nav_help:
                intent = new Intent(this,HelpActivity.class);
                break;
            case R.id.nav_feedback:
                intent = new Intent(this,FeedbackActivity.class);
                break;
                default:
                    fragment = new HomeFragment();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    //Introducing the menu bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
}