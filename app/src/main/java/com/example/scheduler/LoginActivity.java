package com.example.scheduler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;


import java.io.IOException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private Button loginbtn;
    private EditText userID, password;
    private String URL, userid, _password;
    static private String response;
    private AlertDialog dialog;
    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userID = findViewById(R.id.et_id);
        password = findViewById(R.id.et_pass);

        URL = "http://59.18.221.32:5000/auth/login";

        AppCompatButton btn_register = (AppCompatButton) findViewById(R.id.btn_register);
        btn_register.setOnClickListener((new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent); //액티비티 이동
            }
        }));

        loginbtn = findViewById(R.id.btn_login);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid = userID.getText().toString();
                _password = password.getText().toString();
                if (userid.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("아이디를 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (password.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("비밀번호를 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                try {
                    jsonObject.put("user_id", userid);
                    jsonObject.put("password", _password);
                } catch (JSONException e){
                    e.printStackTrace();
                }
                sendRequest(jsonObject, URL);
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
                    PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(LoginActivity.this));
                    List<Cookie> cookieList = cookieJar.loadForRequest(HttpUrl.parse(URL));

                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    RequestBody requestBody = RequestBody.create(
                            MediaType.parse("application/json; charset=uft-8"),
                            inputJSON.toString()
                    );
                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(URL)
                            .build();
                    Response responses = null;
                    responses = client.newCall(request).execute();
                    String res_cookie = responses.headers().get("Set-Cookie");
                    System.out.println(res_cookie);

                    response = responses.body().string();
                    System.out.println(response);

                    String id = res_cookie.split("=")[0];
                    String value = res_cookie.split("=")[1].toString().split(";")[0];
                    String session = id + "=" + value;

                    System.out.println(id);
                    System.out.println(value);

                    Cookie cookie = new Cookie.Builder()
                            .name(id)
                            .value(value)
                            .domain("39.124.122.32")
                            .path("/auth/checkSession/")
                            .build();
                    cookieList.add(cookie);
                    System.out.println(cookieList);


                    setString(id, session);
                    String check = getString(id);
                    System.out.println(check);

                    if (response.contains("password")){
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.contains("wrong ID")) {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.contains("success")){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            private void setString(String key, String value){
                SharedPreferences prefs = LoginActivity.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(key, value);
                editor.commit();
            }
            public String getString(String key) {
                SharedPreferences prefs = LoginActivity.this.getSharedPreferences("session", Context.MODE_PRIVATE);
                String value = prefs.getString(key, " ");
                return value;
            }
        }
        sendData sendData = new sendData();
        sendData.execute();
    }
}
