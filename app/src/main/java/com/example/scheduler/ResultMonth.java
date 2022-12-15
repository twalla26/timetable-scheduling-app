package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ResultMonth extends AppCompatActivity {

    Button sunday0,sunday1,sunday2,sunday3,sunday4,sunday5;
    Button monday0,monday1,monday2,monday3,monday4,monday5;
    Button tuesday0,tuesday1,tuesday2,tuesday3,tuesday4, tuesday5;
    Button wednesday0,wednesday1,wednesday2,wednesday3,wednesday4, wednesday5;
    Button thursday0,thursday1,thursday2,thursday3,thursday4, thursday5;
    Button friday0,friday1,friday2,friday3,friday4, friday5;
    Button saturday0,saturday1,saturday2,saturday3,saturday4, saturday5;

    Button edit, OK;

    TextView firstT, secondT, thirdT;

    double[] result = new double[30];

    int max;
    String code, response;

    String URL, URL_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_month);

        firstT = findViewById(R.id.first);
        secondT = findViewById(R.id.second);
        thirdT = findViewById(R.id.third);

        Intent intent = getIntent();
        code = intent.getExtras().getString("code");
        URL = "http://59.18.221.32:5000/plan/timetable/" + code;
        URL_B = URL + "/best_time";
        System.out.println(URL);

        sendRequest(URL);
        requestBT(URL_B);

        edit = findViewById(R.id.btn_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultMonth.this, MonthTimetable.class);
                intent.putExtra("code", code);
                startActivity(intent);
            }
        });
        OK = findViewById(R.id.btn_ok);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultMonth.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void sendRequest(String URL){
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
                    CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(ResultMonth.this));
                    String sessionid = getString("session");
                    List<Cookie> cookieList = cookieJar.loadForRequest(HttpUrl.parse(URL));
                    System.out.println(sessionid);
                    System.out.println(cookieList);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    Request request = new Request.Builder()
                            .addHeader("Cookie", sessionid)
                            .url(URL)
                            .build();
                    Response responses = null;
                    responses = client.newCall(request).execute();
                    response = responses.body().string();
                    System.out.println(response);

                    JSONObject jsonObject = new JSONObject(response);
                    int startDay = jsonObject.getInt("first_day");
                    int max = jsonObject.getInt("member_cnt");
                    JSONArray jsonArray = jsonObject.getJSONArray("overlapped_schedule");
                    for (int i=0; i<30; i++){
                        result[i] = (double)jsonArray.get(i);
                    }

                    Button[] month={sunday0, monday0, tuesday0, wednesday0, thursday0, friday0, saturday0,
                            sunday1, monday1, tuesday1, wednesday1, thursday1, friday1, saturday1,
                            sunday2, monday2, tuesday2, wednesday2, thursday2, friday2, saturday2,
                            sunday3, monday3, tuesday3, wednesday3, thursday3, friday3, saturday3,
                            sunday4, monday4, tuesday4, wednesday4, thursday4, friday4, saturday4,
                            sunday5, monday5, tuesday5, wednesday5, thursday5, friday5, saturday5};

                    int [] month1={R.id.sunday0, R.id.monday0, R.id.tuesday0, R.id.wednesday0, R.id.thursday0, R.id.friday0, R.id.saturday0,
                            R.id.sunday1, R.id.monday1, R.id.tuesday1, R.id.wednesday1, R.id.thursday1, R.id.friday1, R.id.saturday1,
                            R.id.sunday2, R.id.monday2, R.id.tuesday2, R.id.wednesday2, R.id.thursday2, R.id.friday2, R.id.saturday2,
                            R.id.sunday3, R.id.monday3, R.id.tuesday3, R.id.wednesday3, R.id.thursday3, R.id.friday3, R.id.saturday3,
                            R.id.sunday4, R.id.monday4, R.id.tuesday4, R.id.wednesday4, R.id.thursday4, R.id.friday4, R.id.saturday4,
                            R.id.sunday5, R.id.monday5, R.id.tuesday5, R.id.wednesday5, R.id.thursday5, R.id.friday5, R.id.saturday5};

                    for(int i=0; i<42;i++){
                        month[i]=findViewById(month1[i]);
                    }

                    int a=(startDay/10)%100;
                    System.out.println(a);
                    int b=startDay%10;
                    int whichDay = b;
                    System.out.println(b);
                    int c=(startDay/1000)%100;
                    System.out.println(c);

                    for(int i=0;i<30;i++) {
                        int finalB = b;
                        int finalA = a;
                        ResultMonth.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                month[finalB].setText(String.valueOf(finalA));
                            }
                        });
                        b++;
                        if ((c == 12 || c == 1 || c == 3 || c == 5 || c == 7 || c == 8 || c == 10) && a == 32) {
                            a = 0;
                        } else if ((c == 2) && a == 29) {
                            a = 0;
                        } else if (a == 31)
                            a = 0;
                        a++;
                    }
                    for (int i=whichDay; i<30+whichDay; i++){
                        month[i].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                        month[i].setAlpha((float)((result[i-whichDay]/max)+0.1));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public String getString(String key) {
                SharedPreferences prefs = ResultMonth.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String value = prefs.getString(key, " ");
                return value;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
    public void requestBT(String URL){
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
                    CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(ResultMonth.this));
                    String sessionid = getString("session");
                    List<Cookie> cookieList = cookieJar.loadForRequest(HttpUrl.parse(URL));
                    System.out.println(sessionid);
                    System.out.println(cookieList);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    Request request = new Request.Builder()
                            .addHeader("Cookie", sessionid)
                            .url(URL)
                            .build();
                    Response responses = null;
                    responses = client.newCall(request).execute();
                    response = responses.body().string();
                    System.out.println(response);

                    JSONObject jsonObject = new JSONObject(response);
                    String first = "2022년 1월 10일 ~\n 2022년 1월 12일";
                    String second = "2022년 1월 9일 ~\n 2022년 1월 11일";
                    String third = "2022년 1월 11일 ~\n 2022년 1월 13일";

                    ResultMonth.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            firstT.setText(first);
                            secondT.setText(second);
                            thirdT.setText(third);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public String getString(String key) {
                SharedPreferences prefs = ResultMonth.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String value = prefs.getString(key, " ");
                return value;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
}