package com.example.ues_finalversion.ChatActivites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.ues_finalversion.CreateNewOrder.SignInActivity;
import com.example.ues_finalversion.MainActivity;
import com.example.ues_finalversion.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ListView messageListView;
    private FirstMessageAdapter adapter;
    private ProgressBar progressBar;
    private ImageButton sendMessageButton;
    private ImageButton sendImageButton;
    private EditText messageEditText;
    private String userName;
    private FirebaseAuth auth;
    private static final int RC_IMAGE_PICKER = 123;
    private String recipientUserId;
    private FirebaseDatabase database;
    private DatabaseReference messagesDatabaseReference;
    private ChildEventListener messagesChildEventListener;//слушатель в реальном времени
    private DatabaseReference UsersDatabaseReference;
    private ChildEventListener UsersChildEventListener;
    private FirebaseStorage storage;
    private StorageReference chatImagesStorageReferences;
    private String recipientUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();//Получаем доступ к корневлй папке базе данных
        messagesDatabaseReference = database.getReference().child("messages");//Создаем узел в корневую папку куда записивать данные
        UsersDatabaseReference = database.getReference().child("Users");
        chatImagesStorageReferences = storage.getReference().child("chat_images");

        Intent intent = getIntent();
        if ((intent != null)) {
            userName = intent.getStringExtra("userName");
            recipientUserId = intent.getStringExtra("recipientUserId");
            recipientUserName=intent.getStringExtra("recipientUserName");
        }



        setTitle(recipientUserName);
        messageListView = findViewById(R.id.messageListView);
        List<FirstMessage> firstMessages = new ArrayList<>();
        adapter = new FirstMessageAdapter(this, R.layout.activity_chat, firstMessages);

        //иницилизация из Activity_Chat
        progressBar = findViewById(R.id.progressBar);
        sendImageButton = findViewById(R.id.sendPhotoButton);
        sendMessageButton = findViewById(R.id.sendMessageButton);
        messageEditText = findViewById(R.id.messageEditText);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        messageListView.setAdapter(adapter);


        auth = FirebaseAuth.getInstance();


        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    sendMessageButton.setEnabled(true);
                } else {
                    sendMessageButton.setEnabled(false);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        messageEditText.setFilters(new InputFilter[]
                {
                        new InputFilter.LengthFilter(500)
                });

        //Кнопка отправки сообщения****************
        sendMessageButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                FirstMessage message = new FirstMessage();
                message.setText(messageEditText.getText().toString());
                message.setName(userName);
                message.setImageURL(null);
                message.setSender(auth.getCurrentUser().getUid());
                message.setRecipient(recipientUserId);

                messagesDatabaseReference.push().setValue(message);//записовает в сервер

                messageEditText.setText("");


            }
        });
        //Кнопка отправки сообщения****************

        //*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*//*/*/*/*/

        //Кнопка отправки Изображения****************
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Choose an image"), RC_IMAGE_PICKER);

            }
        });
        //Кнопка отправки Изображения****************

        messagesChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FirstMessage message = snapshot.getValue(FirstMessage.class);//Получаем значение

                if (message.getSender().equals(auth.getCurrentUser().getUid())
                        && message.getRecipient().equals(recipientUserId)) {
                           message.setMine(true);
                               adapter.add(message);

                } else if (message.getRecipient().equals(auth.getCurrentUser().getUid())
                        && message.getSender().equals(recipientUserId)) {
                    message.setMine(false);
                    adapter.add(message);
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
        messagesDatabaseReference.addChildEventListener(messagesChildEventListener);

        UsersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    userName = user.getName();
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
        UsersDatabaseReference.addChildEventListener(UsersChildEventListener);


    }

    //для загрузки изображение
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            final StorageReference imageReference = chatImagesStorageReferences
                    .child(selectedImageUri.getLastPathSegment());
            UploadTask uploadTask = imageReference.putFile(selectedImageUri);
            uploadTask = imageReference.putFile(selectedImageUri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        FirstMessage message = new FirstMessage();
                        message.setImageURL(downloadUri.toString());
                        message.setName(userName);
                        message.setSender(auth.getCurrentUser().getUid());
                        message.setRecipient(recipientUserId);
                        messagesDatabaseReference.push().setValue(message);
                    } else {
                        // Handle failures
                        // ...
                    }
                }

            });
        }
    }
}