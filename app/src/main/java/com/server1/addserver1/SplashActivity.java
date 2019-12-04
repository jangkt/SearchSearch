package com.server1.addserver1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    String[] category = {"culture.php", "economy.php", "politics.php", "entertament.php", "science.php", "sports.php", "world.php"};
    String[] categoryinfo = {"문화", "경제", "정치", "연예", "과학", "스포츠", "국제"};
    ArrayList<CategoryInfo> getList = new ArrayList<>();
    ArrayList<CategoryInfo> setList = new ArrayList<>();
    SQLiteDatabase db;
    Cursor getC, setC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CategoryDBHelper helper = new CategoryDBHelper(SplashActivity.this, "Search", null, 1);

        db = helper.getWritableDatabase();
        getC = db.rawQuery("select * from getCategory", null);
        while (getC.moveToNext()) {
            getList.add(new CategoryInfo(getC.getString(0), getC.getString(1)));
            Log.i("test11", getC.getString(1));
        }
        setC = db.rawQuery("select * from setCategory", null);
        while (setC.moveToNext()) {
            setList.add(new CategoryInfo(setC.getString(0), setC.getString(1)));
            Log.i("test11", setC.getString(1));
        }
        if (getList.size() == 0) {
            for (int i = 0; i < category.length; i++) {
                String cate = category[i];
                String setCate = cate.substring(0, category[i].length() - 4);
                String cateinfo = categoryinfo[i];

                db.execSQL("insert into getCategory values('" + cate + "','" + cateinfo + "') ");
                db.execSQL("insert into setCategory values('" + setCate + "','" + cateinfo + "') ");
                Log.i("test131", cateinfo);
            }

        } else {
//            for (int i = 0; i < getList.size(); i++) {
//                String cate = getList.get(i).category_name;
//                String setCate = cate.substring(0, getList.size() - 4);
//                String cateinfo = getList.get(i).category_info;
//
//                db.execSQL("insert into getCategory values('" + cate + "','" + cateinfo + "') ");
//                db.execSQL("insert into setCategory values('" + setCate + "','" + cateinfo + "') ");
//
//
//                Log.i("test11", setCate);
//            }
        }
        db.close();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
