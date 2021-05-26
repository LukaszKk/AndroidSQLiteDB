package com.example.dziekanat;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onExit(View view) {
        finish();
    }

    public void onViewDB(View view) {
        Intent intent = new Intent(MainActivity.this, ViewUserActivity.class);
        startActivity(intent);
    }

    public void onInsertDB(View view) {
        Intent intent = new Intent(MainActivity.this, InsertUserActivity.class);
        startActivity(intent);
    }
}