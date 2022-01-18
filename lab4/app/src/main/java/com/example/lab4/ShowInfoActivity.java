package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowInfoActivity extends AppCompatActivity {
    private TextView usernameTV;
    private TextView nameTV;
    private TextView ageTV;
    private TextView teleTV;
    private Button BT;
    private String host;
    private String username;
    private String name;
    private String age;
    private String tele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        initview();
    }
    public void initview(){
        usernameTV = findViewById(R.id.username);
        nameTV = findViewById(R.id.name);
        ageTV = findViewById(R.id.age);
        teleTV = findViewById(R.id.telenumber);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        name=intent.getStringExtra("name");
        age=intent.getStringExtra("age");
        tele=intent.getStringExtra("tele");
        host=intent.getStringExtra("host");
        usernameTV.setText(username);
        nameTV.setText(name);
        ageTV.setText(age);
        teleTV.setText(tele);
        /*if(host.equals("isnothost")){
            BT = findViewById(R.id.button1);
            BT.setEnabled(false);
        }*/
    }

    /*public void UpdateInfo(View view){
        Intent intent = new Intent(ShowInfoActivity.this,UpdateInfoActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
        this.finish();
    }*/
    public  void GoBack(View view){
        //Intent intent = new Intent(ShowInfoActivity.this,WelcomeActivity.class);
        //startActivity(intent);
        this.finish();
    }
}