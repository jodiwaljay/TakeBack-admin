package com.elegance.takeback;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Load_Form extends AppCompatActivity {
    JSONParser jParser = new JSONParser();
    private static String url_all_products;
    private static String url_submit_feedback;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "admin_forms";
    public String UNIQUEID_got ;
    public String PIN_got;
    final int NUM_PAGES = Form_Creation.formEditMode.size();
    JSONArray products = null;
    Button bSubmitFeedback;
    private ProgressDialog pDialog;
    private JSONParser jsonParser = new JSONParser();
    EditText etComments, etUserName, etUserEmail, etUserAddress, etUserPhone;
    TextView tvCompanyName, tvCompanyDesc, tvCompanyPhone, tvCompanyEmail, tvCompanyFax, tvCompanyAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        setContentView(R.layout.preview_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        url_all_products = getResources().getString(R.string.get_url);
        url_submit_feedback = getResources().getString(R.string.submit_url);

        //getSupportActionBar().setLogo(R.drawable.icon_feedback);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setTitle("Help us do better");

        bSubmitFeedback = (Button)findViewById(R.id.bSubmitFeedback);
        etComments = (EditText)findViewById(R.id.etComments);
        etUserName = (EditText)findViewById(R.id.etUserName);
        etUserPhone = (EditText)findViewById(R.id.etUserPhone);
        etUserEmail = (EditText)findViewById(R.id.etUserEmail);
        etUserAddress = (EditText)findViewById(R.id.etUserAddress);

        tvCompanyName = (TextView)findViewById(R.id.tvCompanyName);
        tvCompanyDesc = (TextView)findViewById(R.id.tvCompanyDesc);
        tvCompanyPhone = (TextView)findViewById(R.id.tvPhone);
        tvCompanyEmail = (TextView)findViewById(R.id.tvCompanyEmail);
        tvCompanyFax = (TextView)findViewById(R.id.tvFax);
        tvCompanyAdd = (TextView)findViewById(R.id.tvCompanyAdd);

        Intent intent = getIntent();
        String pin_req = intent.getStringExtra("pin");
        if(!pin_req.equals("DEFAULT")){
            PIN_got = pin_req;
            new LoadAllProducts().execute(pin_req);
        }



        else new LoadAllProducts().onPostExecute("NULL");

        Log.d("Num Pages", "" + NUM_PAGES);
        bSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Form_Creation.Comments = new ArrayList<String>();
                Form_Creation.User_Details = new ArrayList<String>();
                Form_Creation.Comments.add(etComments.getText().toString());
                if(!etUserName.getText().toString().equals("")){
                    Form_Creation.User_Details.add(etUserName.getText().toString());
                }
                else {
                    Form_Creation.User_Details.add("Not Entered");
                }

                if(!etUserEmail.getText().toString().equals("")){
                    Form_Creation.User_Details.add(etUserEmail.getText().toString());
                }
                else {
                    Form_Creation.User_Details.add("Not Entered");
                }

                if(!etUserPhone.getText().toString().equals("")){
                    Form_Creation.User_Details.add(etUserPhone.getText().toString());
                }
                else {
                    Form_Creation.User_Details.add("Not Entered");
                }

                if(!etUserAddress.getText().toString().equals("")){
                    Form_Creation.User_Details.add(etUserAddress.getText().toString());
                }
                else {
                    Form_Creation.User_Details.add("Not Entered");
                }

                new CreateNewProduct().execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Form_Creation.Questions_Loaded.clear();
        Form_Creation.companyLoaded.clear();
        Form_Creation.User_Details.clear();
    }

    public void setCompanyDetails(){
        if(Form_Creation.companyLoaded.size()>0){
        if(Form_Creation.companyLoaded.get(0).equals("DEFAULT")){
            tvCompanyName.setVisibility(View.GONE);
        }
        else{
            tvCompanyName.setText(Form_Creation.companyLoaded.get(0));
        }

        if(Form_Creation.companyLoaded.get(1).equals("DEFAULT")){
            tvCompanyAdd.setVisibility(View.GONE);
        }
        else{
            tvCompanyAdd.setText(Form_Creation.companyLoaded.get(1));
        }

        if(Form_Creation.companyLoaded.get(2).equals("DEFAULT")){
            tvCompanyPhone.setVisibility(View.GONE);
        }
        else{
            tvCompanyPhone.setText(Form_Creation.companyLoaded.get(2));
        }

        if(Form_Creation.companyLoaded.get(3).equals("DEFAULT")){
            tvCompanyFax.setVisibility(View.GONE);
        }
        else{
            tvCompanyFax.setText(Form_Creation.companyLoaded.get(3));
        }

        if(Form_Creation.companyLoaded.get(4).equals("DEFAULT")){
            tvCompanyEmail.setVisibility(View.GONE);
        }
        else{
            tvCompanyEmail.setText(Form_Creation.companyLoaded.get(4));
        }

        if(Form_Creation.companyLoaded.get(5).equals("DEFAULT")){
            tvCompanyDesc.setVisibility(View.GONE);
        }
        else{
            tvCompanyDesc.setText(Form_Creation.companyLoaded.get(5));
        }
        }

    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL

            try {
                JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
                // Checking for SUCCESS TAG
                //Log.d("All Products: ", json.toString());
                int success = json.getInt(TAG_SUCCESS);
                Log.d("JSON Received", json.toString());

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    int j;
                    int uniq_id;
                    for ( j = 0; j < products.length(); j++) {
                        JSONObject c = products.getJSONObject(j);
                        boolean flag = false;
                        // Storing each json item in variable
                        String pins = c.getString("PIN");
                        StringTokenizer stringTokenizer = new StringTokenizer(pins,";");
                        while(stringTokenizer.hasMoreTokens()){
                            if(stringTokenizer.nextToken().equals(args[0])){
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            break;
                        }
                    }

                    if(j==products.length()){
                        Log.d("Pin","PIN doesn't exist");
                        return "JSONNull";
                    }
                    Log.d("pin_got",""+j);
                    JSONObject form_required = products.getJSONObject(j);

                    UNIQUEID_got = ""+form_required.getInt("UNIQUEID");

                    Form_Creation.formEditMode = new ArrayList<String >();
                    for(int k = 0; k<getResources().getStringArray(R.array.numbers).length; k++){
                        Form_Creation.formEditMode.add(form_required.getString(getResources().getStringArray(R.array.numbers)[k]));
                    }
                    Log.d("arrayList", Form_Creation.formEditMode.toString());
                    Form_Creation.companyLoaded = new ArrayList<String>();
                    for(int k = 0; k<getResources().getStringArray(R.array.companyParams).length; k++){
                        Form_Creation.companyLoaded.add(form_required.getString(getResources().getStringArray(R.array.companyParams)[k]));
                    }
                    Log.d("arrayList", Form_Creation.companyLoaded.toString());

                } else {
                    // no products found
                    // Launch Add New product Activity
                    Log.d("Form_loading", "No form exists");
                }
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
                return "JSONNull";
                //finish();
                //Toast.makeText(getApplicationContext(), "No Internet Connection or server issue",
                //      Toast.LENGTH_LONG).show();
            }
            Log.e("Error", "Can you see me");
            return "JSONNOtNull";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            HashMap<String, String> map = new HashMap<String, String>();

            // adding each child node to HashMap key => value
            map.put("DAY", values[0]);
            map.put("MONTH", values[1]);
            map.put("YEAR", values[2]);
            map.put("HOUR", values[3]);
            map.put("MINUTES", values[4]);
            map.put("FROM", values[5]);
            map.put("TO", values[6]);
            map.put("NAME", values[7]);
            map.put("MOBILENUMBER", values[8]);

            //adding HashList to ArrayList
            //productsList.add(0,map);
            //adapt = new MyCustomAdapter(getApplicationContext(), R.layout.listitem, productsList);
            //lv.setAdapter(adapt);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("JSONNull")){
                Toast.makeText(getApplicationContext(), "Error connecting to server",
                        Toast.LENGTH_SHORT).show();
                finish();
            }


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < Form_Creation.formEditMode.size(); i++) {
                String question = Form_Creation.formEditMode.get(i);
                Log.d("Error",question);
                String type = question.substring(0, 2);
                Bundle temp = new Bundle();
                temp.putString("Question", question);
                if(chooseFragment(type,temp)==null){
                    break;
                }
                fragmentTransaction.add(R.id.fragment_container, chooseFragment(type, temp));
            }


            fragmentTransaction.commit();
            setCompanyDetails();
            bSubmitFeedback.setVisibility(View.VISIBLE);


        }


    }
    class CreateNewProduct extends AsyncTask<ArrayList<String>, String, String> {

        @Override
        protected String doInBackground(ArrayList<String>... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("UNIQUEID",""+UNIQUEID_got));
            params.add(new BasicNameValuePair("PIN", "" + PIN_got));
            //Log.d("formQs",formEditMode.toString());
            int qp;
            for(qp=0; qp< Form_Creation.Questions_Loaded.size(); qp++){
                StringTokenizer stringTokenizer = new StringTokenizer(Form_Creation.Questions_Loaded.get(qp),";");

                params.add(new BasicNameValuePair(getResources().getStringArray(R.array.numbers)[qp],stringTokenizer.nextToken()));
                Log.d("Added", "QSomething");
                String temp = "";
                while(stringTokenizer.hasMoreTokens()){
                    temp = temp + stringTokenizer.nextToken() + " ";
                }
                params.add(new BasicNameValuePair(getResources().getStringArray(R.array.numbers_ans)[qp],temp));
                Log.d("Added",temp);
            }

            for(;qp<10;qp++ ){
                params.add(new BasicNameValuePair(getResources().getStringArray(R.array.numbers)[qp],"DEFAULT"));
                params.add(new BasicNameValuePair(getResources().getStringArray(R.array.numbers_ans)[qp],"DEFAULT"));
            }
            for(int i=0; i<Form_Creation.User_Details.size(); i++){
                params.add (new BasicNameValuePair(getResources().getStringArray(R.array.userParams)[i],Form_Creation.User_Details.get(i)));
            }
            for(int i=0; i<Form_Creation.Comments.size(); i++){
                /*String temp = formEditMode.get(i).replaceAll("'","\\'");
                Log.d("Error",temp);*/
                //String temp_new  = temp.replaceAll("&","\&");
                Log.e("Error","I am in comments");
                params.add (new BasicNameValuePair("COMMENTS",Form_Creation.Comments.get(i)));
            }


            try {

                JSONObject json = jsonParser.makeHttpRequest(url_submit_feedback, "POST", params);
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

                    Log.e("Cabme", "Booking Failed");
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
            pDialog = new ProgressDialog(Load_Form.this);
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
            }

            if (file_url.equals("JSONNotNull")){
                Form_Creation.Questions_Loaded.clear();
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Success",
                        Toast.LENGTH_SHORT).show();
                finish();
            }


        }
    }
    private Fragment chooseFragment(String type, Bundle temp) {


        if(type.equals("RA")){
            Fragment qFrag = new ratingFragment();
            qFrag.setArguments(temp);
            return qFrag;
        }
        if(type.equals("SM")){
            Fragment qFrag = new smileyFragment();
            qFrag.setArguments(temp);
            return qFrag;
        }
        if(type.equals("DI")){
            Fragment qFrag = new dichotFragment();
            qFrag.setArguments(temp);
            return qFrag;
        }
        if(type.equals("SC")){
            Fragment qFrag = new singleChoiceFragment();
            qFrag.setArguments(temp);
            return qFrag;
        }
        if(type.equals("CH")){
            Fragment qFrag = new checklistFragment();
            qFrag.setArguments(temp);
            return qFrag;
        }
        if(type.equals("DE")){
            Fragment qFrag = new descriptiveFragment();
            qFrag.setArguments(temp);
            return qFrag;
        }
        return null;
    }

}