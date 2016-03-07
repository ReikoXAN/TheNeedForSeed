package com.jonnyg.gardenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Hub extends AppCompatActivity {

    private static Button button_SeedSave;
    private static Button button_Camera;
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
    }
    public void OnClickButtonListener(){
        button_SeedSave = (Button)findViewById(R.id.btn);
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
        button_Camera = (Button)findViewById(R.id.btnCamera);
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

}
