package com.elegance.takeback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/4/16.
 */

public class Form_Creation extends AppCompatActivity {

    public static ArrayList<String> formEditMode = new ArrayList<String>();
    public static ArrayList<String> Questions_Loaded= new ArrayList<String>();
    public static ArrayList<String> User_Details= new ArrayList<String>();
    public static ArrayList<String> Comments= new ArrayList<String>();
    public static ArrayList<String> companyLoaded= new ArrayList<String>();
    public static Activity form_create;


    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();

    // url to create new product
    private static String url_create_product;
    private static String url_change_pin;
    public static ArrayList<String> companyDetails= new ArrayList<String>();
    public static ArrayList<String> form_unique= new ArrayList<String>();

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    Button bSurveyOne;
    Button bSurveyTwo;
    Button bSurveyThree;

    Button bCreateCompany;

    LinearLayout layout_form;

    boolean flag_one = false;
    boolean flag_two = false;
    boolean flag_three = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_creating);

        url_create_product = getResources().getString(R.string.create_url);
        url_change_pin = getResources().getString(R.string.pin_url);

        /*formEditMode = new ArrayList<String>();
        companyDetails = new ArrayList<String>();
        form_unique = new ArrayList<String>(1);
        Questions_Loaded = new ArrayList<String>();
        User_Details = new ArrayList<String>();
        Comments = new ArrayList<String>();
        companyLoaded = new ArrayList<String>();*/

        form_create = this;

        layout_form = (LinearLayout)findViewById(R.id.layout_form);
        final Spinner sCompany = (Spinner)findViewById(R.id.spinnerCompany);
        final Button save = (Button)findViewById(R.id.saveForm);
        final EditText edPin = (EditText)findViewById(R.id.edPin);
        final Button generatePin = (Button)findViewById(R.id.GeneratePin);
        final EditText edUnique = (EditText)findViewById(R.id.edUnique);
        final Button prev = (Button)findViewById(R.id.prev);
        final Button bLoadForm = (Button)findViewById(R.id.bLoadForm);
        final Button fab = (Button) findViewById(R.id.fabForm);
        final FloatingActionButton bCreateCompany = (FloatingActionButton)findViewById(R.id.bCreateCompany);
        layout_form.setVisibility(View.GONE);



        bSurveyOne = (Button)findViewById(R.id.bSurveyOne);
        bSurveyTwo = (Button)findViewById(R.id.bSurveyTwo);
        bSurveyThree = (Button)findViewById(R.id.bSurveyThree);


        bSurveyOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_one){
                    layout_form.setVisibility(View.GONE);
                    flag_one = false;
                    flag_two = false;
                    flag_three = false;
                }
                else {
                    layout_form.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.GONE);
                    sCompany.setVisibility(View.GONE);
                    save.setVisibility(View.GONE);
                    edUnique.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.GONE);

                    bLoadForm.setVisibility(View.GONE);
                    edPin.setVisibility(View.VISIBLE);
                    generatePin.setVisibility(View.VISIBLE);

                    flag_one = true;
                    flag_two = false;
                    flag_three = false;
                }
            }
        });

        bSurveyTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_two){
                    layout_form.setVisibility(View.GONE);
                    flag_one = false;
                    flag_two = false;
                    flag_three = false;


                }
                else {
                    layout_form.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    sCompany.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    edUnique.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);

                    bLoadForm.setVisibility(View.GONE);
                    edPin.setVisibility(View.GONE);
                    generatePin.setVisibility(View.GONE);

                    flag_one = false;
                    flag_two = true;
                    flag_three = false;
                }
            }
        });

        bSurveyThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag_three){
                    layout_form.setVisibility(View.GONE);
                    flag_one = false;
                    flag_two = false;
                    flag_three = false;

                }
                else {
                    layout_form.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.GONE);
                    sCompany.setVisibility(View.GONE);
                    save.setVisibility(View.GONE);
                    edUnique.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);

                    bLoadForm.setVisibility(View.VISIBLE);
                    edPin.setVisibility(View.VISIBLE);
                    generatePin.setVisibility(View.GONE);

                    flag_one = false;
                    flag_two = false;
                    flag_three = true;
                }
            }
        });

        bCreateCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AskCompanyDetails.class);
                startActivity(i);

            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),preview.class);
                startActivity(i);

            }
        });

        bLoadForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Load_Form.class);
                if(edPin.getText().toString().equals("")){
                    i.putExtra("pin","DEFAULT");
                }
                else {
                    i.putExtra("pin", edPin.getText().toString());
                }
                startActivity(i);
            }
        });
        // Creating adapter for spinner
        try {
            sql_backend sb = new sql_backend(Form_Creation.this);
            sb.open();
            Log.e("SQLException", "Empty Database");
            if (sb.isEmpty()) {
                Intent i = new Intent(getApplicationContext(), AskCompanyDetails.class);
                Log.e("SQLException", "Empty Database");
                finish();
                startActivity(i);
            }
        }
        catch (SQLException e){
            Log.e("SQLException", e.toString());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fetchCompanies() );

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sCompany.setAdapter(dataAdapter);
        sCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Clicked Item", fetchCompanies().get(position));
                companyDetails = fetchDetailsCompany(fetchCompanies().get(position));
                Log.d("Company Details", companyDetails.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edUnique.getText().toString().length()==5) {
                    form_unique = new ArrayList<String>();
                    form_unique.add(edUnique.getText().toString());
                    ArrayList<String> questions = new ArrayList<String>();
                    Log.d("formSize", "" + formEditMode.size());
                    questions.addAll(formEditMode);
                    for (int i = formEditMode.size(); i < 10; i++) {
                        questions.add(getResources().getStringArray(R.array.numbers)[i] + ";" + getResources().getStringArray(R.array.numbers)[i]);
                    }
                    edUnique.setText("");
                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edUnique.getWindowToken(), 0);
                    new CreateNewProduct(form_unique, companyDetails, questions).execute();


                }

                else {
                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edUnique.getWindowToken(), 0);


                    Toast.makeText(getApplicationContext(), "Unique id can be of 5 digit only",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        generatePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edUnique.getText().toString().length()==5 || edPin.getText().toString().length()==4){
                new ChangePin(edUnique.getText().toString(),edPin.getText().toString()).execute();
                }

                else {
                    Toast.makeText(getApplicationContext(), "Improper format",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChooseType.class);
                startActivity(i);
            }
        });



    }

    public ArrayList<String> fetchCompanies(){
        sql_backend newEntry = new sql_backend(Form_Creation.this);
        try {
            newEntry.open();
            return newEntry.getData();
        } catch (SQLException e) {
            e.printStackTrace();
            Intent i = new Intent(getApplicationContext(), AskCompanyDetails.class);

            startActivity(i);
            return null;
        }

    }

    public ArrayList<String> fetchDetailsCompany(String name_company){
        sql_backend newEntry = new sql_backend(Form_Creation.this);
        try {
            newEntry.open();
            return newEntry.getInfoCompany(name_company);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    class CreateNewProduct extends AsyncTask<ArrayList<String>, String, String> {
        ArrayList<String> formUnique = new ArrayList<>();
        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String> compDetails = new ArrayList<>();

        CreateNewProduct(ArrayList<String> form_unique, ArrayList<String> compDetails, ArrayList<String> questions){
            this.formUnique = form_unique;
            this.questions = questions;
            this.compDetails = compDetails;
        }

        @Override
        protected String doInBackground(ArrayList<String>... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();


                params.add(new BasicNameValuePair("UNIQUEID", "" + formUnique.get(0)));
                params.add(new BasicNameValuePair("PIN",""+formUnique.get(0)));
            Log.d("formQs", questions.toString());

            for(int i=0; i<compDetails.size(); i++){
                params.add(new BasicNameValuePair(getResources().getStringArray(R.array.companyParams)[i],compDetails.get(i)));
            }
            for(int i=0; i<questions.size(); i++){
                /*String temp = formEditMode.get(i).replaceAll("'","\\'");
                Log.d("Error",temp);*/
                //String temp_new  = temp.replaceAll("&","\&");
                params.add (new BasicNameValuePair(getResources().getStringArray(R.array.numbers)[i],questions.get(i)));
            }


            try {

                JSONObject json = jsonParser.makeHttpRequest(url_create_product, "POST", params);
                if (json==null){
                    publishProgress(""+1);
                    return "JSONNull";

                }
                Log.d("Create Response", json.toString());
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Log.d("Cabme","Booking Done");


                } else {
                    // failed to create product

                    Log.e("Cabme","Booking Failed");
                    return "JSONNull";
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return "JSONNull";
            }

            return "JSONNotNull";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Form_Creation.this);
            pDialog.setMessage("Booking Your Cab..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        protected void onPostExecute(String file_url) {

            if(file_url.equals("JSONNull")){
                Log.d("cabme","Json is null");
                Toast.makeText(getApplicationContext(), "No Internet Connection or server issue",
                        Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
            else {

            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Success",
                    Toast.LENGTH_LONG).show();
                formEditMode.clear();

            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }
    class ChangePin extends AsyncTask<String, String, String> {
        String generated_pin, Unique_id;
        ChangePin(String Unique_id, String generated_pin){
            this.generated_pin = generated_pin;
            this.Unique_id = Unique_id;
        }

        @Override
        protected String doInBackground(String... args) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();


                params.add(new BasicNameValuePair("UNIQUEID", "" + Unique_id));

            params.add(new BasicNameValuePair("PIN", "" + generated_pin));

                try {

                    JSONObject json = jsonParser.makeHttpRequest(url_change_pin, "POST", params);
                    if (json==null){
                        return "JSONNull";

                    }
                    Log.d("Create Response", json.toString());
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // successfully created product
                        Log.d("Cabme","Booking Done");


                    } else {
                        // failed to create product

                        Log.e("Cabme","Booking Failed");
                        return "JSONNull";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return "JSONNotNull";
            }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Form_Creation.this);
            pDialog.setMessage("Booking Your Cab..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected void onPostExecute(String file_url) {

            if(file_url.equals("JSONNull")){
                Log.d("cabme","Json is null");
                Toast.makeText(getApplicationContext(), "Booking Failed \nNo Internet Connection or server issue",
                        Toast.LENGTH_LONG).show();
            }

            pDialog.dismiss();
            if(file_url.equals("JSONNotNull")){
            Toast.makeText(getApplicationContext(), "Booking Done\nA cabme official will call you soon regarding confirmation",
                    Toast.LENGTH_LONG).show();}

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Data", "" + formEditMode.size());
    }
}