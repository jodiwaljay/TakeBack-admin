package com.elegance.takeback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.sql.SQLException;

/**
 * Created by root on 1/4/16.
 */
public class AskCompanyDetails extends AppCompatActivity {

    Boolean LOGO_ADDED = false;
    Boolean TITLE_ADDED = false;
    Boolean ADDRESS_ADDED = false;
    Boolean PHONE_NO_ADDED = false;
    Boolean FAX_ADDED = false;
    Boolean EMAIL_ADDED = false;
    Boolean DESCRIPTION_ADDED = false;

    EditText NAME ;
    EditText ADDRESS ;
    EditText PHONE ;
    EditText FAX ;
    EditText EMAIL ;
    EditText DESCRIPTION ;
    ImageView LOGO ;

    Button bSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_details);

        NAME = (EditText)findViewById(R.id.name);
        ADDRESS = (EditText)findViewById(R.id.address);
        PHONE = (EditText)findViewById(R.id.phone);
        FAX = (EditText)findViewById(R.id.fax);
        EMAIL = (EditText)findViewById(R.id.email);
        DESCRIPTION = (EditText)findViewById(R.id.description);


        bSubmit = (Button)findViewById(R.id.bSubmit);


        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NAME.getText().toString().equals("")) {
                    if(ADDRESS.getText().toString().equals("")){
                        ADDRESS.setText("DEFAULT");
                    }
                    if(PHONE.getText().toString().equals("")){
                        PHONE.setText("DEFAULT");
                    }
                    if(FAX.getText().toString().equals("")){
                        FAX.setText("DEFAULT");
                    }
                    if(EMAIL.getText().toString().equals("")){
                        EMAIL.setText("DEFAULT");
                    }
                    if(DESCRIPTION.getText().toString().equals("")){
                        DESCRIPTION.setText("DEFAULT");
                    }

                    createCompany(
                            NAME.getText().toString(),
                            ADDRESS.getText().toString(),
                            PHONE.getText().toString(),
                            FAX.getText().toString(),
                            EMAIL.getText().toString(),
                            DESCRIPTION.getText().toString(),
                            "DEFAULT");

                    finish();
                    try {
                        Form_Creation.form_create.finish();
                    }
                    catch (NullPointerException e){
                        Log.d("Error",e.toString());
                    }
                    Intent i = new Intent(getApplicationContext(),Form_Creation.class);
                    startActivity(i);

                }
            }

        });
    }

    public void createCompany(String NAME, String ADDRESS, String PHONE, String FAX, String EMAIL, String DESCRIPTION, String LOGO){
        sql_backend newEntry = new sql_backend(AskCompanyDetails.this);


        try {
            newEntry.open();
            newEntry.createEntry(NAME, ADDRESS, PHONE, FAX, EMAIL, DESCRIPTION, LOGO);
            newEntry.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
