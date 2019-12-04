package com.server1.addserver1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    Button add_category, complete_category;
    GridLayout cb_layout;
    static String[] category = {"문화", "경제", "정치", "연예", "과학", "스포츠", "국제"};
    ArrayList<CategoryInfo> category_name_list = new ArrayList<>();
    ArrayList<String> setCategory_name_list = new ArrayList<>();
    ArrayList<String> setCategory_info_list = new ArrayList<>();
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        add_category = (Button) findViewById(R.id.add_category);
        complete_category = (Button) findViewById(R.id.complete_category);
        cb_layout = (GridLayout) findViewById(R.id.cb_layout);


        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_category, null);
                builder.setView(view);
                builder.setTitle("카테고리 추가하기");
                final EditText name_category = (EditText) view.findViewById(R.id.name_category);
                final Spinner spn_category = (Spinner) view.findViewById(R.id.spn_category);
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(CategoryActivity.this, android.R.layout.simple_list_item_activated_1, category);
                spn_category.setAdapter(adapter);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!name_category.equals("")) {
                            db = getApplicationContext().openOrCreateDatabase("Search", MODE_PRIVATE, null);
                            db.execSQL("insert into setCategory values('" + name_category.getText() + "','" + spn_category.getSelectedItem().toString() + "')");
//                            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//                            params.setMargins(20, 20, 20, 20);
//                            CheckBox cb = new CheckBox(CategoryActivity.this);
//                            cb.setLayoutParams(params);
//                            cb.setText(name_category.getText());
//                            cb_layout.addView(cb, cb_layout.getChildCount());
                            db.close();
                            onResume();
                        } else {
                            name_category.setHint("이름을 입력하세요");
                        }
                    }
                });
                builder.show();
            }
        });
        complete_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CheckBox> category_check = new ArrayList<>();
                db = getApplicationContext().openOrCreateDatabase("Search", MODE_PRIVATE, null);
                setCategory_name_list.clear();
                setCategory_info_list.clear();
                for (int i = 0; i < cb_layout.getChildCount(); i++) {
                    View view = cb_layout.getChildAt(i);
                    category_check.add((CheckBox) view);
                    Log.i("test", "" + category_check.get(i).isChecked());
                    if (category_check.get(i).isChecked()) {
                        Cursor c = db.rawQuery("select categoryinfo from setCategory where categoryname = '" + category_check.get(i).getText() + "'", null);
                        while (c.moveToNext()) {
                            setCategory_info_list.add(c.getString(0));
                            setCategory_name_list.add(String.valueOf(category_check.get(i).getText()));
                            Log.i("test11", c.getString(0));
                        }
                        c.close();
                    }
                }

                db.close();
                Intent intent = getIntent();

                intent.putExtra("categoryname", setCategory_name_list);
                intent.putExtra("categoryinfo", setCategory_info_list);

                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        category_name_list.clear();
        cb_layout.removeAllViews();
        db = getApplicationContext().openOrCreateDatabase("Search", MODE_PRIVATE, null);
        c = db.rawQuery("select * from setCategory", null);
        int i = 0;
        while (c.moveToNext()) {
            category_name_list.add(new CategoryInfo(c.getString(0), c.getString(1)));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(20, 20, 20, 20);
            CheckBox cb = new CheckBox(CategoryActivity.this);
            cb.setLayoutParams(params);
            cb.setText(category_name_list.get(i).getCategory_name());
            if (cb_layout.getChildCount() != 0) {
                cb_layout.addView(cb, cb_layout.getChildCount());
            } else {
                cb_layout.addView(cb, 0);
            }
            i++;
        }
        c.close();
        db.close();
        Log.i("test11", "리점");
    }
}
