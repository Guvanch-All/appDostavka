package com.example.ues_finalversion.AccountProfie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ues_finalversion.ChatActivites.ChatActivity;
import com.example.ues_finalversion.ChatActivites.FirstMessage;
import com.example.ues_finalversion.ChatActivites.MainChatActivity;
import com.example.ues_finalversion.ChatActivites.User;
import com.example.ues_finalversion.CreateNewOrder.NewOrderActivity;
import com.example.ues_finalversion.CreateNewOrder.SignInActivity;
import com.example.ues_finalversion.MainActivity;
import com.example.ues_finalversion.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private String userName1;
    private String userEmail1;
    private TextView userName;
    private TextView userEmail;
    private DatabaseReference userDatabaseReference;
    private ChildEventListener userChildEventListener;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //ЭТО ИНИЦИЛИЗАЦИИ КНОПКИ НАВИШАЦИИ!!!!
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //ЭТО ИНИЦИЛИЗАЦИИ КНОПКИ НАВИШАЦИИ!!!!
        setTitle("Профиль");


        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userEmail);

        database = FirebaseDatabase.getInstance();//Получаем доступ к корневлй папке базе данных
        userDatabaseReference = database.getReference().child("Users");//Создаем узел в корневую папку куда записивать данные
        userChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);

               if (user.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    userName1 = user.getName();
                    userEmail1 = user.getEmail();

                    userName.setText(userName1);
                    userEmail.setText(userEmail1);
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


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Order:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.NewOrder:
                intent = new Intent(this, NewOrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.chat:
                intent = new Intent(this, MainChatActivity.class);
                startActivity(intent);
                return true;
            case R.id.Profile:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);

                return true;
        }
        return false;
    }//ЭТО КНОПКИ НАВИШАЦИИ!!!!

    public void Exit(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this, SignInActivity.class));finish();

    }
}