package com.example.thereceiptbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreen extends AppCompatActivity {

    private CircleImageView logo;
    private RelativeLayout splashLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.know_house_logo);
        splashLayout = findViewById(R.id.splash_layout);
        Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        splashLayout.startAnimation(myAnim);
        logo.startAnimation(myAnim);


        int a=0;
        if(a != 0) {
            Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
