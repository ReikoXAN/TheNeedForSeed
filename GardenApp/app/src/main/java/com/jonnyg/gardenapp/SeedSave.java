package com.jonnyg.gardenapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

import java.util.ArrayList;

public class SeedSave extends AppCompatActivity {
    DataBaseHelper myDb;
    EditText editSname, editStype, editSamount;
    Button btnAddData;
    Button btnViewAll;
    Button button_createdb;
    TextView myTextv;
    ListView myList_view;


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
                if (hasFocus) {
                    //context problem sol
                    Toast.makeText(getApplicationContext(), "Must Enter Number Only", Toast.LENGTH_LONG).show();
                }
            }
        });
        AddData();
        veiwAll();
        //testList();
       // populateListView();
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
                            if(editSname.getText().length() == 0  ||
                                    editStype.getText().length()  == 0 ||
                                    editSamount.getText().length()  == 0){
                                Toast.makeText(SeedSave.this, "Values Must not be blank", Toast.LENGTH_LONG).show();
                            }

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
                            else {
                            }


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

                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Error", "No Data in Database");
                            return;
                        }

                        StringBuilder myBuff = new StringBuilder();
                        while (res.moveToNext()) {
                            myBuff.append("Id :" + res.getString(0) + "\n");
                            myBuff.append("Name :" + res.getString(1) + "\n");
                            myBuff.append("Type :" + res.getString(2) + "\n");
                            myBuff.append("Amount :" + res.getString(3) + "\n\n");
                        }
                        // show data
                        showMessage("Data", myBuff.toString());
                    }
                }
        );
    }

    private void populateListView(){

        myDb = new DataBaseHelper(this);
        Cursor res = myDb.getName();

       // String[] from = new String[]{res.getString(0)};
        /*for(Seed_Table item: res){
            Seed_Table mySeed = new Seed_Table(item.get_seedName());

        }*/

        /*mySeed.editSname.toString(),
                mySeed.editStype.toString(),mySeed.editSamount.toString()*/
//        ArrayList<Seed_Table> myCur = res;
        //int[] to = new int[]{R.id.textViewID,R.id.textViewName,R.id.textViewSType,R.id.textViewAmount};
        ArrayAdapter<Seed_Table> myAdapter = new ArrayAdapter<Seed_Table>
                (getApplicationContext(),android.R.layout.simple_list_item_1);

        /*SimpleCursorAdapter myScursorAdapter;
        myScursorAdapter = new SimpleCursorAdapter(getBaseContext(),
               android.R.layout.simple_list_item_1, res);*/

        myList_view = (ListView)findViewById(R.id.listViewTest);
        myList_view.setAdapter(myAdapter);
       // myAdapter.add(mySeed.S);
        //myList_view.setAdapter(myScursorAdapter);
    }

    public void testList(){
        String[] foods = {"Bacon","Tuna","Candy","Meatball","Potato"};
        ListAdapter myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,foods);
        ListView myListView = (ListView)findViewById(R.id.listViewTest);
        myListView.setAdapter(myAdapter);
    }
    //Button mybut = (Button)findViewById(R.id.btnView);
    public void showMessage(String title,String message) {
        final AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        final AlertDialog myAlert = myBuilder.create();
        myAlert.setCancelable(true);
        myAlert.setTitle(title);
        myAlert.setMessage(message);
        myAlert.setButton(DialogInterface.BUTTON_POSITIVE,"Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myAlert.dismiss();
            }
        });
        myAlert.show();
    }


        /*myAlert.setOnDismissListener(
                new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        myAlert.cancel();
                    }
                }
        );*/

}
