package com.amla.radiotulsdktestcase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amla.radiotulsdktestcase.access.Access;
import com.amla.radiotulsdktestcase.access.User;
import com.amla.radiotulsdktestcase.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RadiotulSdk.initialize(getApplicationContext(), "xxx", "xx", 2, true);
        Access.signIn("prueba@prueba.com", "admin", new RadiotulResponse.SignIn() {
            @Override
            public void onSuccess(User userSignedIn) {
                Log.i("yahoo", "logedin");
            }

            @Override
            public void onUserNotFound() {
                Log.i("xx", "onUserNotFound");
            }

            @Override
            public void onUnexpectedError() {
                Log.i("xx", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.i("xx", "onRequestError");
            }

            @Override
            public void onError() {
                Log.i("xx", "onError");
            }
        });
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
