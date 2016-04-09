package com.jonnyg.gardenapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

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

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static Button button_Continue;
    private TextView myTextDetails;
    private ProfilePictureView myImageProf;
    private CallbackManager myCallBkManager;
    private LoginButton button_login;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;

    private FacebookCallback<LoginResult> myCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if(loginResult != null){
                    myTextDetails = (TextView) findViewById(R.id.welcomeMesg);
                    myTextDetails.setText("Welcome " + profile.getName());
                    myImageProf = (ProfilePictureView) findViewById(R.id.profpic);
                    myImageProf.setProfileId(profile.getId());

              /* if(profile.getId()!= null){
                   button_login.setVisibility(View.INVISIBLE);
               }*/
            }
            else{
               // myTextDetails.setText("Login");

           }
        }

        @Override
        public void onCancel() {
            //myTextDetails.setText("not logged in");

        }

        @Override
        public void onError(FacebookException error) {
        }
    };
    private static final String TAG = "JonnysMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FaceBook SDK Initializer

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        myCallBkManager = CallbackManager.Factory.create();

        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken old, AccessToken newToken) {
            }
        };
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                    //newProfile.setCurrentProfile(newProfile);
            }
        };
        mTokenTracker.startTracking();
        mProfileTracker.startTracking();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_login  = (LoginButton) findViewById(R.id.login_button);
        button_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 button_login.registerCallback(myCallBkManager, myCallback);
                 button_login.setReadPermissions("public_profile");
             }
         }
        );
                    Log.i(TAG, "onCreate");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        OnClickButtonListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        myImageProf = (ProfilePictureView) findViewById(R.id.profpic);
        if(Profile.getCurrentProfile() != null){
            Profile profile = Profile.getCurrentProfile();
            myImageProf.setProfileId(profile.getId());
        }

        Log.i(TAG, "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
        Log.i(TAG, "onStop");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        myCallBkManager.onActivityResult(requestCode,resultCode,data);
    }

    public void OnClickButtonListener(){
        button_Continue = (Button)findViewById(R.id.btnContinue);
        button_Continue.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Intent myIntent = new Intent("com.jonnyg.gardenapp.Hub");
                        startActivity(myIntent);
                    }
                }
        );
    }


}

