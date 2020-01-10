package com.knowhouse.thereceiptbook;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.view.View;
import android.widget.TextView;

import com.knowhouse.thereceiptbook.FragmentActivities.HomeFragment;
import com.knowhouse.thereceiptbook.FragmentActivities.RecordsFragment;
import com.knowhouse.thereceiptbook.FragmentActivities.SalesFragment;
import com.knowhouse.thereceiptbook.FragmentActivities.TransactionsFragment;
import com.knowhouse.thereceiptbook.LoginSingleton.SharedPrefManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView navFullName;
    private TextView navPhoneNumber;
    private CircleImageView navProfilePic;

    public static final String USERID = "id";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String FULL_NAME = "fullName";
    public static final String COMPANY_NAME = "companyName";
    public static final String IMAGE_URL = "image";

    private int userid;
    private int phoneNumber;
    private String  fullName;
    private String companyName;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

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

        View headerView = navigationView.getHeaderView(0);

        //Get information from intent
        Intent intent = getIntent();
        userid = intent.getExtras().getInt(USERID);
        phoneNumber = intent.getExtras().getInt(PHONE_NUMBER);
        fullName = intent.getExtras().getString(FULL_NAME).trim();
        String phoneNumberString = "0".concat(String.valueOf(phoneNumber));
       companyName = intent.getExtras().getString(COMPANY_NAME).trim();
       imageUrl = intent.getExtras().getString(IMAGE_URL);


        //Initialization of navigation widgets for drawer layout
        navFullName = headerView.findViewById(R.id.nav_full_name);
        navPhoneNumber = headerView.findViewById(R.id.nav_phone_number);
        navProfilePic = headerView.findViewById(R.id.nav_profile_pic);

        //Get user name and phone number from shared preferences
        navPhoneNumber.setText(phoneNumberString);
        navFullName.setText(fullName);
        Bitmap image = ImageConverter.convertFromStringToImg(imageUrl);
        navProfilePic.setImageBitmap(image);

        //Fragment support
        Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,fragment);
        ft.commit();
    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Intent intent = null;

        switch (id){
            case R.id.transactions:
                fragment = new TransactionsFragment();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();

                break;
            case R.id.sales:
                fragment = new SalesFragment();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();
                break;
            case R.id.records:
                fragment = new RecordsFragment();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();
                break;
            case R.id.nav_help:
                intent = new Intent(this,HelpActivity.class);
                break;
            case R.id.nav_feedback:
                intent = new Intent(this,FeedbackActivity.class);
                break;
                default:
                    fragment = new HomeFragment();
                    ft.replace(R.id.content_frame,fragment);
                    ft.commit();
                    break;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logging_out:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.user_profile_image:
                Intent intent = new Intent(this,UserProfileActivity.class);
                intent.putExtra(UserProfileActivity.USERID,userid);
                intent.putExtra(UserProfileActivity.PHONE_NUMBER,phoneNumber);
                intent.putExtra(UserProfileActivity.FULL_NAME,fullName);
                intent.putExtra(UserProfileActivity.COMPANY_NAME,companyName);
                intent.putExtra(UserProfileActivity.IMAGE_URL,imageUrl);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void issueReceipt(View v){
        Intent intent = new Intent(this,ReceiptPageActivity.class);
        startActivity(intent);
    }
}
