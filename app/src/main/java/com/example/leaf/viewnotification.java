package com.example.leaf;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class viewnotification extends AppCompatActivity {
    ListView lv;
    String url="";
    SharedPreferences sh;
    public static ArrayList<String> date,subject,content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnotification);
        lv=(ListView)findViewById(R.id.reply);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"and_view_notification";

        try {
            ArrayList<NameValuePair> param=new ArrayList<>();

            JSONParser parser=new JSONParser();
            JSONObject jsonObject=parser.makeHttpRequest(url,"POST",param);
            String status=jsonObject.getString("status");
            if(status.equalsIgnoreCase("1")){

                JSONArray ja=jsonObject.getJSONArray("data");
                date=new ArrayList<>();
                subject=new ArrayList<>();
                content=new ArrayList<>();



                for (int i=0;i<ja.length();i++){
                    JSONObject jo=ja.getJSONObject(i);
                    date.add(jo.getString("date"));
                    subject.add(jo.getString("subject"));
                    content.add(jo.getString("content"));


                }
                lv.setAdapter(new custom_view_noti(getApplicationContext(),subject,date,content));
            }
            else{
                Toast.makeText(this, "No notification available", Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
