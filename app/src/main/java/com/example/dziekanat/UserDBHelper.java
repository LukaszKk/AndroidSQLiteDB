package com.example.dziekanat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Dziekanat";
    private static final int DB_VERSION = 2;

    public static final String SURNAME = "Nazwisko";
    public static final String NAME = "Imie";
    public static final String EMAIL = "Email";
    public static final String PHONE = "Telefon";
    public static final String PASSWORD = "Haslo";

    private static final String CREATE = "CREATE TABLE ";
    public static final String TAB_NAME = "UserTab";
    private static final String PRIM_KEY = "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
    private static final String CREATE_CLIENT_TAB = CREATE + TAB_NAME + "("
            + PRIM_KEY
            + SURNAME + " TEXT, "
            + NAME + " TEXT, "
            + EMAIL + " TEXT, "
            + PHONE + " TEXT, "
            + PASSWORD + " TEXT);";

    public UserDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDB(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDB(db, oldVersion, newVersion);
    }

    private void updateDB(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DB_VERSION) {
            db.execSQL(CREATE_CLIENT_TAB);
        }
    }

    public void insertDB(SQLiteDatabase db, String surname, String name, String email, String phone, String password) {
        ContentValues values = new ContentValues();
        values.put(SURNAME, surname);
        values.put(NAME, name);
        values.put(EMAIL, email);
        values.put(PHONE, phone);
        values.put(PASSWORD, password);
        db.insert(TAB_NAME, null, values);
    }

    public void updateDB(SQLiteDatabase db, int id, String surname, String name, String email, String phone,
            String password) {
        ContentValues values = new ContentValues();
        values.put(SURNAME, surname);
        values.put(NAME, name);
        values.put(EMAIL, email);
        values.put(PHONE, phone);
        values.put(PASSWORD, password);
        db.update(TAB_NAME, values, "_id=?", new String[] {String.valueOf(id)});
    }

    public void delStudent(SQLiteDatabase db, int id) {
        db.delete(TAB_NAME, "_id=?", new String[] {String.valueOf(id)});
    }
}
