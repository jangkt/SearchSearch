package com.server1.addserver1;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;

public class compareData {
    resultPHP phps;
    public static ArrayList<News> list_set_test = new ArrayList<>();
    public static ArrayList<News> listss = new ArrayList<>();
    public static String setting_server;


    public compareData(String setting_server) {
        this.setting_server = setting_server;
    }

    public void insertData(final int a, final int b, final String caterory, final ArrayList<News> lists, int compare, final int serverend, final int serverOrder) {
        final ArrayList<News> list = new ArrayList<>();
        if (compare == 1) {
            listss.clear();
            listss.addAll(lists);
            list.add(listss.get(a));
            list.add(listss.get(b));
        } else {
            list.add(lists.get(a));
            list.add(lists.get(b));
        }

        phps = new resultPHP();
        phps.execute(setting_server + "result.php", caterory);
        list_set_test.clear();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("test21", "왜안되노 : " + phps.getList().size());
                list_set_test.addAll(phps.getList());
            }
        }, 3000);
        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                compareList(list, list_set_test, caterory,serverend,serverOrder);
                Log.i("test3333", "왜안되노 : " +caterory);
            }
        }, 4000);

    }


    public static int randomNum() {
        double d = Math.random() * 20;
        int a = (int) d;
        return a;
    }

    public static void startInsert(ArrayList<News> list, String category, int serverend,int serverOrder) {
        String subject = list.get(0).getSubject().replace("ggom", "'");
        String content = list.get(0).getContent().replace("ggom", "'");
        String[] urls = {setting_server + "insert.php", subject, content, category,String.valueOf(serverend), String.valueOf(serverOrder)};
        insertPHP insertPHP = new insertPHP();
        insertPHP.execute(urls);
        Log.i("test14", category);
    }

    public static void startInsert2(ArrayList<News> list, String category, int serverend, int serverOrder) {
        String subject = list.get(1).getSubject().replace("ggom", "'");
        String content = list.get(1).getContent().replace("ggom", "'");
        String[] urls2 = {setting_server + "insert.php", subject, content, category,String.valueOf(serverend), String.valueOf(serverOrder)};
        insertPHP insertPHP1 = new insertPHP();
        insertPHP1.execute(urls2);
        Log.i("test14", "222"+category);
    }

    public void compareList(ArrayList<News> a, ArrayList<News> b, String category, int serverend, int serverOrder) {
        Log.i("test51", "시작");
        if (b.size() != 0) {
            for (int i = 0; i < b.size(); i++) {
                String as = a.get(0).getSubject();
                String bs = b.get(i).getSubject();
                Log.i("test51", "for" + as + " : " + bs);
                if (as.equals(bs)) {
                    Log.i("test51", "서버 : " + setting_server);
                    Log.i("test51", "1개 중복" + a.size() + " : " + a.get(0).getSubject());
                    a.remove(0);
                    compareList2(a, b, category,serverend,serverOrder);
                    break;
                } else {
                    if (b.size() - 1 == i) {
                        Log.i("test51", "서버 : " + setting_server);
                        Log.i("test51", "0개 중복" + a.size() + " : " + a.get(0).getSubject() + " : " + a.get(1).getSubject());
                        compareList2(a, b, category,serverend,serverOrder);
                    }
                }
            }
        } else {
            Log.i("test51", "서버 : " + setting_server);
            Log.i("test51", "기사없음");
            startInsert(a, category, serverend,serverOrder);
            startInsert2(a, category,serverend,serverOrder);
        }
    }

    public void compareList2(final ArrayList<News> a, final ArrayList<News> b, final String category, int serverend, int serverOrder) {
        for (int i = 0; i < b.size(); i++) {
            String bs = b.get(i).getSubject();
            if (a.size() >= 2) {
                String as = a.get(0).getSubject();
                String as1 = a.get(1).getSubject();
                if (as1.equals(bs)) {
                    a.remove(1);
                } else {
                    if (i == b.size() - 1) {
                        Log.i("test51", "서버 : " + setting_server);
                        Log.i("test51", "두번째 0개 중복" + a.size() + " : " + a.get(0).getSubject() + " : " + a.get(1).getSubject());
                        startInsert(a, category,serverend,serverOrder);
                        startInsert2(a, category,serverend,serverOrder);
                    }
                }
            } else {
                String as = a.get(0).getSubject();
                if (as.equals(bs)) {
                    a.clear();
                    Log.i("test51", "서버 : " + setting_server);
                    Log.i("test51", "남은게 없다");
                    break;
                } else {
                    if (i == b.size() - 1) {
                        Log.i("test51", "서버 : " + setting_server);
                        Log.i("test51", "두번째 1개 중복 1개남음" + a.size() + " : " + a.get(0).getSubject());
                        startInsert(a, category,serverend,serverOrder);
                    }
                }
            }
        }
    }
}
