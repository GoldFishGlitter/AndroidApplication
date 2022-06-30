package com.example.androidapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> user_name, user_pass;
    CustomAdapter customAdapter;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);

        DB = new DBHelper(HomeActivity.this);
        user_name = new ArrayList<>();
        user_pass = new ArrayList<>();
        StoreDataInArrays();

        customAdapter = new CustomAdapter(HomeActivity.this, user_name, user_pass);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
    }

    void StoreDataInArrays() {
        Cursor cursor = DB.readData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                user_name.add(cursor.getString(0));
                user_pass.add(cursor.getString(1));
            }
        }
    }
}