package com.server1.addserver1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ServerListActivity extends AppCompatActivity {
    ListView showServerList;
    ArrayList<String> showList = new ArrayList<>();
    ArrayAdapter adapter;
    SQLiteDatabase sqldb;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);
        showServerList = (ListView) findViewById(R.id.showServerList);
        adapter = new ArrayAdapter(ServerListActivity.this, android.R.layout.simple_list_item_multiple_choice, showList);
        showServerList.setAdapter(adapter);
        sqldb = getApplicationContext().openOrCreateDatabase("Search", MODE_PRIVATE, null);
        c = sqldb.rawQuery("select server from serverName", null);
        while (c.moveToNext()) {
            showList.add(c.getString(0));
        }
        adapter.notifyDataSetChanged();

        sqldb.close();
        c.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.serverDelete:
                SparseBooleanArray checkedItems = showServerList.getCheckedItemPositions();
                int count = adapter.getCount();
                sqldb = getApplicationContext().openOrCreateDatabase("Search", MODE_PRIVATE, null);

                for (int i = count - 1; i >= 0; i--) {
                    Log.i("tttt111", "체크 사이즈" + showList.get(i));
                    if (checkedItems.get(i)) {
                        sqldb.execSQL("delete from serverName where server  = '"+showList.get(i)+"'");
                        showList.remove(i);
                    }
                }
                sqldb.close();
                showServerList.clearChoices();
                adapter.notifyDataSetChanged();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
