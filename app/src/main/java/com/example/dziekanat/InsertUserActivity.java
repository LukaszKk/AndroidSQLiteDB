package com.example.dziekanat;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InsertUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user);
    }

    public void onInsertStudent(View view) {
        EditText editText = findViewById(R.id.edtSurname);
        String surname = editText.getText().toString();
        editText.setText("");

        editText = findViewById(R.id.edtName);
        String name = editText.getText().toString();
        editText.setText("");

        editText = findViewById(R.id.edtEmail);
        String email = editText.getText().toString();
        editText.setText("");

        editText = findViewById(R.id.edtPhone);
        String phone = editText.getText().toString();
        editText.setText("");

        editText = findViewById(R.id.edtPassword);
        String password = editText.getText().toString();
        editText.setText("");

        try {
            UserDBHelper userDBHelper = new UserDBHelper(this);
            SQLiteDatabase db = userDBHelper.getReadableDatabase();
            userDBHelper.insertDB(db, surname, name, email, phone, password);
            Toast.makeText(this, "** Dodano uzytkownika do bazy **", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Baza danych nie osiagalna", Toast.LENGTH_SHORT).show();
        }
    }
}

