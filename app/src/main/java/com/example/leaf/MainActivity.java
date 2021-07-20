package com.example.leaf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    Button bt;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=(EditText)findViewById(R.id.editText);
        bt=(Button)findViewById(R.id.button2);



        bt.setOnClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        et.setText(sh.getString("ip",""));
    }

    @Override
    public void onClick(View view)
    {
        String ipad=et.getText().toString();
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("ip",ipad);
        ed.putString("url","http://"+ipad+":5000/");
        ed.putString("url1","http://"+ipad+":5000");
        ed.commit();
        startActivity(new Intent(MainActivity.this,Login.class));

    }
}
