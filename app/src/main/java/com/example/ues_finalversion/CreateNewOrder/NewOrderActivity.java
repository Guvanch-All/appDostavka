package com.example.ues_finalversion.CreateNewOrder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ues_finalversion.ChatActivites.FirstMessage;
import com.example.ues_finalversion.ChatActivites.User;
import com.example.ues_finalversion.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NewOrderActivity extends AppCompatActivity {
    private RadioButton onFoot;
    private RadioButton onAuto;

    private Spinner spinner1, spinner2;
    private EditText adressFromWhere;
    private EditText timeFromWhere;
    private EditText dateFromWhere;
    private EditText phoneFromWhere;
    private EditText commentFromWhere;
    //*****
    private EditText adressInWhere;
    private EditText timeInWhere;
    private EditText dateInWhere;
    private EditText phoneInWhere;
    private EditText commentInWhere;
    private RadioButton documentRadiobutton;
    private RadioButton flowerRadiobutton;
    private RadioButton giftRadiobutton;
    private RadioButton hotFoodRadiobutton;
    private RadioButton payWithCard;
    private RadioButton payWithCash;
    private TextView priceTextView;
    private Button nextButton;
    private TextView nameUserOrder;
    private String userName;
    private RadioGroup radioGroup;
    private FirebaseDatabase database;
    private DatabaseReference orderSenderReference;
    private DatabaseReference userDatabaseReference;
    private ChildEventListener userChildEventListener;
    private FirebaseAuth mAuth;
    private Dialog dialogOrder;
    private  String hello;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        setTitle("Создать заякву");
        //Connect Firebase to the project

        //ChatBranch



        database = FirebaseDatabase.getInstance();//Получаем доступ к корневлй папке базе данных
        orderSenderReference = database.getReference().child("Order");//Создаем узел в корневую папку куда записивать данные
        userDatabaseReference = database.getReference().child("Users");//Создаем узел в корневую папку куда записивать данные

        onAuto = findViewById(R.id.radio_auto);
        onFoot = findViewById(R.id.radio_Walk);
        //**Спиннер
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        //***
        adressFromWhere = findViewById(R.id.adressFromWhere);
        timeFromWhere = findViewById(R.id.timeFromWhere);
        dateFromWhere = findViewById(R.id.dateFromWhere);
        phoneFromWhere = findViewById(R.id.phoneFromWhere);
        commentFromWhere = findViewById(R.id.commentFromWhere);
        //**************************************//
        adressInWhere = findViewById(R.id.adressInWhere);
        timeInWhere = findViewById(R.id.timeInWhere);
        dateInWhere = findViewById(R.id.dateInWhere);
        phoneInWhere = findViewById(R.id.phoneInWhere);
        phoneInWhere = findViewById(R.id.phoneInWhere);
        nameUserOrder=findViewById(R.id.userNameOrder);
        commentInWhere = findViewById(R.id.commentInWhere);
        priceTextView = findViewById(R.id.priceTextView);
        //******Радио батоны
        documentRadiobutton = findViewById(R.id.documentRadiobutton);
        flowerRadiobutton = findViewById(R.id.flowerRadiobutton);
        hotFoodRadiobutton = findViewById(R.id.hotFoodRadiobutton);
        giftRadiobutton = findViewById(R.id.giftRadiobutton);
        payWithCash = findViewById(R.id.payWithCash);
        payWithCard = findViewById(R.id.payWithCard);
        nextButton = findViewById(R.id.nextbutton009);
        radioGroup=findViewById(R.id.radioGroup2);




        Intent intent = getIntent();
        if ((intent != null)) {
            userName = intent.getStringExtra("userName");
        }
        dialogOrder=new Dialog(this);
        dialogOrder.setContentView(R.layout.preview_dialog);
        dialogOrder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//прозрачность
        dialogOrder.setCancelable(false);




        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Для закрытие диологовое окно
                TextView closeDialog = (TextView) dialogOrder.findViewById(R.id.textDialog);
                TextView buttonSuccess = (TextView) dialogOrder.findViewById(R.id.buttonSuccess);

                dialogOrder.show();
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         dialogOrder.dismiss();

                    }
                });

                //кнопка перхода на другой активити
                buttonSuccess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent2=new Intent(NewOrderActivity.this,checkOrderActivity.class);
                        if (onAuto.isChecked()){
                            intent2.putExtra(constantOrder.ORDER_TRANSPORT,onAuto.getText().toString() );

                        }else if (onFoot.isChecked()){
                            intent2.putExtra(constantOrder.ORDER_TRANSPORT,onFoot.getText().toString() );
                        }

                        if (documentRadiobutton.isChecked()) {
                            intent2.putExtra(constantOrder.ORDER_TAKING, documentRadiobutton.getText().toString());
                        }

                       if (flowerRadiobutton.isChecked()){
                           intent2.putExtra(constantOrder.ORDER_TAKING,flowerRadiobutton.getText().toString() );

                       }
                       if (documentRadiobutton.isChecked()){
                            intent2.putExtra(constantOrder.ORDER_TAKING,documentRadiobutton.getText().toString() );

                        }
                       if (hotFoodRadiobutton.isChecked()){
                           intent2.putExtra(constantOrder.ORDER_TAKING,hotFoodRadiobutton.getText().toString() );

                       }
                        if (giftRadiobutton.isChecked()){
                           intent2.putExtra(constantOrder.ORDER_TAKING,giftRadiobutton.getText().toString() );

                       }
                        if (payWithCard.isChecked()){
                            intent2.putExtra(constantOrder.ORDER_PAYMENT,payWithCard.getText().toString() );

                        }
                        if (payWithCash.isChecked()){
                            intent2.putExtra(constantOrder.ORDER_PAYMENT,payWithCash.getText().toString() );

                        }

                        intent2.putExtra(constantOrder.ORDER_SPINNER,String.valueOf(spinner1.getSelectedItem()));
                        intent2.putExtra(constantOrder.ORDER_SPINNER,String.valueOf(spinner2.getSelectedItem()));


                        intent2.putExtra(constantOrder.ORDER_ADDRESS1, adressFromWhere.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_ADDRESS2,adressInWhere.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_COMMENT1,commentFromWhere.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_COMMENT2,commentInWhere.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_DATE1,dateFromWhere.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_DATE2,dateInWhere.getText().toString() );
                        intent2.putExtra(constantOrder.USER_NAME,nameUserOrder.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_PHONE1,phoneFromWhere.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_PHONE2,phoneInWhere.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_PRICE,priceTextView.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_TIME,timeFromWhere.getText().toString() );
                        intent2.putExtra(constantOrder.ORDER_TIME2,timeInWhere.getText().toString() );

                        Order order = new Order();
                order.setAdressNum1(adressFromWhere.getText().toString());
                order.setTime1(timeFromWhere.getText().toString());
                order.setDate1(dateFromWhere.getText().toString());
                order.setPhone1(phoneFromWhere.getText().toString());
                order.setComment1(commentFromWhere.getText().toString());

                order.setAdressNum2(adressInWhere.getText().toString());
                order.setTime2(timeInWhere.getText().toString());
                order.setDate2(dateInWhere.getText().toString());
                order.setName(userName);
                order.setPhone2(phoneInWhere.getText().toString());
                order.setComment2(commentInWhere.getText().toString());
                order.setPrice(priceTextView.getText().toString());

                String take1 = documentRadiobutton.getText().toString();
                String take2 = flowerRadiobutton.getText().toString();
                String take3 = giftRadiobutton.getText().toString();
                String take4 = hotFoodRadiobutton.getText().toString();

                String foot=onFoot.getText().toString();
                String auto=onAuto.getText().toString();



                if (onFoot.isChecked()) {
                    order.setSpinner(spinner1.getSelectedItem().toString());
                } else if (onAuto.isChecked()) {
                    order.setSpinner(spinner2.getSelectedItem().toString());
                }
                if (onFoot.isChecked()){
                    order.setTransport(foot);
                }
                if (onAuto.isChecked()){

                    order.setTransport(auto);

                }


                if (documentRadiobutton.isChecked()) {
                    order.setWhatTaking(take1);
                    orderSenderReference.push().setValue(order);

                }
                if (flowerRadiobutton.isChecked()) {
                    order.setWhatTaking(take2);
                    orderSenderReference.push().setValue(order);

                }
                if (giftRadiobutton.isChecked()) {
                    order.setWhatTaking(take3);
                    orderSenderReference.push().setValue(order);

                }
                if (hotFoodRadiobutton.isChecked()) {
                    order.setWhatTaking(take4);
                    orderSenderReference.push().setValue(order);


               }
                startActivity(intent2);finish();

                    }
                });

            }
        });

        userChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user =snapshot.getValue(User.class);
                if (user.getId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    userName=user.getName();
                    nameUserOrder.setText(userName);


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

    private void changePrice() {
        if (documentRadiobutton.isChecked()){
            priceTextView.setText("200 Руб");
        }
        if (giftRadiobutton.isChecked()){
            priceTextView.setText("250 Руб");
        }
        if (flowerRadiobutton.isChecked()){

        }
    }


    public void onFootButton(View view) {

        spinner1.setVisibility(View.VISIBLE);
        spinner2.setVisibility(View.GONE);

    }

    public void autoButton(View view) {

        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.VISIBLE);


    }


    public void documents(View view) {
    }


}
