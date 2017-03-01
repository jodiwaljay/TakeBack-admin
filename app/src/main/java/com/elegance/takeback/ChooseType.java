package com.elegance.takeback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by root on 30/3/16.
 */
public class ChooseType extends AppCompatActivity {
    public static Activity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_type);
        act = this;

        ArrayList<String> types = new ArrayList<String>();
        for(String temp: getResources().getStringArray(R.array.string_array_name)){
            types.add(temp);
        }
        ListView l1 = (ListView)findViewById(R.id.listChoose);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,types);
        l1.setAdapter(myAdapter);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Intent i = new Intent(getApplicationContext(), AddQuestion.class);
                    i.putExtra("TypeListPos",position);
                    startActivity(i);
                }

        });
    }
}