package com.server1.addserver1;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class StartServer {
    String servername;
    int a;
    int b;
    int c;
    boolean endData;
    ArrayList<getServer> server;

    public StartServer(int serverOrder, String servername, int a, int b, ArrayList<getServer> server, boolean endData, ArrayList<String> catename, ArrayList<String> cateinfo, int c, Context context) {
        this.servername = servername;
        this.a = a;
        this.b = b;
        this.server = server;
        this.endData = endData;
        this.c = c;
        if (endData == true) {
            startService(catename, cateinfo, a, b, servername, serverOrder, context);
        } else {
            MainActivity.getNews.setText("연결이 끝났습니다");
            MainActivity.spnGetServer.setSelection(0);
        }
    }

    public void starts(int a, int b, String category, String categoryInfo, String setting, int serverend, int serverOrder) {
        ArrayList<News> list = new ArrayList<>();
        compareData data = new compareData(setting);
        Log.i("test28", setting);
        if (a != b) {
            switch (categoryInfo) {
                case "문화":
                    Log.i("test20", categoryInfo);
                    list.addAll(server.get(0).getList());
                    data.insertData(a, b, category, list, 1, serverend, serverOrder);
                    break;
                case "경제":
                    Log.i("test20", categoryInfo);
                    list.addAll(server.get(1).getList());
                    data.insertData(a, b, category, list, 1, serverend, serverOrder);
                    break;
                case "정치":
                    Log.i("test20", "정치다" + categoryInfo);
                    list.addAll(server.get(2).getList());
                    data.insertData(a, b, category, list, 1, serverend, serverOrder);
                    break;
                case "연예":
                    Log.i("test20", "연예다" + categoryInfo);
                    list.addAll(server.get(3).getList());
                    data.insertData(a, b, category, list, 1, serverend, serverOrder);
                    break;
                case "과학":
                    Log.i("test20", categoryInfo);
                    list.addAll(server.get(4).getList());
                    data.insertData(a, b, category, list, 1, serverend, serverOrder);
                    break;
                case "스포츠":
                    Log.i("test20", categoryInfo);
                    list.addAll(server.get(5).getList());
                    data.insertData(a, b, category, list, 1, serverend, serverOrder);
                    break;
                case "국제":
                    Log.i("test20", categoryInfo);
                    list.addAll(server.get(6).getList());
                    data.insertData(a, b, category, list, 1, serverend, serverOrder);
                    break;
                default:
                    Log.i("test20", categoryInfo);
                    break;
            }


        }
    }

    public void startService(final ArrayList<String> catename, final ArrayList<String> cateinfo, final int a, final int b, final String servername, final int serverOrder, final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int d = c;
                        for (int j = 0; j < catename.size(); j++) {
                            try {
                                if (d == 1001) {
                                    if (j == catename.size() - 1) {
                                        d = 1002;
                                        starts(a, b, catename.get(j), cateinfo.get(j), servername, d, serverOrder);
                                        Log.i("test99", cateinfo.get(j));
                                        Log.i("test99", catename.get(j));
                                    }else {
                                        starts(a, b, catename.get(j), cateinfo.get(j), servername, d, serverOrder);
                                        Log.i("test99", cateinfo.get(j));
                                        Log.i("test99", catename.get(j));
                                    }
                                    MainActivity.calltext("마지막 서버 등록 진행");
                                } else {
                                    if (j == catename.size() - 1) {
                                        d = 1003;
                                        starts(a, b, catename.get(j), cateinfo.get(j), servername, d, serverOrder);
                                        Log.i("test99", cateinfo.get(j));
                                        Log.i("test99", catename.get(j));
                                    } else {
                                        starts(a, b, catename.get(j), cateinfo.get(j), servername, d, serverOrder);
                                        Log.i("test99", cateinfo.get(j));
                                        Log.i("test99", catename.get(j));
                                    }
                                    MainActivity.calltext((c + 1) + "개의 서버 등록 진행");
                                }

                                Thread.sleep(1300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();
    }
}
