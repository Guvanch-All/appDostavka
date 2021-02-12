package com.example.ues_finalversion.CreateNewOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ues_finalversion.ChatActivites.User;
import com.example.ues_finalversion.MainActivity;
import com.example.ues_finalversion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "SignInActivity2";
    private EditText nameEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText emailEditText;
    private TextView toggleLoginSignUpTextView;
    private Button loginSignUpButton;
    private boolean loginModeActive;

    private FirebaseDatabase database;
    private DatabaseReference UsersDatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();//Получаем доступ к корневлй папке базе данных
        UsersDatabaseReference = database.getReference().child("Users");//Создаем узел в корневую папку куда записивать данные

        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        toggleLoginSignUpTextView = findViewById(R.id.toggleLoginSignUpTextView);
        loginSignUpButton = findViewById(R.id.LoginSignUp);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));finish();
        }


        loginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSignUpUser(emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim());

            }
        });

    }

    private void loginSignUpUser(String email, String password) {
        if (loginModeActive) {
            if (passwordEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(this, "Пароль должен быть не менее 7 симболов", Toast.LENGTH_LONG).show();
            } else if (emailEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(this, "Пожалуйста введите вашу почту", Toast.LENGTH_LONG).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //   updateUI(user);
                                    Intent intent = new Intent(new Intent(SignInActivity.this,
                                            MainActivity.class));
                                    intent.putExtra("userName", nameEditText.getText().toString().trim());
                       //            intent.putExtra(constantOrder.USER_NAME,nameEditText.getText().toString().trim());
                                    startActivity(intent);finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "Ошибка Аутентификации.",
                                            Toast.LENGTH_SHORT).show();
                                    //    updateUI(null);
                                    // ...
                                }

                                // ...
                            }
                        });
            }
        } else {
            if (!passwordEditText.getText().toString().trim().equals(
                    repeatPasswordEditText.getText().toString().trim()
            )) {
                Toast.makeText(this, "Пворли не совпадают", Toast.LENGTH_LONG).show();
            } else if (passwordEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(this, "Пворл должен быть не менее 7 симболов", Toast.LENGTH_LONG).show();
            } else if (emailEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(this, "Пожалуйста введите вашу почту", Toast.LENGTH_LONG).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    createUser(user);
                                    //   updateUI(user);


                                    Intent intent = new Intent(new Intent(SignInActivity.this, MainActivity.class));
                                    intent.putExtra("userName", nameEditText.getText().toString().trim());
                            //        intent.putExtra(constantOrder.USER_NAME,nameEditText.getText().toString() );
                                    startActivity(intent);finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignInActivity.this, "email адресс уже существует!",
                                            Toast.LENGTH_SHORT).show();
                                    //    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        }
    }

    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();

        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(nameEditText.getText().toString().trim());//брать Имя из эдитекта пригодиттся тоже для создание заявки
        UsersDatabaseReference.push().setValue(user);


    }

    public void toggleLoginMode(View view) {
        if (loginModeActive) {
            loginModeActive = false;
            loginSignUpButton.setText("Зарегестрироваться");
            toggleLoginSignUpTextView.setText("У меня есть аккаунт!");
            repeatPasswordEditText.setVisibility(View.VISIBLE);
            nameEditText.setVisibility(View.VISIBLE);


        } else {
            loginModeActive = true;
            loginSignUpButton.setText("Войти в аккаунт");
            toggleLoginSignUpTextView.setText("Зарегестрироваться");
            repeatPasswordEditText.setVisibility(View.GONE);
            nameEditText.setVisibility(View.GONE);


        }


    }

}
