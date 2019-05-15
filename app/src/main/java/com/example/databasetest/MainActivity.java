package com.example.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCreateDatabase;
    private Button mDatabaseCreate;
    private Button mUpdateData;
    private Button mDeleteData;
    private Button mQueryData;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
    }

    private void initView() {
        mCreateDatabase = findViewById(R.id.create_database);
        mCreateDatabase.setOnClickListener(this);
        mDatabaseCreate = findViewById(R.id.add_data);
        mDatabaseCreate.setOnClickListener(this);
        mUpdateData = findViewById(R.id.update_data);
        mUpdateData.setOnClickListener(this);
        mDeleteData = findViewById(R.id.delete_data);
        mDeleteData.setOnClickListener(this);
        mQueryData = findViewById(R.id.query_data);
        mQueryData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.create_database:
                dbHelper.getWritableDatabase();
                break;
            case R.id.add_data:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);
                values.clear();
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values);
                break;
            case R.id.update_data:
                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put("price", 10.99);
                db1.update("Book", values1, "name = ?", new String[]{"The Da Vinci Code"});
                break;
            case R.id.delete_data:
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                db2.delete("Book", "Pages > ?", new String[]{"500"});
                break;
            case R.id.query_data:
                SQLiteDatabase db3 = dbHelper.getWritableDatabase();
                Cursor cursor = db3.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.i("MainActivity ", " book name is " + name);
                        Log.i("MainActivity ", " book author is " + author);
                        Log.i("MainActivity ", " book pages is " + pages);
                        Log.i("MainActivity ", " book price is " + price);
                    } while (cursor.moveToNext());
                }
                break;
        }
    }
}
