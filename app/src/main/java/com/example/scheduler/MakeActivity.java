package com.example.scheduler;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MakeActivity extends AppCompatActivity {
    EditText title, content, time;
    ImageButton boxbutton, boxbutton2;
    ImageButton checkbutton, checkbutton2;
    Button register;
    String code, makedPer;
    String period, response, timeS;
    String titleS, contentS;
    AlertDialog dialog;
    JSONObject makeJSON = new JSONObject();
    int firstDate = 0;

    String URL = "http://59.18.221.32:5000/plan/make";

    CalendarView cal;
    TextView tv_text;

    int week;
    int monthtoday;
    int selectedweek;
    int daytoday;
    char date;

    int i = 0;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        Calendar calendar = Calendar.getInstance();

        week=calendar.get(Calendar.DAY_OF_WEEK);//요일
        daytoday=calendar.get(Calendar.DAY_OF_MONTH);//날짜
        monthtoday=calendar.get(Calendar.MONTH);//월
        cal = findViewById(R.id.calendarView);

        tv_text = findViewById(R.id.tv_text);

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        register = findViewById(R.id.btn_register);
        time = findViewById(R.id.get_time);


        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                int a=day-daytoday;
                if(monthtoday!=month){
                while(monthtoday!=month){
                    if (monthtoday == 0 || monthtoday == 2 || monthtoday == 4 || monthtoday == 6 || monthtoday == 7 || monthtoday == 9 || monthtoday == 11) {
                        int b=31;
                        a+=b;
                    }
                    else if(monthtoday==1){
                        int c=28;
                        a+=c;
                    }
                    else{
                        int d=30;
                        a+=d;
                    }
                    if(monthtoday==11){
                        monthtoday=0;
                    }
                    else{
                        monthtoday++;
                    }
                    }}

                    while (a >= 7) {
                        a = a - 7;
                    }
                selectedweek=(week+a)%7;
                if(selectedweek==0){
                    selectedweek=7;
                }
                switch(selectedweek){
                    case 1 :
                        date='일';
                        break;
                    case 2:
                        date='월';
                        break;
                    case 3 :
                        date='화';
                        break;
                    case 4:
                        date='수';
                        break;
                    case 5 :
                        date='목';
                        break;
                    case 6:
                        date='금';
                        break;
                    case 7:
                        date='토';
                        break;
                }
                String yearS = String.valueOf(year);
                String monthS;
                String dayS;
                if (month+1 < 10){
                    monthS = "0" + String.valueOf(month+1);
                } else {
                    monthS = String.valueOf(month+1);
                }
                if (day < 10){
                    dayS = "0" + String.valueOf(day);
                } else {
                    dayS = String.valueOf(day);
                }
                firstDate = Integer.parseInt(yearS+monthS+dayS) + date;

                tv_text.setText(year + "년 " + (month + 1) + "월 " + day + "일 " + date);
                week=calendar.get(Calendar.DAY_OF_WEEK);//요일
                daytoday=calendar.get(Calendar.DAY_OF_MONTH);//날짜
                monthtoday=calendar.get(Calendar.MONTH);
            }

        });

        AppCompatButton register = (AppCompatButton) findViewById(R.id.btn_register);
        register.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleS = title.getText().toString();
                timeS = time.getText().toString();
                if (titleS.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MakeActivity.this);
                    dialog = builder.setMessage("제목을 입력해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (i==1 && j==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MakeActivity.this);
                    dialog = builder.setMessage("기간을 하나만 체크해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (i==0 && j==0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MakeActivity.this);
                    dialog = builder.setMessage("기간을 체크해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (firstDate == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MakeActivity.this);
                    dialog = builder.setMessage("시작 날짜를 입력해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (i == 1) {
                    if (timeS.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MakeActivity.this);
                        dialog = builder.setMessage("약속 시간를 입력해주세요.").setPositiveButton("확인", null).create();
                        dialog.show();
                        return;
                    } else {
                        period = "week";
                    }
                } else if (j == 1){
                    if (timeS.equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MakeActivity.this);
                        dialog = builder.setMessage("약속 일수를 입력해주세요.").setPositiveButton("확인", null).create();
                        dialog.show();
                        return;
                    } else {
                        period = "month";
                    }
                }
                try {
                    System.out.println(timeS);
                    System.out.println(firstDate);
                    System.out.println(period);
                    System.out.println(timeS);
                    System.out.println(contentS);
                    makeJSON.put("title", titleS);
                    makeJSON.put("first_day", firstDate);
                    makeJSON.put("duration", period);
                    makeJSON.put("needed_time", timeS);
                    makeJSON.put("detail", contentS);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                sendRequest(makeJSON, URL);
            }
        }));

        boxbutton = (ImageButton) findViewById(R.id.checkbox1);
        checkbutton = (ImageButton) findViewById(R.id.check1);
        checkbutton.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);

        boxbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0) {
                    boxbutton.setVisibility(View.VISIBLE);
                    checkbutton.setVisibility(View.VISIBLE);
                    boxbutton2.setVisibility(View.VISIBLE);
                    checkbutton2.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.VISIBLE);
                    i = 1;
                    j = 0;
                } else {
                    boxbutton.setVisibility(View.VISIBLE);
                    checkbutton.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    i = 0;
                }
            }
        });

        boxbutton2 = (ImageButton) findViewById(R.id.checkbox2);
        checkbutton2= (ImageButton) findViewById(R.id.check2);
        checkbutton2.setVisibility(View.INVISIBLE);

        boxbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (j == 0) {
                    boxbutton2.setVisibility(View.VISIBLE);
                    checkbutton2.setVisibility(View.VISIBLE);
                    checkbutton.setVisibility(View.INVISIBLE);
                    checkbutton2.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    j = 1;
                    i = 0;
                } else {
                    boxbutton2.setVisibility(View.VISIBLE);
                    checkbutton2.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);
                    j = 0;
                }
            }
        });
    }
    public void sendRequest(JSONObject inputJSON, String URL){
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
                    CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MakeActivity.this));
                    String sessionid = getString("session");
                    List<Cookie> cookieList = cookieJar.loadForRequest(HttpUrl.parse(URL));
                    System.out.println(sessionid);
                    System.out.println(cookieList);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    RequestBody requestBody = RequestBody.create(
                            MediaType.parse("application/json; charset=uft-8"),
                            inputJSON.toString()
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
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        if (response.contains("month")){
                            Intent intent = new Intent(MakeActivity.this, MonthTimetable.class);
                            intent.putExtra("code", code);
                            intent.putExtra("period", makedPer);
                            startActivity(intent);
                        } else if (response.contains("week")){
                            Intent intent = new Intent(MakeActivity.this, MakeTimetable.class);
                            intent.putExtra("code", code);
                            intent.putExtra("period", makedPer);
                            startActivity(intent);
                        }
                    } else {
                        MakeActivity.this.runOnUiThread(new Runnable() {
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
                SharedPreferences prefs = MakeActivity.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String value = prefs.getString(key, " ");
                return value;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
}