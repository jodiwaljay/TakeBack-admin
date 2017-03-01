package com.elegance.takeback;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by root on 30/3/16.
 */
public class AddQuestion extends AppCompatActivity {

    public static String DATA_QUESTION;
    EditText etQuestion;
    CheckBox check1, check2, check3, check4;
    RadioButton radio1, radio2, radio3, radio4;
    LinearLayout ratPrev, singleChoicePrev, dichotPrev, checklistPrev, smileyPrev, addCheck1, addCheck2;
    RadioGroup addRadio;
    String chosen = "normal";
    Button btAddQuestion;
    int position ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);
        setUpVariables();

        if(Form_Creation.formEditMode.size()==10){
            Form_Creation.formEditMode.clear();
        }

        position = -1;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("TypeListPos");
        }

        if (position == -1) {
            finish();
        }

        if(position == 3){
            chosen = "singleChoice";
        }

        if(position == 4){
            chosen = "checkList";
        }

        removeAll();

        btAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addQuestion();
                Form_Creation.formEditMode.add(DATA_QUESTION);
                ChooseType.act.finish();
                finish();
            }
        });

    }

    public void removeAll(){
        ratPrev.setVisibility(View.GONE);
        singleChoicePrev.setVisibility(View.GONE);
        smileyPrev.setVisibility(View.GONE);
        dichotPrev.setVisibility(View.GONE);
        checklistPrev.setVisibility(View.GONE);

        addCheck1.setVisibility(View.GONE);
        addCheck2.setVisibility(View.GONE);
        addRadio.setVisibility(View.GONE);

        if(position == 0){
            ratPrev.setVisibility(View.VISIBLE);
        }
        if(position == 1){
            smileyPrev.setVisibility(View.VISIBLE);
        }
        if(position == 2){
            dichotPrev.setVisibility(View.VISIBLE);
        }
        if(position == 3){
            singleChoicePrev.setVisibility(View.VISIBLE);
            addRadio.setVisibility(View.VISIBLE);
        }
        if(position == 4){
            checklistPrev.setVisibility(View.VISIBLE);
            addCheck1.setVisibility(View.VISIBLE);
            addCheck2.setVisibility(View.VISIBLE);
        }
    }

    public void addQuestion() {
        DATA_QUESTION = getQId(position);
        DATA_QUESTION = DATA_QUESTION + (";") + etQuestion.getText().toString();
        if(chosen.equals("singleChoice")){
            DATA_QUESTION = DATA_QUESTION + ";" + radio1.getText().toString() + ";" +
                    radio2.getText().toString() + ";" +
                    radio3.getText().toString() + ";" +
                    radio4.getText().toString() ;
        }
        if(chosen.equals("checkList")){
            DATA_QUESTION = DATA_QUESTION + ";" + check1.getText().toString() + ";" +
                    check2.getText().toString() + ";" +
                    check3.getText().toString() + ";" +
                    check4.getText().toString() ;
        }
        Log.d("Data",DATA_QUESTION);
    }

    public String getQId(int pos){
        return getResources().getStringArray(R.array.QIds)[pos];
    }
    public void setUpVariables(){
        check1 = (CheckBox) findViewById(R.id.check1);
        check2 = (CheckBox) findViewById(R.id.check2);
        check3 = (CheckBox) findViewById(R.id.check3);
        check4 = (CheckBox) findViewById(R.id.check4);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
        etQuestion = (EditText)findViewById(R.id.etQuestion);
        btAddQuestion = (Button)findViewById(R.id.btAddQuestion);

        ratPrev = (LinearLayout)findViewById(R.id.ratPrev);
        singleChoicePrev = (LinearLayout)findViewById(R.id.singleChoicePrev);
        smileyPrev = (LinearLayout)findViewById(R.id.smileyPrev);
        dichotPrev = (LinearLayout)findViewById(R.id.dichotPrev);
        checklistPrev = (LinearLayout)findViewById(R.id.checklistPrev);

        addCheck1 = (LinearLayout)findViewById(R.id.addQCheck1);
        addCheck2 = (LinearLayout)findViewById(R.id.addQCheck2);

        addRadio = (RadioGroup)findViewById(R.id.addQRadioBut);
    }


    public void showPromptDialog(final View view) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);
        final String temp = "";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                retStringPrompt(view,userInput.getText().toString());

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    public void retStringPrompt(View view, String retStr){

        if(getQId(position).equals(getResources().getStringArray(R.array.QIds)[4])){
            ((CheckBox)view).setText(retStr);
        }

        if(getQId(position).equals(getResources().getStringArray(R.array.QIds)[3])){
            ((RadioButton)view).setText(retStr);
        }
    }

    public void buttonClicked(View v) {
        showPromptDialog(v);
    }
}

