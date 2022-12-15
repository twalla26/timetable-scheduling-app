package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.ViewCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MakeTimetable extends AppCompatActivity {
    TextView title, content;

    Button sunday0,sunday1,sunday2,sunday3,sunday4,sunday5,sunday6,sunday7,sunday8,sunday9,sunday10,sunday11,sunday12,sunday13,sunday14,sunday15,sunday16,sunday17,sunday18,sunday19,sunday20,sunday21,sunday22,sunday23;
    Button monday0,monday1,monday2,monday3,monday4,monday5,monday6,monday7,monday8,monday9,monday10,monday11,monday12,monday13,monday14,monday15,monday16,monday17,monday18,monday19,monday20,monday21,monday22,monday23;
    Button tuesday0,tuesday1,tuesday2,tuesday3,tuesday4,tuesday5,tuesday6,tuesday7,tuesday8,tuesday9,tuesday10, tuesday11,tuesday12,tuesday13,tuesday14,tuesday15,tuesday16,tuesday17,tuesday18,tuesday19,tuesday20,tuesday21,tuesday22,tuesday23;
    Button wednesday0,wednesday1,wednesday2,wednesday3,wednesday4,wednesday5,wednesday6,wednesday7,wednesday8,wednesday9,wednesday10,wednesday11,wednesday12,wednesday13,wednesday14,wednesday15,wednesday16,wednesday17,wednesday18,wednesday19,wednesday20,wednesday21,wednesday22,wednesday23;
    Button thursday0,thursday1,thursday2,thursday3,thursday4,thursday5,thursday6,thursday7,thursday8,thursday9,thursday10,thursday11,thursday12,thursday13,thursday14,thursday15,thursday16,thursday17,thursday18,thursday19,thursday20,thursday21,thursday22,thursday23;
    Button friday0,friday1,friday2,friday3,friday4,friday5,friday6,friday7,friday8,friday9,friday10,friday11,friday12,friday13,friday14,friday15,friday16,friday17,friday18,friday19,friday20,friday21,friday22,friday23;
    Button saturday0,saturday1,saturday2,saturday3,saturday4,saturday5,saturday6,saturday7,saturday8,saturday9,saturday10,saturday11,saturday12,saturday13,saturday14,saturday15,saturday16,saturday17,saturday18,saturday19,saturday20,saturday21,saturday22,saturday23;

    Button save;

    double[] sun = new double[24];
    double[] mon = new double[24];
    double[] tue = new double[24];
    double[] wed = new double[24];
    double[] thu = new double[24];
    double[] fri = new double[24];
    double[] sat = new double[24];

    double[][] forSend = new double[7][24];

    int colored = 0;
    int firstDay;
    AppCompatButton btn_ambiguous, btn_possible, btn_delete;

    String response;
    String code, URL;

    JSONObject inputJSON = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_timetable);

        title = findViewById(R.id.title1);
        content = findViewById(R.id.detail_info);

        Intent intent = getIntent();
        code = intent.getExtras().getString("code");
        firstDay = intent.getExtras().getInt("firstDay");

        URL = "http://59.18.221.32:5000/plan/timetable/" + code;
        sendRequest(URL);

        btn_ambiguous=(AppCompatButton) findViewById(R.id.btn_ambiguous);
        btn_possible=(AppCompatButton) findViewById(R.id.btn_possible);
        btn_delete=(AppCompatButton) findViewById(R.id.btn_delete);

        Drawable.ConstantState poss = getResources().getDrawable(R.drawable.cell_shape_possible).getConstantState();
        Drawable.ConstantState ambi = getResources().getDrawable(R.drawable.cell_shape_ambiguous).getConstantState();
        Drawable.ConstantState impo =  getResources().getDrawable(R.drawable.cell_shape1).getConstantState();

        save = findViewById(R.id.btn_save);

        btn_ambiguous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colored=2;
            }
        });

        btn_possible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colored=3;
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colored=1;
            }
        });


        Button btn_a[]={sunday0,sunday1,sunday2,sunday3,sunday4,sunday5,sunday6,sunday7,sunday8,sunday9,sunday10,sunday11,sunday12,sunday13,sunday14,sunday15,sunday16,sunday17,sunday18,sunday19,sunday20,sunday21,sunday22,sunday23};
        Button btn_b[]={monday0,monday1,monday2,monday3,monday4,monday5,monday6,monday7,monday8,monday9,monday10,monday11,monday12,monday13,monday14,monday15,monday16,monday17,monday18,monday19,monday20,monday21,monday22,monday23};
        Button btn_c[]={tuesday0,tuesday1,tuesday2,tuesday3,tuesday4,tuesday5,tuesday6,tuesday7,tuesday8,tuesday9,tuesday10, tuesday11,tuesday12,tuesday13,tuesday14,tuesday15,tuesday16,tuesday17,tuesday18,tuesday19,tuesday20,tuesday21,tuesday22,tuesday23};
        Button btn_d[]={wednesday0,wednesday1,wednesday2,wednesday3,wednesday4,wednesday5,wednesday6,wednesday7,wednesday8,wednesday9,wednesday10,wednesday11,wednesday12,wednesday13,wednesday14,wednesday15,wednesday16,wednesday17,wednesday18,wednesday19,wednesday20,wednesday21,wednesday22,wednesday23};
        Button btn_e[]={thursday0,thursday1,thursday2,thursday3,thursday4,thursday5,thursday6,thursday7,thursday8,thursday9,thursday10,thursday11,thursday12,thursday13,thursday14,thursday15,thursday16,thursday17,thursday18,thursday19,thursday20,thursday21,thursday22,thursday23};
        Button btn_f[]={friday0,friday1,friday2,friday3,friday4,friday5,friday6,friday7,friday8,friday9,friday10,friday11,friday12,friday13,friday14,friday15,friday16,friday17,friday18,friday19,friday20,friday21,friday22,friday23};
        Button btn_g[]={saturday0,saturday1,saturday2,saturday3,saturday4,saturday5,saturday6,saturday7,saturday8,saturday9,saturday10,saturday11,saturday12,saturday13,saturday14,saturday15,saturday16,saturday17,saturday18,saturday19,saturday20,saturday21,saturday22,saturday23};


        int a[]={R.id.sunday0,R.id.sunday1,R.id.sunday2,R.id.sunday3,R.id.sunday4,R.id.sunday5,R.id.sunday6,R.id.sunday7,R.id.sunday8,R.id.sunday9,R.id.sunday10,R.id.sunday11,R.id.sunday12,R.id.sunday13,R.id.sunday14,R.id.sunday15,R.id.sunday16,R.id.sunday17,R.id.sunday18,R.id.sunday19,R.id.sunday20,R.id.sunday21,R.id.sunday22,R.id.sunday23};
        int b[]={R.id.monday0,R.id.monday1,R.id.monday2,R.id.monday3,R.id.monday4, R.id.monday5,R.id.monday6,R.id.monday7,R.id.monday8,R.id.monday9,R.id.monday10, R.id.monday11,R.id.monday12,R.id.monday13,R.id.monday14,R.id.monday15,R.id.monday16, R.id.monday17,R.id.monday18,R.id.monday19,R.id.monday20,R.id.monday21,R.id.monday22, R.id.monday23};
        int c[]={R.id.tuesday0,R.id.tuesday1,R.id.tuesday2,R.id.tuesday3,R.id.tuesday4,R.id.tuesday5,R.id.tuesday6,R.id.tuesday7,R.id.tuesday8,R.id.tuesday9,R.id.tuesday10,R.id.tuesday11,R.id.tuesday12,R.id.tuesday13,R.id.tuesday14,R.id.tuesday15,R.id.tuesday16,R.id.tuesday17,R.id.tuesday18,R.id.tuesday19,R.id.tuesday20,R.id.tuesday21,R.id.tuesday22,R.id.tuesday23};
        int d[]={R.id.wednesday0,R.id.wednesday1,R.id.wednesday2,R.id.wednesday3,R.id.wednesday4, R.id.wednesday5,R.id.wednesday6,R.id.wednesday7,R.id.wednesday8,R.id.wednesday9,R.id.wednesday10, R.id.wednesday11,R.id.wednesday12,R.id.wednesday13,R.id.wednesday14,R.id.wednesday15,R.id.wednesday16, R.id.wednesday17,R.id.wednesday18,R.id.wednesday19,R.id.wednesday20,R.id.wednesday21,R.id.wednesday22, R.id.wednesday23};
        int e[]={R.id.thursday0,R.id.thursday1,R.id.thursday2,R.id.thursday3,R.id.thursday4, R.id.thursday5,R.id.thursday6,R.id.thursday7,R.id.thursday8,R.id.thursday9,R.id.thursday10, R.id.thursday11,R.id.thursday12,R.id.thursday13,R.id.thursday14,R.id.thursday15,R.id.thursday16, R.id.thursday17,R.id.thursday18,R.id.thursday19,R.id.thursday20,R.id.thursday21,R.id.thursday22, R.id.thursday23};
        int f[]={R.id.friday0,R.id.friday1,R.id.friday2,R.id.friday3,R.id.friday4, R.id.friday5,R.id.friday6,R.id.friday7,R.id.friday8,R.id.friday9,R.id.friday10, R.id.friday11,R.id.friday12,R.id.friday13,R.id.friday14,R.id.friday15,R.id.friday16, R.id.friday17,R.id.friday18,R.id.friday19,R.id.friday20,R.id.friday21,R.id.friday22, R.id.friday23};
        int g[]={R.id.saturday0,R.id.saturday1,R.id.saturday2,R.id.saturday3,R.id.saturday4, R.id.saturday5,R.id.saturday6,R.id.saturday7,R.id.saturday8,R.id.saturday9,R.id.saturday10, R.id.saturday11,R.id.saturday12,R.id.saturday13,R.id.saturday14,R.id.saturday15,R.id.saturday16, R.id.saturday17,R.id.saturday18,R.id.saturday19,R.id.saturday20,R.id.saturday21,R.id.saturday22, R.id.saturday23};



        for(int i=0; i<24;i++){
            btn_a[i] = (Button) findViewById(a[i]);
            btn_b[i] = (Button) findViewById(b[i]);
            btn_c[i] = (Button) findViewById(c[i]);
            btn_d[i] = (Button) findViewById(d[i]);
            btn_e[i] = (Button) findViewById(e[i]);
            btn_f[i] = (Button) findViewById(f[i]);
            btn_g[i] = (Button) findViewById(g[i]);

            int finalI = i;
            btn_a[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_a[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_a[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_a[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });
            btn_b[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_b[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_b[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_b[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });
            btn_c[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_c[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_c[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_c[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });

            btn_d[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_d[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_d[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_d[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });

            btn_e[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_e[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_e[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_e[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });

            btn_f[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_f[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_f[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_f[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });

            btn_g[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(colored==2)
                        btn_g[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_ambiguous));
                    else if(colored==3)
                        btn_g[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape_possible));
                    else if(colored==1)
                        btn_g[finalI].setBackground(getResources().getDrawable(R.drawable.cell_shape1));
                }
            });
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<24; i++){
                    Drawable.ConstantState state_a = btn_a[i].getBackground().getConstantState();
                    if (state_a.equals(poss)){
                        sun[i] = 1;
                    } else if (state_a.equals(ambi)){
                        sun[i] = 0.5;
                    } else if (state_a.equals(impo)){
                        sun[i] = 0;
                    }
                    Drawable.ConstantState state_b = btn_b[i].getBackground().getConstantState();
                    if (state_b.equals(poss)){
                        mon[i] = 1;
                    } else if (state_b.equals(ambi)){
                        mon[i] = 0.5;
                    } else if (state_b.equals(impo)){
                        mon[i] = 0;
                    }
                    Drawable.ConstantState state_c = btn_c[i].getBackground().getConstantState();
                    if (state_c.equals(poss)){
                        tue[i] = 1;
                    } else if (state_c.equals(ambi)){
                        tue[i] = 0.5;
                    } else if (state_c.equals(impo)){
                        tue[i] = 0;
                    }
                    Drawable.ConstantState state_d = btn_d[i].getBackground().getConstantState();
                    if (state_d.equals(poss)){
                        wed[i] = 1;
                    } else if (state_d.equals(ambi)){
                        wed[i] = 0.5;
                    } else if (state_d.equals(impo)){
                        wed[i] = 0;
                    }
                    Drawable.ConstantState state_e = btn_e[i].getBackground().getConstantState();
                    if (state_e.equals(poss)){
                        thu[i] = 1;
                    } else if (state_e.equals(ambi)){
                        thu[i] = 0.5;
                    } else if (state_e.equals(impo)){
                        thu[i] = 0;
                    }
                    Drawable.ConstantState state_f = btn_f[i].getBackground().getConstantState();
                    if (state_f.equals(poss)){
                        fri[i] = 1;
                    } else if (state_f.equals(ambi)){
                        fri[i] = 0.5;
                    } else if (state_f.equals(impo)){
                        fri[i] = 0;
                    }
                    Drawable.ConstantState state_g = btn_g[i].getBackground().getConstantState();
                    if (state_g.equals(poss)){
                        sat[i] = 1;
                    } else if (state_g.equals(ambi)){
                        sat[i] = 0.5;
                    } else if (state_g.equals(impo)){
                        sat[i] = 0;
                    }
                }
                forSend[0] = mon;
                forSend[1] = tue;
                forSend[2] = wed;
                forSend[3] = thu;
                forSend[4] = fri;
                forSend[5] = sat;
                forSend[6] = sun;
                System.out.println(Arrays.deepToString(forSend));

                try{
                    inputJSON.put("timetable_array", forSend);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                sendRequestResult(inputJSON, URL);
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
                    CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MakeTimetable.this));
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
                    String titleS = jsonObject.getString("title");
                    String contentS = jsonObject.getString("detail");

                    MakeTimetable.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            title.setText(String.valueOf(titleS));
                            content.setText(contentS);
                        }
                    });

                    setString("firstDay", startDay);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            private void setString(String key, int value){
                SharedPreferences prefs = MakeTimetable.this.getSharedPreferences("firstDay", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(key, value);
                editor.commit();
            }
            public String getString(String key) {
                SharedPreferences prefs = MakeTimetable.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String value = prefs.getString(key, " ");
                return value;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
    public void sendRequestResult(JSONObject jsonObject, String URL){
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
                    CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MakeTimetable.this));
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
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(MakeTimetable.this, ResultTime.class);
                        intent.putExtra("code", code);
                        startActivity(intent);
                    } else {
                        MakeTimetable.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "오류 발생", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public String getString(String key) {
                SharedPreferences prefs = MakeTimetable.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String value = prefs.getString(key, " ");
                return value;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
}