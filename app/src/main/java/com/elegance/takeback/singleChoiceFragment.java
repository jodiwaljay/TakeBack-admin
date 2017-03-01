package com.elegance.takeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.StringTokenizer;

/**
 * Created by root on 2/4/16.
 */
public class singleChoiceFragment extends Fragment {

    RadioButton radio1, radio2, radio3, radio4;
    String answer;
    Boolean flag = false;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.single_choice_layout, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String strtext = getArguments().getString("Question");
        Log.d("dtat", strtext);

        StringTokenizer stringTokenizer = new StringTokenizer(strtext);
        stringTokenizer.nextToken(";");

        textView = (TextView) getView().findViewById(R.id.test);
        textView.setText(stringTokenizer.nextToken());


        radio1 = (RadioButton) getView().findViewById(R.id.radio1);
        radio2 = (RadioButton) getView().findViewById(R.id.radio2);
        radio3 = (RadioButton) getView().findViewById(R.id.radio3);
        radio4 = (RadioButton) getView().findViewById(R.id.radio4);

        radio1.setText(stringTokenizer.nextToken());
        radio2.setText(stringTokenizer.nextToken());
        radio3.setText(stringTokenizer.nextToken());
        radio4.setText(stringTokenizer.nextToken());

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearRadio();
                radio1.setChecked(true);
                if(flag){
                    Form_Creation.Questions_Loaded.remove(answer);
                }
                answer = textView.getText().toString() + ";" + radio1.getText();
                Form_Creation.Questions_Loaded.add(answer);
                flag = true;
                Log.d("Arraylist Answers", Form_Creation.Questions_Loaded.toString());
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearRadio();
                radio2.setChecked(true);
                if(flag){
                    Form_Creation.Questions_Loaded.remove(answer);
                }
                answer = textView.getText().toString() + ";" + radio2.getText();
                Form_Creation.Questions_Loaded.add(answer);
                flag = true;
            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearRadio();
                radio3.setChecked(true);
                if(flag){
                    Form_Creation.Questions_Loaded.remove(answer);
                }
                answer = textView.getText().toString() + ";" + radio3.getText();
                Form_Creation.Questions_Loaded.add(answer);
                flag = true;
            }
        });

        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearRadio();
                radio4.setChecked(true);
                if(flag){
                    Form_Creation.Questions_Loaded.remove(answer);
                }
                answer = textView.getText().toString() + ";" + radio4.getText();
                Form_Creation.Questions_Loaded.add(answer);
                flag = true;
            }
        });
    }

    public void ClearRadio(){
        radio1.setChecked(false);
        radio2.setChecked(false);
        radio3.setChecked(false);
        radio4.setChecked(false);

    }

}