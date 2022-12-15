package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Popup extends AppCompatActivity {

    EditText inputCode;
    Button join;
    String response, URL, code, makedPer;

    int firstDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);


        URL = "http://59.18.221.32:5000/plan/join";
        inputCode = findViewById(R.id.input);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.35));


        join = findViewById(R.id.btn_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = inputCode.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("code", code);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                sendRequest(jsonObject, URL);
            }
        });
     //   WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
      //  layoutParams.flags=WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        //setContentView(R.layout.activity_popup);
/*
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        int width = (int) (dm.widthPixels * 0.9); // Display 사이즈의 90%

        int height = (int) (dm.heightPixels * 0.9); // Display 사이즈의 90%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;*/
    }
    public void sendRequest(JSONObject jsonObject, String URL){
        class sendData extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {super.onPreExecute();}
            @Override
            protected void onPostExecute(String s) {super.onPostExecute(s);}
            @Override
            protected void onProgressUpdate(Void... values){
                super.onProgressUpdate(values);
            }
            @Override
            protected void onCancelled(String s){
                super.onCancelled(s);
            }
            @Override
            protected void onCancelled(){
                super.onCancelled();
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(Popup.this));
                    String sessionid = getString("session");
                    List<Cookie> cookieList = cookieJar.loadForRequest(HttpUrl.parse(URL));
                    System.out.println(sessionid);
                    System.out.println(cookieList);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    RequestBody requestBody = RequestBody.create(
                            MediaType.parse("application/json; charset=uft-8"),
                            jsonObject.toString()
                    );

                    Request request = new Request.Builder()
                            .addHeader("Cookie", sessionid)
                            .post(requestBody)
                            .url(URL)
                            .build();
                    Response responses = null;
                    responses = client.newCall(request).execute();
                    response = responses.body().string();
                    System.out.println(response);

                    if (response.contains("success")){
                        try{
                            JSONObject resJSON = new JSONObject(response);
                            code = resJSON.getString("code");
                            makedPer = resJSON.getString("duration");
                            firstDay = resJSON.getInt("first_day");
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        if (response.contains("month")){
                            Intent intent = new Intent(Popup.this, MonthTimetable.class);
                            intent.putExtra("code", code);
                            intent.putExtra("period", makedPer);
                            intent.putExtra("firstDay", firstDay);
                            startActivity(intent);
                        } else if (response.contains("week")){
                            Intent intent = new Intent(Popup.this, MakeTimetable.class);
                            intent.putExtra("code", code);
                            intent.putExtra("period", makedPer);
                            intent.putExtra("firstDay", firstDay);
                            startActivity(intent);
                        }
                    } else {
                        Popup.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "약속 생성에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public String getString(String key) {
                SharedPreferences prefs = Popup.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String value = prefs.getString(key, " ");
                return value;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
}