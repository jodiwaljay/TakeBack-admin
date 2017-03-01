package com.elegance.takeback;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class preview extends AppCompatActivity {
    JSONParser jParser = new JSONParser();
    private static String url_all_products;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "admin_forms";
    JSONArray products = null;
    EditText etComments;
    TextView tvCompanyName, tvCompanyDesc, tvCompanyPhone, tvCompanyEmail, tvCompanyFax, tvCompanyAdd;

    int NUM_PAGES;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        NUM_PAGES = Form_Creation.formEditMode.size();
        setContentView(R.layout.preview);
        url_all_products = getResources().getString(R.string.get_url);

        if(Form_Creation.formEditMode.size()==10){
            Form_Creation.formEditMode.clear();
            NUM_PAGES = Form_Creation.formEditMode.size();
        }

        etComments = (EditText)findViewById(R.id.etComments);

        tvCompanyName = (TextView)findViewById(R.id.tvCompanyName);
        tvCompanyDesc = (TextView)findViewById(R.id.tvCompanyDesc);
        tvCompanyPhone = (TextView)findViewById(R.id.tvPhone);
        tvCompanyEmail = (TextView)findViewById(R.id.tvCompanyEmail);
        tvCompanyFax = (TextView)findViewById(R.id.tvFax);
        tvCompanyAdd = (TextView)findViewById(R.id.tvCompanyAdd);

        setCompanyDetails();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //new LoadAllProducts().execute();

            Log.d("Num Pages", "" + NUM_PAGES);
            for (int i = 0; i < NUM_PAGES; i++) {
                String question = Form_Creation.formEditMode.get(i);
                String type = question.substring(0, 2);
                Bundle temp = new Bundle();
                temp.putString("Question", question);
                Log.d(type,question);
                fragmentTransaction.add(R.id.fragment_container, chooseFragment(type, temp));
            }


        fragmentTransaction.commit();



    }

    public void setCompanyDetails(){
        if(Form_Creation.companyDetails.get(0).equals("DEFAULT")){
            tvCompanyName.setVisibility(View.GONE);
        }
        else{
            tvCompanyName.setText(Form_Creation.companyDetails.get(0));
        }

        if(Form_Creation.companyDetails.get(1).equals("DEFAULT")){
            tvCompanyAdd.setVisibility(View.GONE);
        }
        else{
            tvCompanyAdd.setText(Form_Creation.companyDetails.get(1));
        }

        if(Form_Creation.companyDetails.get(2).equals("DEFAULT")){
            tvCompanyPhone.setVisibility(View.GONE);
        }
        else{
            tvCompanyPhone.setText(Form_Creation.companyDetails.get(2));
        }

        if(Form_Creation.companyDetails.get(3).equals("DEFAULT")){
            tvCompanyFax.setVisibility(View.GONE);
        }
        else{
            tvCompanyFax.setText(Form_Creation.companyDetails.get(3));
        }

        if(Form_Creation.companyDetails.get(4).equals("DEFAULT")){
            tvCompanyEmail.setVisibility(View.GONE);
        }
        else{
            tvCompanyEmail.setText(Form_Creation.companyDetails.get(4));
        }

        if(Form_Creation.companyDetails.get(5).equals("DEFAULT")){
            tvCompanyDesc.setVisibility(View.GONE);
        }
        else{
            tvCompanyDesc.setText(Form_Creation.companyDetails.get(5));
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
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        Log.d("JSON int", ""+c.getInt("UNIQUEID"));
                        /*String month = c.getString("MONTH");
                        String year = c.getString("YEAR");
                        String hour = c.getString("HOUR");
                        String minutes = c.getString("MINUTES");
                        String from = c.getString("FROM");
                        String to = c.getString("TO");
                        String name = c.getString("NAME");
                        String mobilenumber = c.getString("MOBILENUMBER");

                        publishProgress(day,month,year, hour, minutes, from, to, name, mobilenumber);*/

                    }
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
        return new ScreenSlidePageFragment();
    }

}