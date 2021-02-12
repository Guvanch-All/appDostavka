package com.example.ues_finalversion.CreateNewOrder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ues_finalversion.ChatActivites.User;
import com.example.ues_finalversion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {
    private TextView nameUser;
    private TextView priceText;
    private TextView paymentText;
    private TextView address1Text;
    private TextView time1Text;
    private TextView date1Text;
    private TextView comment1Text;
    private TextView onFootText;
    private TextView weightFootText;
    private TextView onAutoText;
    private TextView weightAutoText;
    private TextView WhatsTakingText;

    private TextView address2Text;
    private TextView time2Text;
    private TextView date2Text;
    private TextView comment2Text;
    private String userName1;

    private FirebaseDatabase database;
    private DatabaseReference userDatabaseReference;
    private ChildEventListener userChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        getIntentMain();





    }

    private void init() {
        nameUser = findViewById(R.id.nameUser);
        priceText = findViewById(R.id.priceText);
        paymentText = findViewById(R.id.paymentText);
        address1Text = findViewById(R.id.address1Text);
        time1Text = findViewById(R.id.time1Text);
        date1Text = findViewById(R.id.date1Text);
        comment1Text = findViewById(R.id.comment1Text);
        onFootText = findViewById(R.id.onFootText);
        weightFootText = findViewById(R.id.weightFootText);
        onAutoText = findViewById(R.id.onAutoText);
        weightAutoText = findViewById(R.id.weightAutoText);
        WhatsTakingText = findViewById(R.id.WhatsTakingText);
        address2Text = findViewById(R.id.address2Text);
        time2Text = findViewById(R.id.time2Text);
        date2Text = findViewById(R.id.date2Text);
        comment2Text = findViewById(R.id.comment2Text);
        database = FirebaseDatabase.getInstance();//Получаем доступ к корневлй папке базе данных
        userDatabaseReference = database.getReference().child("Users");//Создаем узел в корневую папку куда записивать данные
        Intent intent12 = getIntent();
        if ((intent12 != null)) {
            userName1 = intent12.getStringExtra("userName");
        }
    }

    private void getIntentMain() {
        Intent intent = getIntent();
        if (intent != null) {

            nameUser.setText(intent.getStringExtra("Имя: " + constantOrder.USER_NAME));

            priceText.setText(intent.getStringExtra(constantOrder.ORDER_PRICE));
            address1Text.setText(intent.getStringExtra(constantOrder.ORDER_ADDRESS1));
            time1Text.setText(intent.getStringExtra(constantOrder.ORDER_TIME));
            date1Text.setText(intent.getStringExtra(constantOrder.ORDER_DATE1));
            comment1Text.setText(intent.getStringExtra(constantOrder.ORDER_COMMENT1));
            onAutoText.setText(intent.getStringExtra(constantOrder.ORDER_TRANSPORT));
            onFootText.setText(intent.getStringExtra(constantOrder.ORDER_TRANSPORT));
            if (onFootText.getText().equals("Пешком")) {
                onFootText.setText(intent.getStringExtra(constantOrder.ORDER_TRANSPORT));
                weightFootText.setText(intent.getStringExtra(constantOrder.ORDER_SPINNER));
                onFootText.setVisibility(View.VISIBLE);
                weightFootText.setVisibility(View.VISIBLE);
                onAutoText.setVisibility(View.GONE);
                weightAutoText.setVisibility(View.GONE);
            }
            if (onAutoText.getText().equals("Авто")) {
                onAutoText.setText(intent.getStringExtra(constantOrder.ORDER_TRANSPORT));
                weightAutoText.setText(intent.getStringExtra(constantOrder.ORDER_SPINNER));
                onAutoText.setVisibility(View.VISIBLE);
                weightAutoText.setVisibility(View.VISIBLE);
                onFootText.setVisibility(View.GONE);
                weightFootText.setVisibility(View.GONE);
            }

            address2Text.setText(intent.getStringExtra(constantOrder.ORDER_ADDRESS2));
            time2Text.setText(intent.getStringExtra(constantOrder.ORDER_TIME2));
            date2Text.setText(intent.getStringExtra(constantOrder.ORDER_DATE2));
            priceText.setText(intent.getStringExtra(constantOrder.ORDER_PRICE));
            comment2Text.setText(intent.getStringExtra(constantOrder.ORDER_COMMENT2));


        }

    }

}