package com.example.wuken.weather_save;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.wuken.weather_save.DbConstants.TABLE_NAME;
import static android.provider.BaseColumns._ID;
import static com.example.wuken.weather_save.DbConstants.WEATHER;
import static com.example.wuken.weather_save.DbConstants.TEMP;
import static com.example.wuken.weather_save.DbConstants.DATE;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.view.View;

public class activity_viewdb extends AppCompatActivity {
    private DBHelper dbhelper = null;
    ListView ListView_db;
    Button btn_delete;
    Button btn_backtomain;
    String str_id;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdb);

        ListView_db = (ListView) findViewById(R.id.ListView_db);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_backtomain=(Button)findViewById(R.id.btn_backtomain);

        dbhelper = new DBHelper(this);

        Cursor cursor = getCursor();

        String[] from = {_ID, WEATHER, TEMP, DATE};
        int[] to = {R.id.txt_ID, R.id.txt_WEATHER, R.id.txt_TEMP, R.id.txt_DATE};


        adapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        ListView_db.setAdapter(adapter);

        ListView_db.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                str_id = String.valueOf(ListView_db.getItemIdAtPosition(position));
            }
        });

        btn_delete.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                db.delete(TABLE_NAME, _ID + "=" + str_id, null);
                //SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
                //adapter.notifyDataSetChanged();//無法刷新
                //ListView_db.setAdapter(adapter);
                refresh();
            }
        });

        btn_backtomain.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(activity_viewdb.this,MainActivity.class);
                startActivity(intent);
                activity_viewdb.this.finish();
            }
        });
    }

    public void refresh() {
        finish();
        Intent intent = new Intent(activity_viewdb.this, activity_viewdb.class);
        startActivity(intent);
    }

    private Cursor getCursor(){
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] columns = {_ID, WEATHER, TEMP, DATE};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
    }
}
