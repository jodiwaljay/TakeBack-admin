package com.elegance.takeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.StringTokenizer;

/**
 * Created by root on 2/4/16.
 */
public class checklistFragment extends Fragment {
    CheckBox check1, check2, check3, check4;
    String answer;
    Boolean flag = false;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.checklist_layout, container, false);

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


        check1 = (CheckBox) getView().findViewById(R.id.check1);
        check2 = (CheckBox) getView().findViewById(R.id.check2);
        check3 = (CheckBox) getView().findViewById(R.id.check3);
        check4 = (CheckBox) getView().findViewById(R.id.check4);

        check1.setText(stringTokenizer.nextToken());
        check2.setText(stringTokenizer.nextToken());
        check3.setText(stringTokenizer.nextToken());
        check4.setText(stringTokenizer.nextToken());

        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check1.setChecked(true);
                if (flag) {
                    Form_Creation.Questions_Loaded.remove(answer);
                }
                answer = textView.getText().toString();
                if(check1.isChecked()){
                    answer = answer + check1.getText().toString();
                }
                if(check2.isChecked()){
                    answer = answer + check2.getText().toString();
                }
                if(check3.isChecked()){
                    answer = answer + check3.getText().toString();
                }
                if(check4.isChecked()){
                    answer = answer + check4.getText().toString();
                }
                Form_Creation.Questions_Loaded.add(answer);
                flag = true;
                Log.d("Arraylist Answers", Form_Creation.Questions_Loaded.toString());
            }
        });

        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check2.setChecked(true);
                if (flag) {
                    Form_Creation.Questions_Loaded.remove(answer);
                }
                answer = textView.getText().toString();
                if(check1.isChecked()){
                    answer = answer + check1.getText().toString();
                }
                if(check2.isChecked()){
                    answer = answer + check2.getText().toString();
                }
                if(check3.isChecked()){
                    answer = answer + check3.getText().toString();
                }
                if(check4.isChecked()){
                    answer = answer + check4.getText().toString();
                }
                Form_Creation.Questions_Loaded.add(answer);
                flag = true;
            }
        });

        check3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Form_Creation.Questions_Loaded.remove(answer);
                }
                answer = textView.getText().toString();
                if(check1.isChecked()){
                    answer = answer + check1.getText().toString();
                }
                if(check2.isChecked()){
                    answer = answer + check2.getText().toString();
                }
                if(check3.isChecked()){
                    answer = answer + check3.getText().toString();
                }
                if(check4.isChecked()){
                    answer = answer + check4.getText().toString();
                }
                Form_Creation.Questions_Loaded.add(answer);
                flag = true;
            }
        });

        check4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Form_Creation.Questions_Loaded.remove(answer);
                }
                answer = textView.getText().toString();
                if(check1.isChecked()){
                    answer = answer + check1.getText().toString();
                }
                if(check2.isChecked()){
                    answer = answer + check2.getText().toString();
                }
                if(check3.isChecked()){
                    answer = answer + check3.getText().toString();
                }
                if(check4.isChecked()){
                    answer = answer + check4.getText().toString();
                }
                Form_Creation.Questions_Loaded.add(answer);
                flag = true;
            }
        });

    }

}
