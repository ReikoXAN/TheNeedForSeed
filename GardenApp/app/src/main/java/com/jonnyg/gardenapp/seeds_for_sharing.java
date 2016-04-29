package com.jonnyg.gardenapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.ArrayList;

public class seeds_for_sharing extends AppCompatActivity {

    private MobileServiceClient mClient;
    Azuretbl myAzuretbl = new Azuretbl();

    Button button_view_from_azure;
    ListView list_view_see_sharing;
    private static final String TAG = "JonnysMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeds_for_sharing);
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
        viewFromAzure();
    }


    public void viewFromAzure(){
        button_view_from_azure = (Button)findViewById(R.id.btnAzureShare);
        button_view_from_azure.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*final ArrayAdapter<Azuretbl> myAdapter = new ArrayAdapter<Azuretbl>
                                (getApplicationContext(), android.R.layout.simple_list_item_activated_1);*/
                        ArrayList<Azuretbl> arrayOfSeeds = new ArrayList<Azuretbl>();
                        final AzureTestAdapter custAdapter = new AzureTestAdapter(getApplicationContext(), arrayOfSeeds);

                        list_view_see_sharing = (ListView) findViewById(R.id.listViewVSharing);

                        list_view_see_sharing.setAdapter(custAdapter);

                        new AsyncTask<Void, Void, MobileServiceList<Azuretbl>>(){
                            MobileServiceTable<Azuretbl> myTestAzuretbl = mClient.getTable(Azuretbl.class);

                            @Override
                            protected MobileServiceList<Azuretbl> doInBackground(Void... params) {
                                MobileServiceList<Azuretbl> result;
                                try {
                                    result = myTestAzuretbl.select("SEEDNAME","SEEDTYPE","SEED_AMOUNT").execute().get();

                                    /*where().field("SEEDNAME").eq("tomato").*/
                                    final MobileServiceList<Azuretbl> finalResult = result;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            custAdapter.clear();
                                            for (Azuretbl item : finalResult) {
                                                //Azuretbl newSeed = new Azuretbl(item.id);
                                                custAdapter.add(item);
                                                Log.i(TAG, "Read object with ID " + item.id + " " + item.SEEDNAME + " " +
                                                        item.SEEDTYPE + " " + item.SEED_AMOUNT);

                                                System.out.println("Item is " + finalResult);
                                            }
                                        }
                                    });

                                } catch (Exception exception) {

                                }
                                return null;
                            }
                        }.execute();


                    }
                }
        );
    }

}
