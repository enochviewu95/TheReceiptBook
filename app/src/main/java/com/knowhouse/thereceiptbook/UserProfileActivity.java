package com.knowhouse.thereceiptbook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.knowhouse.thereceiptbook.LoginSingleton.SharedPrefManager;

import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView profileImageIcon;
    private TextInputEditText fullNameEditText;
    private TextInputEditText phoneNumberEditText;
    private TextInputEditText companyNameEditText;
    private Button editButton;

    public static final String USERID = "id";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String FULL_NAME = "fullName";
    public static final String COMPANY_NAME = "companyName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Set toolbar

        Toolbar toolbar = findViewById(R.id.toolbarNav);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        profileImageIcon = findViewById(R.id.profileImageSetup);


        //get a reference to the TextInputEditText view
        fullNameEditText = findViewById(R.id.full_name_edittext);
        phoneNumberEditText = findViewById(R.id.user_phone_number_edittext);
        companyNameEditText = findViewById(R.id.company_name_edittext);

        //get reference to the button view
        editButton = findViewById(R.id.edit_button);

        Intent intent = getIntent();
        String fullName = intent.getExtras().getString(FULL_NAME).trim();
        int phoneNumber = intent.getExtras().getInt(PHONE_NUMBER);
        String phoneNumberString = "0".concat(String.valueOf(phoneNumber));
        String companyName = intent.getExtras().getString(COMPANY_NAME).trim();


        //fullNameDisplay.setText(fullName);
        fullNameEditText.setText(fullName);
        phoneNumberEditText.setText(phoneNumberString);
        companyNameEditText.setText(companyName);

        editButton.setOnClickListener(this);
        profileImageIcon.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileImageSetup:
                selectImage();
                break;

            case R.id.save_button:
                break;

            case R.id.edit_button:
                fullNameEditText.setEnabled(true);
                phoneNumberEditText.setEnabled(true);
                companyNameEditText.setEnabled(true);
                break;

            case R.id.full_name_edittext:
                break;
        }
    }

    //Select image for the user profile image
    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.
                        getBitmap(getContentResolver(),path);
                profileImageIcon.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
