package com.server1.addserver1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static Spinner spnGetServer;
    public static Button btnGetServer, btnSetServer, select_category;
    public static LinearLayout setLayout;
    public static ProgressBar getLoadingBar;
    public static TextView setNews, getNews;
    public static Context context;
    SQLiteDatabase db;
    Cursor c;
    String[] getServerName = {"서버", "신중일보"};
    public static String connection_server;


    public static ArrayList<getServer> server = new ArrayList<>();

    public static ArrayList<News> list_server = new ArrayList<>();
    public static ArrayList<String> setting_server = new ArrayList<>();
    public static ArrayList<String> setting_get_server = new ArrayList<>();
    public static ArrayList<CategoryInfo> getCategory_name = new ArrayList<>();
    public static ArrayList<String> setCategory_name = new ArrayList<>();
    public static ArrayList<String> setCategory_name_info = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        spnGetServer = (Spinner) findViewById(R.id.spnGetServer);
        btnGetServer = (Button) findViewById(R.id.btnGetServer);
        btnSetServer = (Button) findViewById(R.id.btnSetServer);
        select_category = (Button) findViewById(R.id.select_category);
        setLayout = (LinearLayout) findViewById(R.id.setLayout);
        getNews = (TextView) findViewById(R.id.getNews);
        getLoadingBar = (ProgressBar) findViewById(R.id.getLoadingBar);
        db = getApplicationContext().openOrCreateDatabase("Search", MODE_PRIVATE, null);
        c = db.rawQuery("select * from getCategory", null);

        while (c.moveToNext()) {
            getCategory_name.add(new CategoryInfo(c.getString(0), c.getString(1)));
        }
        c.close();
        db.close();

        final ArrayList<String> spnGetServerList = new ArrayList();
        Collections.addAll(spnGetServerList, getServerName);
        ArrayAdapter<String> spnGetAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spnGetServerList);
        spnGetServer.setAdapter(spnGetAdapter);
        spnGetServer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spnGetServerList.get(position)) {
                    case "서버":
                        connection_server = "";
                        break;
                    case "신중일보":
                        connection_server = "http://sinjoongilbo.co.kr/DBtest/";
                        break;
                    default:
                        connection_server = null;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGetServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(connection_server.equals(""))) {
                    for (int i = 0; i < getCategory_name.size(); i++) {
                        server.add(new getServer(connection_server, getCategory_name.get(i).getCategory_name()));
                        Handler handler1 = new Handler();
                        final int finalI = i;
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int j = 0; j < server.get(finalI).getList().size(); j++) {
                                    server.get(finalI).addServer(list_server);
                                }
                            }
                        }, 500);
                    }
                    getLoadingBar.setVisibility(View.VISIBLE);
                    getNews.setVisibility(View.INVISIBLE);
                    setLayout.setVisibility(View.INVISIBLE);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getServerData();
                            MainActivity.getNews.setVisibility(View.VISIBLE);
                            MainActivity.getLoadingBar.setVisibility(View.INVISIBLE);
                            MainActivity.setLayout.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                } else {
                    if (connection_server.equals("")) {
                        getNews.setText("서버를 선택하지않으셨습니다.");
                        setLayout.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        btnSetServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calltext("등록이 진행중입니다.");
                spnGetServer.setSelection(0);
                if (setCategory_name.size() != 0) {
                    Log.i("testt123", "서버갯수 : " + setting_server.size());
                    for (int i = 0; i <= setting_server.size(); i++) {
                        if (i < setting_server.size()) {
                            final int finalI1;
                            final int a = i * 20000;
                            Handler handler = new Handler();
                            final int finalI = i;

                            if (i == setting_server.size()-1) {
                                 finalI1 = 1001;
                            }else {
                                 finalI1 = i;
                            }

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("testt123", "서버콜");
                                    callServer(finalI, finalI1);

                                }
                            }, a);
                        } else if (i >= setting_server.size()) {

                        }
                    }
                }
                setLayout.setVisibility(View.INVISIBLE);


            }
        });
        select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivityForResult(intent, 1001);
            }
        });
    }


    public static void getServerData() {


    }

    public static int randomNum() {
        double d = Math.random() * 20;
        int a = (int) d;
        return a;
    }

    public static void callServer(final int c, int d) {
        int a = randomNum();
        int b = randomNum();
        if (a!=b) {
            new StartServer(c,setting_server.get(c), a, b, server, true, setCategory_name, setCategory_name_info, d,context);
        }else if (a==b){
            callServer(c,d);
        }
    }
    public static void calltext(String text) {
        setTextNews(text);
    }

    public static void setTextNews(final String text) {

       new Thread(new Runnable() {
           @Override
           public void run() {
               ((Activity)context).runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       getNews.setText(text);
                   }
               });
           }
       }).start();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setCategory_name.clear();
            setCategory_name_info.clear();
            setCategory_name_info.addAll(data.getExtras().getStringArrayList("categoryinfo"));
            setCategory_name.addAll(data.getExtras().getStringArrayList("categoryname"));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addServer:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_addserver, null);
                builder.setView(view);
                builder.setTitle("서버 추가하기");
                final EditText addServerName = (EditText) view.findViewById(R.id.addServerName);
                final EditText addServerURL = (EditText) view.findViewById(R.id.addServerURL);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!addServerName.equals("")) {
                            db = getApplicationContext().openOrCreateDatabase("Search", MODE_PRIVATE, null);
                            db.execSQL("insert into serverName values('" + addServerName.getText() + "','" + addServerURL.getText() + "/')");
                            db.close();
                        } else {
                            addServerName.setHint("이름을 입력하세요");
                        }
                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
                break;
            case R.id.checkServer:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                View view2 = getLayoutInflater().inflate(R.layout.dialog_checkserver, null);
                builder2.setView(view2);
                builder2.setTitle("등록할 서버");
                setting_get_server.clear();
                db = getApplicationContext().openOrCreateDatabase("Search", MODE_PRIVATE, null);
                c = db.rawQuery("select serverURL from serverName", null);
                while (c.moveToNext()) {
                    setting_get_server.add(c.getString(0));
                }
                c.close();
                db.close();
                final ListView listView = (ListView)view2.findViewById(R.id.checklistserver);
                final ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, setting_get_server);
                listView.setAdapter(adapter);

                builder2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setting_server.clear();
                        SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                        int count = adapter.getCount();
                        for (int i = count - 1; i >= 0; i--) {
                            if (checkedItems.get(i)) {
                               setting_server.add(setting_get_server.get(i));
                                Log.i("tttt111", "체크 사이즈" +setting_get_server.get(i));
                            }
                        }
                        int i = 0;
                        while (i < setting_server.size()) {
                            Log.i("tttt001", "" + setting_server.get(i));
                            i++;
                        }
                        listView.clearChoices();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder2.setNegativeButton("취소", null);
                builder2.show();
                break;
            case R.id.showList:
                Intent intent = new Intent(MainActivity.this, ServerListActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}




