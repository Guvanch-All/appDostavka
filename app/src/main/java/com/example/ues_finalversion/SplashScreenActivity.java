package com.example.ues_finalversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ues_finalversion.CreateNewOrder.NewOrderActivity;
import com.example.ues_finalversion.CreateNewOrder.SignInActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       Intent intent = new Intent(this, SignInActivity.class);
       startActivity(intent);finish();
    }
}