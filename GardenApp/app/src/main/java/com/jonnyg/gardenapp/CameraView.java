package com.jonnyg.gardenapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class CameraView extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView myImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);
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

        Button myButtom = (Button) findViewById(R.id.btnCamera);
        myImageView = (ImageView) findViewById(R.id.imgViewCamera);

        //disable button if no camera
        if(!hasCamera()) {
            myButtom.setEnabled(false);
        }


        ///end of onCreate
    }

    private Boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //launching the camera
    public void takePhoto(View view){
        Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //take picture and pass results
        startActivityForResult(myIntent, REQUEST_IMAGE_CAPTURE);
    }

    //if you want to return the image taken


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //did take an image
            Bundle extras = data.getExtras();

            Bitmap myPhoto = (Bitmap) extras.get("data");
            myImageView.setImageBitmap(myPhoto);

          /*  Bitmap myBit = ((BitmapDrawable)myImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream myBos = new ByteArrayOutputStream();
            myBit.compress(Bitmap.CompressFormat.PNG,100,myBos);
           // myImageView.setImageBitmap(myBit);
            byte[] img = myBos.toByteArray();*/

        }
    }

}
