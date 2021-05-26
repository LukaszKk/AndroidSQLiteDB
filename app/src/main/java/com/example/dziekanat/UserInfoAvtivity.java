package com.example.dziekanat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserInfoAvtivity extends AppCompatActivity {

    public static final String USER_INFO = "User info";
    private int userId;
    private static SQLiteDatabase db;
    private static UserDBHelper userDB;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userId = (int) getIntent().getExtras().get(USER_INFO);
        try {
            userDB = new UserDBHelper(this);
            db = userDB.getReadableDatabase();
            Cursor cursor = db.query(UserDBHelper.TAB_NAME,
                    new String[] {"_id", UserDBHelper.SURNAME, UserDBHelper.NAME, UserDBHelper.EMAIL,
                            UserDBHelper.PHONE, UserDBHelper.PASSWORD},
                    "_id=?",
                    new String[] {String.valueOf(userId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                EditText editText = findViewById(R.id.edtSurnameEdit);
                editText.setText(cursor.getString(1));

                editText = findViewById(R.id.edtNameEdit);
                editText.setText(cursor.getString(2));

                editText = findViewById(R.id.edtEmailEdit);
                editText.setText(cursor.getString(3));

                editText = findViewById(R.id.edtPhoneEdit);
                editText.setText(cursor.getString(4));

                editText = findViewById(R.id.edtPasswordEdit);
                editText.setText(cursor.getString(5));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Baza danych nie osiagalna", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUpdate(View view) {
        EditText editText = findViewById(R.id.edtSurnameEdit);
        String surname = editText.getText().toString();

        editText = findViewById(R.id.edtNameEdit);
        String name = editText.getText().toString();

        editText = findViewById(R.id.edtEmailEdit);
        String email = editText.getText().toString();

        editText = findViewById(R.id.edtPhoneEdit);
        String phone = editText.getText().toString();

        editText = findViewById(R.id.edtPasswordEdit);
        String password = editText.getText().toString();

        try {
            userDB.updateDB(db, userId, surname, name, email, phone, password);
            Toast.makeText(this, "** Zmodyfikowano rekord z bazy **", Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Baza danych nie osiagalna", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRemove(View view) {
        try {
            userDB.delStudent(db, userId);
            Toast.makeText(this, "** Skasowano rekord z bazy **", Toast.LENGTH_SHORT).show();

            EditText text = findViewById(R.id.edtNameEdit);
            text.setText(getString(R.string.deleted));

            text = findViewById(R.id.edtEmailEdit);
            text.setText(getString(R.string.deleted));

            text = findViewById(R.id.edtPhoneEdit);
            text.setText(getString(R.string.deleted));

            text = findViewById(R.id.edtPasswordEdit);
            text.setText(getString(R.string.deleted));
        } catch (SQLiteException e) {
            Toast.makeText(this, "Baza danych nie osiagalna", Toast.LENGTH_SHORT).show();
        }
    }
}
