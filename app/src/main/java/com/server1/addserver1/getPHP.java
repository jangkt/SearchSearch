package com.server1.addserver1;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class getPHP extends AsyncTask<String, Integer, String> {
    public ArrayList<News> list = new ArrayList<>();

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder jsonHtml = new StringBuilder();
        try {
            // 연결 url 설정
            URL url = new URL(urls[0]);
            // 커넥션 객체 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 연결되었으면.
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                // 연결되었음 코드가 리턴되면.
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    for (; ; ) {
                        // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                        String line = br.readLine();
                        if (line == null) break;
                        // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                        jsonHtml.append(line);
                    }
                    br.close();
                }
                conn.disconnect();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonHtml.toString();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onPostExecute(String str) {

        String json = str.replace("rudxo'", "DDOM");
        String s = json.replace("'", "ggom");
        String s2 = s.replace("DDOM", "'");

        try {

            JSONObject obj = new JSONObject(s2);
            JSONArray array = obj.getJSONArray("news");
            for (int i = 0; i < array.length(); i++) { //jsonObject에 담긴 두 개의 jsonObject를 jsonArray를 통해 하나씩 호출한다.
                try {
                    obj = array.getJSONObject(i);
                    list.add(new News(obj.getString("제목"), obj.getString("내용")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.i("test31", "이건" +s2);
            MainActivity.getNews.setText("서버연결을 성공하였습니다.");


        } catch (Throwable t) {
            Log.i("test11", "서버관련" +t.getMessage());
            MainActivity.getNews.setText("서버연결을 실패했습니다.");
            MainActivity.setLayout.setVisibility(View.INVISIBLE);
        }

    }


    public ArrayList<News> getList() {
        ArrayList<News> returnList = new ArrayList<>();
        returnList.addAll(list);
        return returnList;
    }
}

