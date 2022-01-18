package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    private TextView wc;
    private String username;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initview();
    }
    public void initview(){
        wc =findViewById(R.id.welcomeusr);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        name = intent.getStringExtra("name");
        wc.setText("Welcome "+username);
        MainActivity.instance.SetText();
    }
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WelcomeActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ShowPersonalInfo(View view){
        String myurl = "http://106.66.32.109:8080/lab4_server/ShowInfo";
        String tag = "ShowInfo";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.cancelAll(tag);
        final StringRequest request = new StringRequest(Request.Method.POST, myurl,
                response -> {
                    try{
                        JSONObject jsonObject = (JSONObject)  new JSONObject(response).get("params");
                        String username = jsonObject.getString("username");
                        String name = jsonObject.getString("name");
                        String age = jsonObject.getString("age");
                        String tele = jsonObject.getString("tele");
                        Intent intent = new Intent(WelcomeActivity.this,ShowInfoActivity.class);
                        intent.putExtra("username",username);
                        intent.putExtra("name",name);
                        intent.putExtra("age",age);
                        intent.putExtra("tele",tele);
                        intent.putExtra("host","ishodt");
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("TAG",e.getMessage(),e);
                    }
                }, error -> {
            toast("请求失败！");
            Log.e("TAG",error.getMessage(),error);
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
    }
    public void UpdateIF(View view){
        Intent intent = new Intent(WelcomeActivity.this,UpdateInfoActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void ResetPW(View view){
        Intent intent = new Intent(WelcomeActivity.this,ResetPasswordActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }
    public void ShowAllInfo(View view){
        Intent intent = new Intent(WelcomeActivity.this,ShowTableActivity.class);
        intent.putExtra("host",username);
        startActivity(intent);
    }
    public void Loginout(View view){
        /*Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);*/
        this.finish();
    }
    public void DestroyUser(View view){
        String myurl = "http://106.66.32.109:8080/lab4_server/DeletePerson";
        String tag = "DeletePerson";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.cancelAll(tag);
        final StringRequest request = new StringRequest(Request.Method.POST, myurl,
                response -> {
                    try{
                        JSONObject jsonObject = (JSONObject)  new JSONObject(response).get("params");
                        String result = jsonObject.getString("result");
                        if(result.equals("Success!")){
                            this.finish();
                        }else{
                            toast(result);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("TAG",e.getMessage(),e);
                    }
                }, error -> {
            toast("请求失败！");
            Log.e("TAG",error.getMessage(),error);
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
    }
}