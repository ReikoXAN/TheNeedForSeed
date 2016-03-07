package com.jonnyg.gardenapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

public class SeedSave extends AppCompatActivity {
    DataBaseHelper myDb;
    EditText editSname, editStype, editSamount;
    Button btnAddData;
    Button btnViewAll;
    Button button_createdb;
    TextView myTextv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_save);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editSname = (EditText)findViewById(R.id.editText);
        editStype = (EditText)findViewById(R.id.editText2);
        editSamount = (EditText)findViewById(R.id.editText3);
        btnAddData = (Button)findViewById(R.id.btnAdd);
        btnViewAll = (Button)findViewById(R.id.btnView);
       // button_createdb = (Button)findViewById(R.id.btnCreateDB);
        myTextv = (TextView)findViewById(R.id.textViewFBname);

        Profile profile = Profile.getCurrentProfile();
        if(Profile.getCurrentProfile() != null) {
            myTextv.setText(profile.getName());
        }
        else
            myTextv.setText("User");

        editSamount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        //context problem sol
                        Toast.makeText(getApplicationContext(), "Must Enter Number Only", Toast.LENGTH_LONG).show();
                    }
            }
        });
        AddData();
        veiwAll();
        //CreateBtn();
       //myDb.onUpgrade(db,1,2);
       //myDb.insertTest("test1","test2");
        myDb = new DataBaseHelper(this);
    }

   /* public void CreateBtn(){
        button_createdb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb = new DataBaseHelper(SeedSave.this);
                    }
                });
    }*/

    /*public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean isInserted = myDb.insertData(editSname.getText().toString(),
                               editStype.getText().toString(),
                               editSamount.getText().toString());
                        if(isInserted == true) {
                            Toast.makeText(SeedSave.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            editSname.getText().clear();
                            editStype.getText().clear();
                            editSamount.getText().clear();
                        }
                        else
                            Toast.makeText(SeedSave.this,"Data not Inserted",Toast.LENGTH_LONG).show();

                       *//* boolean inSert = myDb.insertTest(editSamount.getText().toString());
                        if(inSert == true)
                            Toast.makeText(SeedSave.this,"Inserted"();,Toast.LENGTH_LONG).show
                        else
                            Toast.makeText(SeedSave.this,"not Inserted",Toast.LENGTH_LONG).show();*//*
                    }
                }
        );
    }*/

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int quantity =  Integer.parseInt(editSamount.getText().toString());
                            Seed_Table seed = new Seed_Table(
                                    editSname.getText().toString(),
                                    editStype.getText().toString(),
                                    //editSamount.getText().toString()
                                    quantity
                            );
                            myDb.addSeed(seed);
                            if(editSname.getText().length() != 0  &&
                                    editStype.getText().length()  != 0 &&
                                    editSamount.getText().length()  != 0){

                                        editSname.getText().clear();
                                        editStype.getText().clear();
                                        editSamount.getText().clear();
                            }
                            //Toast.makeText(SeedSave.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            //Toast.makeText(SeedSave.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void veiwAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //res holds the data from the database
                       Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            // show message
                            showMessage("Error", "No Data in Database");
                            return;
                        }

                        StringBuffer myBuff = new StringBuffer();
                        while (res.moveToNext()){
                            myBuff.append("Id :"+ res.getString(0)+"\n");
                            myBuff.append("Name :"+ res.getString(1)+"\n");
                            myBuff.append("Type :"+ res.getString(2)+"\n");
                            myBuff.append("Amount :"+ res.getString(3)+"\n\n");
                        }
                        // show data
                        showMessage("Data",myBuff.toString());
                    }
                }
        );
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        myBuilder.setCancelable(true);
        myBuilder.setTitle(title);
        myBuilder.setMessage(message);
        myBuilder.show();
    }
}
