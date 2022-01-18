package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateInfoActivity extends AppCompatActivity {
    private TextView usernameTV;
    private EditText nameET;
    private EditText ageET;
    private EditText teleET;
    private TextView tip1;
    private TextView tip2;
    private TextView tip3;
    private String username;
    private String name;
    private String age;
    private String tele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        initview();
    }
    public void initview(){
        usernameTV = findViewById(R.id.username);
        nameET = findViewById(R.id.name);
        ageET = findViewById(R.id.age);
        teleET = findViewById(R.id.telenumber);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        usernameTV.setText(username);
        tip1 = findViewById(R.id.tip1);
        tip2 = findViewById(R.id.tip2);
        tip3 = findViewById(R.id.tip3);
        nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = nameET.getText().toString();
                if(name.isEmpty()){
                    tip1.setText("姓名不能为空!但不能超过20个字符");
                }else if(name.length()>20){
                    tip1.setText("姓名长度不能超过20个字符");
                }else{
                    tip1.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ageET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                age = ageET.getText().toString();
                if(!age.isEmpty()&&!age.matches("0|[1-9][0-9]*")){
                    tip2.setText("年龄必须为非负整数");
                }else{
                    tip2.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        teleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tele = teleET.getText().toString();
                if(!tele.isEmpty()&&!tele.matches("[1][3-9][0-9]{9}")){
                    tip3.setText("请输入有效的11位手机号");
                }else{
                    tip3.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UpdateInfoActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void UpdateInfo(View view){
        name = nameET.getText().toString();
        age = ageET.getText().toString();
        tele = teleET.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(UpdateInfoActivity.this,"姓名不能为空",Toast.LENGTH_SHORT).show();
        }else if(name.length()>20){
            Toast.makeText(UpdateInfoActivity.this,"姓名长度不能超过20个字符",Toast.LENGTH_SHORT).show();
        }else if(!age.isEmpty()&&!age.matches("0|[1-9][0-9]*")) {
            Toast.makeText(UpdateInfoActivity.this,"年龄必须为非负整数",Toast.LENGTH_SHORT).show();
        }else if(!tele.isEmpty()&&!tele.matches("[0-9]{11}")) {
            Toast.makeText(UpdateInfoActivity.this,"电话号码应为11位数字",Toast.LENGTH_SHORT).show();
        }else{
            toast("Update...");
            UpdateRequest(username,name,age,tele);
        }
    }
    public void Goback(View view){
        //Intent intent = new Intent(UpdateInfoActivity.this,WelcomeActivity.class);
        //startActivity(intent);
        this.finish();
    }
    public void UpdateRequest(String username,String name,String age,String tele){
        String myurl = "http://106.66.32.109:8080/lab4_server/UpdateInfo";
        String tag = "UpdateInfo";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.cancelAll(tag);
        final StringRequest request = new StringRequest(Request.Method.POST, myurl,
                response -> {
                    try{
                        JSONObject jsonObject = (JSONObject)  new JSONObject(response).get("params");
                        String result = jsonObject.getString("result");
                        if(result.equals("Success!")){
                            Success();
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
                params.put("name",name);
                params.put("age",age);
                params.put("telenumber",tele);
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
    }
    public void Success(){
        //Intent intent = new Intent(UpdateInfoActivity.this,WelcomeActivity.class);
        //intent.putExtra("username",username);
        //startActivity(intent);
        this.finish();
    }
}