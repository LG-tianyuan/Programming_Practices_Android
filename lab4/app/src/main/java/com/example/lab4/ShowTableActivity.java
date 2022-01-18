package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class ShowTableActivity extends AppCompatActivity {

    private RecyclerView RV;
    private MyAdapter AP;
    private List<Person> PersonList = new ArrayList<>();
    private String host;
    private String username;
    private String name;
    private String tel;
    private String age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_table);
        initview();
    }
    public void initview(){
        Intent intent=getIntent();
        host=intent.getStringExtra("host");
        String myurl = "http://106.66.32.109:8080/lab4_server/ShowTable";
        String tag = "ShowTable";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.cancelAll(tag);
        final StringRequest request = new StringRequest(Request.Method.POST, myurl,
                response -> {
                    try{
                        JSONArray result = (JSONArray) new JSONObject(response).get("list");
                        int len = result.length();
                        System.out.println(len);
                        for(int i=0;i<len;i++){
                            JSONObject value = result.getJSONObject(i);
                            username = value.getString("username");
                            name = value.getString("name");
                            tel = value.getString("tel");
                            age = value.getString("age");
                            Person person = new Person(username,name,age,tel);
                            PersonList.add(person);
                            RV=findViewById(R.id.recyclerview);
                            AP = new MyAdapter();
                            RV.setAdapter(AP);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ShowTableActivity.this);
                            RV.setLayoutManager(layoutManager);
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
                params.put("request","request");
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);
    }
    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ShowTableActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {
        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(ShowTableActivity.this, R.layout.item_list, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            Person person = PersonList.get(position);
            if(person.getUsername().equals(host)){
                holder.usernameTV.setText(person.getUsername());
                holder.nameTV.setText(person.getName());
                holder.telTV.setText(person.getTel());
            }
            else{
                holder.usernameTV.setText(person.getUsername());
                holder.nameTV.setText("***");
                holder.telTV.setText("***");
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    username = person.getUsername();
                    name = person.getName();
                    tel = person.getTel();
                    age = person.getAge();
                    Intent intent = new Intent(ShowTableActivity.this,ShowInfoActivity.class);
                    if(username.equals(host)){
                        intent.putExtra("host","ishost");
                        intent.putExtra("username",username);
                        intent.putExtra("name",name);
                        intent.putExtra("age",age);
                        intent.putExtra("tele",tel);
                    }else{
                        intent.putExtra("host","isnothost");
                        intent.putExtra("username",username);
                        intent.putExtra("name","***");
                        intent.putExtra("age","***");
                        intent.putExtra("tele","***");
                    }
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return PersonList.size();
        }
    }
    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView usernameTV;
        TextView nameTV;
        TextView telTV;
        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            usernameTV = itemView.findViewById(R.id.tv1);
            nameTV = itemView.findViewById(R.id.tv2);
            telTV = itemView.findViewById(R.id.tv3);
        }
    }
    public void goback(View view){
        //Intent intent = new Intent(ShowPersonListActivity.this,MainActivity.class);
        //startActivity(intent);
        ShowTableActivity.this.finish();
    }
}