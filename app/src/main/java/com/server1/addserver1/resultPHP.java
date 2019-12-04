package com.server1.addserver1;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class resultPHP extends AsyncTask<String, Integer, String> {
    public ArrayList<News> list = new ArrayList<>();

    @Override
    protected String doInBackground(String... urls) {
        String test = urls[1];
        String postParameters = "test=" + test;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urls[0]); // 주소가 저장된 변수를 이곳에 입력합니다.
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(5000); //5초안에 응답이 오지 않으면 예외가 발생합니다.
            httpURLConnection.setConnectTimeout(5000); //5초안에 연결이 안되면 예외가 발생합니다.

            httpURLConnection.setRequestMethod("POST"); //요청 방식을 POST로 합니다.

            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8")); //전송할 데이터가 저장된 변수를 이곳에 입력합니다. 인코딩을 고려해줘야 합니다.

            outputStream.flush();
            outputStream.close();


            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.i("test11", "POST response code - " + responseStatusCode);

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {

                // 정상적인 응답 데이터
                inputStream = httpURLConnection.getInputStream();
            } else {

                // 에러 발생

                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }


            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        StringBuilder jsonHtml = new StringBuilder();
//        try {
//            // 연결 url 설정
//            URL url = new URL(urls[0]);
//            // 커넥션 객체 생성
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            // 연결되었으면.
//            if (conn != null) {
//                conn.setConnectTimeout(10000);
//                conn.setUseCaches(false);
//                // 연결되었음 코드가 리턴되면.
//                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//                    for (; ; ) {
//                        // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
//                        String line = br.readLine();
//                        if (line == null) break;
//                        // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
//                        jsonHtml.append(line);
//                    }
//                    br.close();
//                }
//                conn.disconnect();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        return sb.toString();
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
                obj = array.getJSONObject(i);
                list.add(new News(obj.getString("제목"), obj.getString("내용")));
            }
            Log.i("test15", "이건" +s2);

        } catch (Throwable t) {
            Log.i("test16", t.getMessage());

        }

    }

    public ArrayList<News> getList() {
        return list;
    }
}
