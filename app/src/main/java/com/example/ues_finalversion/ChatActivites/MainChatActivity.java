package com.example.ues_finalversion.ChatActivites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ues_finalversion.AccountProfie.ProfileActivity;
import com.example.ues_finalversion.MainActivity;
import com.example.ues_finalversion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainChatActivity extends AppCompatActivity {
    private DatabaseReference usersDataBaseReference;
    private ChildEventListener usersChildEventListener;
    private FirebaseAuth mAuth;
    private ArrayList<User> userArrayList;
    private UserAdapter userAdapter;
    private String userName;
    private TextView text2;
    private boolean isSupport = false;
    private RecyclerView userRecyclerView;
    private RecyclerView.LayoutManager userLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        Intent intent = getIntent();
        setTitle("Чат");


        if ((intent != null)) {
            userName = intent.getStringExtra(userName);
        }


        userArrayList = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        buildRecyclerView();
        attachUserDataBaseReference();
    }

    private void attachUserDataBaseReference() {
        usersDataBaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        if (usersChildEventListener == null) {
            usersChildEventListener = new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    User user = snapshot.getValue(User.class);

                    if ("lq7Iw1kvmLhjAqVqPkr19OkG6hp1".equals(mAuth.getCurrentUser().getUid())) {

                        if (!user.getId().equals("lq7Iw1kvmLhjAqVqPkr19OkG6hp1")) {
                            user.setAvatarMockUpResource(R.drawable.user_image);
                            userArrayList.add(user);
                            userAdapter.notifyDataSetChanged();
                        }
                    } else if (user.getId().equals("lq7Iw1kvmLhjAqVqPkr19OkG6hp1")) {
                        user.setAvatarMockUpResource(R.drawable.user_image);
                        userArrayList.add(user);
                        userAdapter.notifyDataSetChanged();
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
            usersDataBaseReference.addChildEventListener(usersChildEventListener);
        }
    }

    private void buildRecyclerView() {
        userRecyclerView = findViewById(R.id.userListRecyclerView);
        userRecyclerView.setHasFixedSize(true);
        userLayoutManager = new LinearLayoutManager(this);
        userAdapter = new UserAdapter(userArrayList);
        userRecyclerView.setLayoutManager(userLayoutManager);
        userRecyclerView.setAdapter(userAdapter);
        userRecyclerView.addItemDecoration(new DividerItemDecoration(userRecyclerView.getContext(), DividerItemDecoration.VERTICAL));


        userAdapter.setOnUserClickListener(new UserAdapter.OnUserClickListener() {
            @Override
            public Void onUserClick(int positon) {
                goToChat(positon);
                return null;
            }
        });

    }


    private void goToChat(int position) {
        Intent intent = new Intent(MainChatActivity.this, ChatActivity.class);
        intent.putExtra("recipientUserId", userArrayList.get(position).getId());
        intent.putExtra("recipientUserName", userArrayList.get(position).getName());
        intent.putExtra("userName", userName);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goTOProfile:
                startActivity(new Intent(MainChatActivity.this, ProfileActivity.class));

                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}