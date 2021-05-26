package com.example.dziekanat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewUserActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private CursorAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        AdapterView.OnItemClickListener listener = (AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(ViewUserActivity.this, UserInfoAvtivity.class);
            intent.putExtra(UserInfoAvtivity.USER_INFO, (int) id);
            startActivity(intent);
        };

        try {
            SQLiteOpenHelper studDBHelper = new UserDBHelper(this);
            db = studDBHelper.getReadableDatabase();
            cursor = db.query(UserDBHelper.TAB_NAME, new String[] {"_id", UserDBHelper.SURNAME, UserDBHelper.NAME,
                            UserDBHelper.PHONE},
                    null, null, null, null, UserDBHelper.SURNAME);
            listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor,
                    new String[] {UserDBHelper.SURNAME, UserDBHelper.NAME, UserDBHelper.PHONE},
                    new int[] {android.R.id.text1, android.R.id.text2}, 0);

            ((SimpleCursorAdapter) listAdapter).setViewBinder((View view, Cursor cursor, int columnIndex) -> {
                if (columnIndex == 1) {
                    ((TextView) view).setText(
                            String.format("%s %s", cursor.getString(1), cursor.getString(2)));
                } else if (columnIndex == 2) {
                    ((TextView) view).setText(cursor.getString(3));
                }
                return true;
            });

            ListView userList = findViewById(R.id.lstSTUDENTS_LST);
            userList.setAdapter(listAdapter);
            userList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
            userList.setOnItemClickListener(listener);
        } catch(SQLiteException e) {
            Toast.makeText(this, "Baza danych nie osiagalna", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            cursor = db.query(UserDBHelper.TAB_NAME, new String[]{"_id", UserDBHelper.SURNAME, UserDBHelper.NAME,
                            UserDBHelper.PHONE},
                    null, null, null, null, UserDBHelper.SURNAME);
            listAdapter.changeCursor(cursor);
        } catch (SQLiteException e) {
            Toast.makeText(this, "Baza danych nie osiagalna", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
}
