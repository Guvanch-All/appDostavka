package com.example.ues_finalversion.CreateNewOrder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ues_finalversion.ChatActivites.User;
import com.example.ues_finalversion.MainActivity;
import com.example.ues_finalversion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class checkOrderActivity extends AppCompatActivity {

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
    private Spinner weightSpinner;
    private String userName1;
    private Button continueButton;


    private FirebaseDatabase database;
    private DatabaseReference userDatabaseReference;
    private ChildEventListener userChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);
        database = FirebaseDatabase.getInstance();//Получаем доступ к корневлй папке базе данных
        userDatabaseReference = database.getReference().child("Users");//Создаем узел в корневую папку куда записивать данные
        getInf();
        Intent intent12 = getIntent();
        if ((intent12 != null)) {
            userName1 = intent12.getStringExtra("userName");
            setTitle("Успешно");

        }

        userChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    userName1 = user.getName();
                    nameUser.setText("Имя: " + userName1);

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userDatabaseReference.addChildEventListener(userChildEventListener);

        getIntentMain();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(checkOrderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void getInf() {
        nameUser = findViewById(R.id.nameCheckUser);
        priceText = findViewById(R.id.priceCheckText);
        paymentText = findViewById(R.id.paymentCheckText);
        address1Text = findViewById(R.id.address1CheckText);
        time1Text = findViewById(R.id.time1CheckText);
        date1Text = findViewById(R.id.date1CheckText);
        comment1Text = findViewById(R.id.comment1CheckText);
        onFootText = findViewById(R.id.onFootCheckText);
        weightFootText = findViewById(R.id.weightFootCheckText);
        onAutoText = findViewById(R.id.onAutoCheckText);
        weightAutoText = findViewById(R.id.weightAutoCheckText);
        WhatsTakingText = findViewById(R.id.WhatsTakingCheckText);
        address2Text = findViewById(R.id.address2CheckText);
        time2Text = findViewById(R.id.time2CheckText);
        date2Text = findViewById(R.id.date2CheckText);
        comment2Text = findViewById(R.id.comment2CheckText);
        continueButton = findViewById(R.id.continueButton);
    }

    private void getIntentMain() {
        Intent intent = getIntent();
        if (intent != null) {
            address1Text.setText(intent.getStringExtra(constantOrder.ORDER_ADDRESS1));


            priceText.setText(intent.getStringExtra(constantOrder.ORDER_PRICE));
            time1Text.setText(intent.getStringExtra(constantOrder.ORDER_TIME));
            date1Text.setText(intent.getStringExtra(constantOrder.ORDER_DATE1));
            comment1Text.setText(intent.getStringExtra(constantOrder.ORDER_COMMENT1));

            WhatsTakingText.setText(intent.getStringExtra(constantOrder.ORDER_TAKING));

            onFootText.setText(intent.getStringExtra(constantOrder.ORDER_TRANSPORT));
            onAutoText.setText(intent.getStringExtra(constantOrder.ORDER_TRANSPORT));
            paymentText.setText(intent.getStringExtra(constantOrder.ORDER_PAYMENT));


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
            priceText.setText(" / Цена: " + intent.getStringExtra(constantOrder.ORDER_PRICE));
            comment2Text.setText(intent.getStringExtra(constantOrder.ORDER_COMMENT2));

        }


    }
}


