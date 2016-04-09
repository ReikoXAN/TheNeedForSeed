package com.jonnyg.gardenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Hub extends AppCompatActivity {

    private static ImageButton button_SeedSave;
    private static ImageButton button_Camera;
    private static  ImageButton button_AzureSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
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
        OnClickButtonListener();
        OnClickButtonCamera();
        OnClickButtonAzureSave();
    }
    public void OnClickButtonListener(){
        button_SeedSave = (ImageButton)findViewById(R.id.btnSeedSave);
        button_SeedSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent("com.jonnyg.gardenapp.SeedSave");
                        startActivity(myIntent);

                    }
                }
        );
    }

    public void OnClickButtonCamera(){
        button_Camera = (ImageButton)findViewById(R.id.btnCamera);
        button_Camera.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent("com.jonnyg.gardenapp.CameraView");
                        startActivity(myIntent);
                    }
                }
        );
    }

    public void OnClickButtonAzureSave(){
        button_AzureSave = (ImageButton)findViewById(R.id.btnSeedShare);
        button_AzureSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toAzureSharePage = new Intent("com.jonnyg.gardenapp.SeedShare");
                        startActivity(toAzureSharePage);
                    }
                }
        );
    }

}
