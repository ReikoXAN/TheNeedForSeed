package com.jonnyg.gardenapp;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AzurePage extends AppCompatActivity{

    private MobileServiceClient mClient;
    Azuretbl myAzuretbl = new Azuretbl();

    Button button_save_to_azure;
    Button button_view_from_azure;
    EditText edittext_seed_name_for_azure;
    EditText edittext_seed_type_for_azure;
    EditText edittext_seed_amount_for_azure;

    //ListView azure_list_view;
    private static final String TAG = "JonnysMessage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azure_page);
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

        try {
            mClient = new MobileServiceClient("https://testerga.azurewebsites.net", this);
        }
        catch (Exception e) {

        }
        saveToAzure();
        //viewFromAzure();

        edittext_seed_name_for_azure = (EditText)findViewById(R.id.editTextSaveNameToAzure);
        edittext_seed_type_for_azure = (EditText) findViewById(R.id.editTextSaveTypeToAzure);
        edittext_seed_amount_for_azure = (EditText) findViewById(R.id.editTextSaveAmountToAzure);



        //newThread();



    }

   /* public void newThread(){
        final ArrayAdapter<Azuretbl> myAdapter = new ArrayAdapter<Azuretbl>
                (getApplicationContext(), android.R.layout.simple_list_item_activated_1);

        azure_list_view = (ListView) findViewById(R.id.listViewAzure);

        azure_list_view.setAdapter(myAdapter);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    MobileServiceList<Azuretbl> myResult = mClient.getTable(Azuretbl.class)
                            .where().field("SEEDNAME").eq("Tomato").execute().get();
                    for(Azuretbl item: myResult){
                        myAdapter.add(item);
                        Log.e("Result are SEEDNAME :" ,"==== " + item.SEEDNAME);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }*/

    public void saveToAzure(){
        button_save_to_azure = (Button)findViewById(R.id.btnSaveDataToAzure);
        button_save_to_azure.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAzuretbl.SEEDNAME = edittext_seed_name_for_azure.getText().toString();
                        myAzuretbl.SEEDTYPE = edittext_seed_type_for_azure.getText().toString();
                        myAzuretbl.SEED_AMOUNT = Integer.parseInt(edittext_seed_amount_for_azure.getText().toString());

                        mClient.getTable(Azuretbl.class).insert(myAzuretbl, new TableOperationCallback<Azuretbl>() {

                            @Override
                            public void onCompleted(Azuretbl entity, Exception exception, ServiceFilterResponse response) {
                                if (exception == null) {
                                    // Insert succeeded
                                    Toast myToast = Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG);
                                    myToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                                    myToast.show();

                                   /* MobileServiceTable<Azuretbl> myToDOMobService = mClient.getTable(Azuretbl.class);
                                    myToDOMobService.select("SEEDNAME");*/

                                    edittext_seed_name_for_azure.setText("");
                                    edittext_seed_type_for_azure.setText("");
                                    edittext_seed_amount_for_azure.setText("");

                                } else {
                                    // Insert failed

                                    Toast myFailToast = Toast.makeText(getApplicationContext(),"Not Inserted", Toast.LENGTH_LONG);
                                    myFailToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                    myFailToast.show();

                                    edittext_seed_name_for_azure.setText("");
                                    edittext_seed_type_for_azure.setText("");
                                    edittext_seed_amount_for_azure.setText("");
                                }
                            }
                        });

                    }
                }
        );
    }

    public void showMessage(String title,String message) {
        final AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        final AlertDialog myAlert = myBuilder.create();
        myAlert.setCancelable(true);
        myAlert.setTitle(title);
        myAlert.setMessage(message);
        myAlert.show();
        /*StringBuffer myBuff = null;
        myAdapter.add(item);
        showMessage("Azure ", myBuff.toString());
myBuff = new StringBuffer();

        myBuff.append("id " + item.id + "\n");
        myBuff.append("Name :"+ item.SEEDNAME +"\n\n");
        myBuff.append("id2 " + item.id +"\n");
        myBuff.append("Name2 :"+ item.SEEDNAME +"\n\n");*/
    }







}
