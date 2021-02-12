package com.example.ues_finalversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ues_finalversion.AccountProfie.ProfileActivity;
import com.example.ues_finalversion.ChatActivites.ChatActivity;
import com.example.ues_finalversion.ChatActivites.FirstMessage;
import com.example.ues_finalversion.ChatActivites.MainChatActivity;
import com.example.ues_finalversion.ChatActivites.User;
import com.example.ues_finalversion.CreateNewOrder.NewOrderActivity;
import com.example.ues_finalversion.CreateNewOrder.Order;
import com.example.ues_finalversion.CreateNewOrder.OrderActivity;
import com.example.ues_finalversion.CreateNewOrder.constantOrder;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private FirebaseDatabase database;
    private DatabaseReference mDataBase;
    private ChildEventListener readChildEventListener;
    // private FirstMessageAdapter adapter;
    private ListView listViewActiv, listViewEnded;
    private ArrayAdapter<String> adapter1;
    private List<String> listData;
    private List<Order> listTemp;
    private Intent intent;
    private RadioButton radioButton1, radioButton2;
    private TextView textViewList;
    private DatabaseReference userDatabaseReference;
    private ChildEventListener userChildEventListener;


    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ЭТО ИНИЦИЛИЗАЦИИ КНОПКИ НАВИШАЦИИ!!!!
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Order);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //ЭТО ИНИЦИЛИЗАЦИИ КНОПКИ НАВИШАЦИИ!!!!
        textViewList = findViewById(R.id.ListText);
        listViewActiv = findViewById(R.id.ListViewActiv);
        listViewEnded = findViewById(R.id.ListViewEnded);

        init();
        getDataFromDB();
        setOnClickItem();

    }

    private void init() {
        listViewActiv = findViewById(R.id.ListViewActiv);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listViewActiv.setAdapter(adapter1);
        mDataBase = FirebaseDatabase.getInstance().getReference("Order");
    }

    //считовать DataSnapshot внести инф что записано в бд
    private void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listData.size() > 0) listData.clear();
                if (listTemp.size() > 0) listTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Order order = ds.getValue(Order.class);
                    assert order != null;
                    listData.add(order.getName());
                    listTemp.add(order);
                }
                adapter1.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    private void setOnClickItem() {
        listViewActiv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = listTemp.get(position);
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra(constantOrder.ORDER_ADDRESS1, order.getAdressNum1());
                intent.putExtra(constantOrder.ORDER_ADDRESS2, order.getAdressNum2());
                intent.putExtra(constantOrder.ORDER_COMMENT1, order.getComment1());
                intent.putExtra(constantOrder.ORDER_COMMENT2, order.getComment2());
                intent.putExtra(constantOrder.ORDER_DATE1, order.getDate1());
                intent.putExtra(constantOrder.ORDER_DATE2, order.getDate2());
                intent.putExtra(constantOrder.USER_NAME, order.getName());
                intent.putExtra(constantOrder.ORDER_PHONE1, order.getPhone1());
                intent.putExtra(constantOrder.ORDER_PHONE2, order.getPhone2());
                intent.putExtra(constantOrder.ORDER_PRICE, order.getPrice());
                intent.putExtra(constantOrder.ORDER_SPINNER, order.getSpinner());
                intent.putExtra(constantOrder.ORDER_TIME, order.getTime1());
                intent.putExtra(constantOrder.ORDER_TIME2, order.getTime2());
                intent.putExtra(constantOrder.ORDER_TRANSPORT, order.getTransport());
                intent.putExtra(constantOrder.ORDER_TAKING, order.getWhatTaking());
                startActivity(intent);
            }
        });
    }


    //ЭТО КНОПКИ НАВИШАЦИИ!!!!
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
                overridePendingTransition(0, 0);
                finish();
                return true;


        }
        return false;
    }//ЭТО КНОПКИ НАВИШАЦИИ!!!!


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
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void activOrders(View view) {
        listViewEnded.setVisibility(View.INVISIBLE);
        listViewActiv.setVisibility(View.VISIBLE);
        textViewList.setVisibility(View.INVISIBLE);
    }

    public void endedOrders(View view) {
        listViewActiv.setVisibility(View.INVISIBLE);
        listViewEnded.setVisibility(View.INVISIBLE);
        textViewList.setVisibility(View.VISIBLE);
    }




}


