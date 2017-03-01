package com.elegance.takeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.StringTokenizer;

/**
 * Created by root on 2/4/16.
 */

public class smileyFragment extends Fragment {
    String answer;
    Boolean flag = false;
    TextView tvExc, tvGood, tvAvg, tvPoor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.smiley_layout, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String strtext = getArguments().getString("Question");
        Log.d("dtat", strtext);


        if(strtext!=null || getView()!=null) {
            StringTokenizer stringTokenizer = new StringTokenizer(strtext);
            stringTokenizer.nextToken(";");

            final TextView textView = (TextView) getView().findViewById(R.id.test);
            textView.setText(stringTokenizer.nextToken());

            ImageButton bExcel = (ImageButton) getView().findViewById(R.id.bExcellent);
            ImageButton bGood = (ImageButton) getView().findViewById(R.id.bGood);
            ImageButton bAverage = (ImageButton) getView().findViewById(R.id.bAverage);
            ImageButton bPoor = (ImageButton) getView().findViewById(R.id.bPoor);

            tvExc = (TextView) getView().findViewById(R.id.tvExcellent);
            tvGood = (TextView) getView().findViewById(R.id.tvGood);
            tvAvg = (TextView) getView().findViewById(R.id.tvAverage);
            tvPoor = (TextView) getView().findViewById(R.id.tvPoor);


            bExcel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag){
                        Form_Creation.Questions_Loaded.remove(answer);
                    }
                    answer = textView.getText().toString() + ";" + "Excellent";
                    Form_Creation.Questions_Loaded.add(answer);
                    flag = true;
                    tvExc.setVisibility(View.VISIBLE);
                    tvGood.setVisibility(View.INVISIBLE);
                    tvAvg.setVisibility(View.INVISIBLE);
                    tvPoor.setVisibility(View.INVISIBLE);
                }
            });

            bGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag){
                        Form_Creation.Questions_Loaded.remove(answer);
                    }
                    answer = textView.getText().toString() + ";" + "Good";
                    Form_Creation.Questions_Loaded.add(answer);
                    flag = true;
                    tvExc.setVisibility(View.INVISIBLE);
                    tvGood.setVisibility(View.VISIBLE);
                    tvAvg.setVisibility(View.INVISIBLE);
                    tvPoor.setVisibility(View.INVISIBLE);
                }
            });

            bAverage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag){
                        Form_Creation.Questions_Loaded.remove(answer);
                    }
                    answer = textView.getText().toString() + ";" + "Average";
                    Form_Creation.Questions_Loaded.add(answer);
                    flag = true;
                    tvExc.setVisibility(View.INVISIBLE);
                    tvGood.setVisibility(View.INVISIBLE);
                    tvAvg.setVisibility(View.VISIBLE);
                    tvPoor.setVisibility(View.INVISIBLE);
                }
            });

            bPoor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag){
                        Form_Creation.Questions_Loaded.remove(answer);
                    }
                    answer = textView.getText().toString() + ";" + "Poor";
                    Form_Creation.Questions_Loaded.add(answer);
                    flag = true;
                    tvExc.setVisibility(View.INVISIBLE);
                    tvGood.setVisibility(View.INVISIBLE);
                    tvAvg.setVisibility(View.INVISIBLE);
                    tvPoor.setVisibility(View.VISIBLE);
                }
            });


        }
    }
}