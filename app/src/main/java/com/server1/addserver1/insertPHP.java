package com.server1.addserver1;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class insertPHP extends AsyncTask<String, Integer, String> {
    @SuppressLint("WrongThread")
    @Override
    protected String doInBackground(String... urls) {

        String wr_subject = urls[1];
            String resultContent = "";
            String[] wr_content = urls[2].split(" =");
            ArrayList<String> content = new ArrayList<>();
            for (int i = 0; i < wr_content.length; i++) {
                content.add(wr_content[i]);
            }
            String test = urls[3];
            String serverend = urls[4];
            String serverOrder = urls[5];

            content.remove(0);
            for (int i = 0; i < content.size(); i++) {
                resultContent += content.get(i);
            }
        Log.i("test1001", "서버끝난갯수"+serverend);
//        MainActivity.calltext(serverend+"개 완료되었습니다.");
        if (serverend.equals("1002")){
            MainActivity.calltext("모두 완료되었습니다.");
        }else if (serverend.equals("1003")){
            MainActivity.calltext((Integer.parseInt(serverOrder)+1)+"번째 등록이 완료되었습니다.");
        }

        Log.i("test991", test);
            // HTTP 메시지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야 합니다.
            // 전송할 데이터는 “이름=값” 형식이며 여러 개를 보내야 할 경우에는 항목 사이에 &를 추가합니다.
            // 여기에 적어준 이름을 나중에 PHP에서 사용하여 값을 얻게 됩니다.
           String postParameters = "wr_subject=" + wr_subject + "&wr_content=" + resultContent + "&test=" + test;

            try {
                // 2. HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송합니다.
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




            // 3. 응답을 읽습니다.

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


            // 4. StringBuilder를 사용하여 수신되는 데이터를 저장합니다.
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }


            bufferedReader.close();


            // 5. 저장된 데이터를 스트링으로 변환하여 리턴합니다.
            return sb.toString();


        } catch (Exception e) {

            Log.i("test11", "InsertData: Error ", e);

            return new String("Error: " + e.getMessage());
        }

    }


}

