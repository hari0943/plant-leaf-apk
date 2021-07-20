package com.example.leaf;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.counciloor_app.CircleTransform;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class custom_view_leaf extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c,d;


    public custom_view_leaf(android.content.Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d) {

        this.Context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;



    }

    @Override
    public int getCount() {

        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {


        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(Context);
            gridView=inflator.inflate(R.layout.activity_custom_leaf, null);
        }
        else
        {
            gridView=(View)convertview;
        }
        TextView tv1=(TextView)gridView.findViewById(R.id.name);
        TextView tv2=(TextView)gridView.findViewById(R.id.dis);
        TextView tv3=(TextView)gridView.findViewById(R.id.des);
        ImageView b9=(ImageView) gridView.findViewById(R.id.imageView2);
        try
        {
            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);
            String url1=sh.getString("url1","")+""+d.get(position);
            Picasso.with(Context).load(url1.toString()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).transform(new CircleTransform()).error(R.drawable.ic_menu_gallery).into(b9);

        }catch (Exception e)
        {
            Toast.makeText(Context, e.toString(), Toast.LENGTH_SHORT).show();
        }

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(a.get(position));
        tv2.setText(b.get(position));
        tv3.setText(c.get(position));

        return gridView;
    }

}


