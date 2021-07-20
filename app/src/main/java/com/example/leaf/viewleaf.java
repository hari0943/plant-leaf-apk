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

public class viewleaf extends AppCompatActivity {
    ListView lv;
    String url = "";
    SharedPreferences sh;
    public static ArrayList<String> name, photo, discreption, disease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewleaf);
        lv = (ListView) findViewById(R.id.reply);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "and_view_leafdisease";

        try {
            ArrayList<NameValuePair> param = new ArrayList<>();

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = parser.makeHttpRequest(url, "POST", param);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("1")) {

                JSONArray ja = jsonObject.getJSONArray("data");
                name = new ArrayList<>();
                photo = new ArrayList<>();
                discreption = new ArrayList<>();
                disease = new ArrayList<>();


                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    name.add(jo.getString("name"));
                    photo.add(jo.getString("phtoto"));
                    discreption.add(jo.getString("discreption"));
                    disease.add(jo.getString("disease"));


                }
                lv.setAdapter(new custom_view_leaf(getApplicationContext(),name,disease,discreption,photo));
            } else {
                Toast.makeText(this, "No leaf available", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}