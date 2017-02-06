package com.amla.radiotulsdktestcase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amla.radiotulsdk.Constants;
import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
import com.amla.radiotulsdk.access.Access;
import com.amla.radiotulsdk.access.User;
import com.amla.radiotulsdk.company.Company;
import com.amla.radiotulsdk.company.CompanyAPI;
import com.amla.radiotulsdk.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RadiotulSdk.initialize(getApplicationContext(), "RTUL_APP3a526ce2-1c04-4b50-adba-e2bc__AR__14861777", "t7PU20IUO0qtT52VKi_qV9hL68nQPZ7gHl-gaD4Pmvbb80F6E6sBzhLzkMEpBc--cb0eLM58TVwoSENQQ9hm9KGuTFgFh7Y4blmMyVMHIH84-Uh6ovph7goD-IKVR2GaDJT6G-wDL9KA323VmjZ2iYkPiXX1u_rmBfBrU4U3IHTvi2gEobaBcX1hFd92W2tOXmKbakIHuqA2D5RFC8gdBG3WRj0cp87vcMCb7FwN4WKEHyXhWB11cA6G-ei4z6lwdQVPCUIXK6T0RYyXMuZSPuQAGiIvXguYkKjRQuPSTqyjzy8K-LpCYQK8E248-GaLHdpUHEGYkb3eY-X5ACkFew", 3, true);

        Log.i("url", Constants.SERVER_URL);

        if(Constants.SERVER_URL.equals("https://radiotul.com/"))
            throw new RuntimeException("GUARDA GIL, ESTAS PROBANDO CONTRA PROD");

        testSingUp();

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

    private void testSingUp(){
        Access.noSocialsignUp("david", "rearte", "M", "1234", "2015-03-01", "dengue8830@gmail.com", "admin", "1234", RadiotulSdk.getInstance().getCompanyId(), new RadiotulResponse.SignUp() {
            @Override
            public void onEmailAlreadyExists() {
                Log.i("noSocialsignUp", "onEmailAlreadyExists");
                testSignIn();
            }

            @Override
            public void onSuccess() {
                Log.i("noSocialsignUp", "onSuccess");
                testSignIn();
            }

            @Override
            public void onUnexpectedError() {
                Log.i("noSocialsignUp", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.i("noSocialsignUp", "onRequestError");
            }

            @Override
            public void onError() {
                Log.i("noSocialsignUp", "onError");
            }
        });


    }

    private void testSignIn(){
        Access.signIn("dengue8830@gmail.com", "admin", new RadiotulResponse.SignIn() {
            @Override
            public void onSuccess(User userSignedIn) {
                Log.i("signIn", "onSuccess");
                RadiotulSdk.getInstance().setUserSignedIn(userSignedIn);
                testGetDatosEmpresa();
            }

            @Override
            public void onUserNotFound() {
                Log.i("signIn", "onUserNotFound");
            }

            @Override
            public void onUnexpectedError() {
                Log.i("signIn", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.i("signIn", "onRequestError");
            }

            @Override
            public void onError() {
                Log.i("signIn", "onError");
            }
        });
    }

    private void testGetDatosEmpresa(){
        CompanyAPI.getCompanyData(new RadiotulResponse.GetCompanyData() {
            @Override
            public void onSuccess(Company company) {
                Log.i("getCompanyData", "onSuccess");
                Log.i("getCompanyData radio's size", company.getRadios().size()+"");
            }

            @Override
            public void onUnexpectedError() {
                Log.i("getCompanyData", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.i("getCompanyData", "onRequestError");
            }

            @Override
            public void onError() {
                Log.i("getCompanyData", "onError");
            }
        });
    }
}
