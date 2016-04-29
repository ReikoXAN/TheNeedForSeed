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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class CameraView extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView myImageView;
    Button button_savephoto;
    Button button_image_gallery;
    DataBaseHelper myDb;

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

        Button myButtom = (Button) findViewById(R.id.btnTakePhoto);
        myImageView = (ImageView) findViewById(R.id.imgViewCamera);

        //disable button if no camera
        if(!hasCamera()) {
            myButtom.setEnabled(false);
        }


        ///end of onCreate
        toImageGallery();
        OnClickSavePhoto();
        myDb = new DataBaseHelper(this);
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

    public void OnClickSavePhoto(){
        button_savephoto = (Button)findViewById(R.id.btnSaveImage);
        button_savephoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bitmap myBit = ((BitmapDrawable) myImageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream myBos = new ByteArrayOutputStream();
                        myBit.compress(Bitmap.CompressFormat.PNG, 100, myBos);
                        byte[] img = myBos.toByteArray();
                        //gets byteArray and converts to Bitmap
                        /*Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
                        ImageView image = (ImageView)findViewById(R.id.imageview1) ;
                        image.setImageBitmap(bmp);*/
                        boolean isInserted = myDb.insertData(img);
                        if (isInserted == true) {
                            Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    public void toImageGallery(){
        button_image_gallery = (Button)findViewById(R.id.btnViewGallery);
        button_image_gallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryInetent = new Intent("com.jonnyg.gardenapp.Gallery");
                        startActivity(galleryInetent);
                    }
                }
        );
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
