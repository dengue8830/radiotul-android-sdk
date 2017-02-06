package com.amla.radiotulsdktestcase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amla.radiotulsdk.Constants;
import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
import com.amla.radiotulsdk.access.AccessAPI;
import com.amla.radiotulsdk.access.User;
import com.amla.radiotulsdk.company.Company;
import com.amla.radiotulsdk.company.CompanyAPI;
import com.amla.radiotulsdk.event.Event;
import com.amla.radiotulsdk.event.EventAPI;
import com.amla.radiotulsdk.event.Prize;
import com.amla.radiotulsdk.event.Show;
import com.amla.radiotulsdk.message.MessageAPI;
import com.amla.radiotulsdk.profile.ProfileAPI;
import com.amla.radiotulsdk.show.ShowAPI;
import com.amla.radiotulsdk.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RadiotulSdk.initialize(getApplicationContext(), "RTUL_APP3a526ce2-1c04-4b50-adba-e2bc__AR__14861777", "t7PU20IUO0qtT52VKi_qV9hL68nQPZ7gHl-gaD4Pmvbb80F6E6sBzhLzkMEpBc--cb0eLM58TVwoSENQQ9hm9KGuTFgFh7Y4blmMyVMHIH84-Uh6ovph7goD-IKVR2GaDJT6G-wDL9KA323VmjZ2iYkPiXX1u_rmBfBrU4U3IHTvi2gEobaBcX1hFd92W2tOXmKbakIHuqA2D5RFC8gdBG3WRj0cp87vcMCb7FwN4WKEHyXhWB11cA6G-ei4z6lwdQVPCUIXK6T0RYyXMuZSPuQAGiIvXguYkKjRQuPSTqyjzy8K-LpCYQK8E248-GaLHdpUHEGYkb3eY-X5ACkFew", 3, true);

        Log.i("url", Constants.SERVER_URL);

        if(Constants.SERVER_URL.equals("https://radiotul.com/"))
            throw new RuntimeException("GUARDA GIL, ESTAS PROBANDO CONTRA PROD");

        noSocialsignUp();

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

    private void noSocialsignUp(){
        AccessAPI.noSocialsignUp("david", "rearte", "M", "1234", "2015-03-01", "dengue8830@gmail.com", "admin", "1234", RadiotulSdk.getInstance().getCompanyId(), new RadiotulResponse.SignUp() {
            @Override
            public void onEmailAlreadyExists() {
                Log.w("noSocialsignUp", "onEmailAlreadyExists");
                signIn();
            }

            @Override
            public void onSuccess() {
                Log.i("noSocialsignUp", "onSuccess");
                signIn();
            }

            @Override
            public void onUnexpectedError() {
                Log.e("noSocialsignUp", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("noSocialsignUp", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("noSocialsignUp", "onError");
            }
        });


    }

    private void signIn(){
        AccessAPI.signIn("dengue8830@gmail.com", "admin", new RadiotulResponse.SignIn() {
            @Override
            public void onSuccess(User userSignedIn) {
                Log.i("signIn", "onSuccess");
                RadiotulSdk.getInstance().setUserSignedIn(userSignedIn);
                getDatosEmpresa();
                getMyNotSeenWonEvents();
                getMyWonEvents();
                sendMessageToCompany("Prueba mensaje de lib", Constants.COMMENT_TYPE_GOOD);
                updateProfile();
            }

            @Override
            public void onUserNotFound() {
                Log.w("signIn", "onUserNotFound");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("signIn", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("signIn", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("signIn", "onError");
            }
        });
    }

    private void getDatosEmpresa(){
        CompanyAPI.getCompanyData(new RadiotulResponse.GetCompanyData() {
            @Override
            public void onSuccess(Company company) {
                Log.i("getCompanyData", "onSuccess");
                Log.i("getCompanyData radio's size", company.getRadios().size()+"");

                if(!company.getRadios().isEmpty()){
                    getEvents(company.getRadios().get(0).getId());
                    getWeekShowSchedule(company.getRadios().get(0).getId());
                }else{
                    Log.w("getCompanyData", "Empresa no tiene radios, no se puede probar: getEvents, getWeekShowSchedule ni sus dependientes");
                }
            }

            @Override
            public void onUnexpectedError() {
                Log.e("getCompanyData", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("getCompanyData", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("getCompanyData", "onError");
            }
        });
    }

    private void getEvents(long companyId) {
        EventAPI.getEvents(companyId, new RadiotulResponse.GetEvents() {
            @Override
            public void onSuccess(List<Event> events) {
                Log.i("getEvents", "onSuccess");
                Log.i("getEvents size", events.size()+"");

                if(!events.isEmpty()) {
                    getEventPrizes(events.get(0).getId());
                    signUpOnEvent(events.get(0).getId());
                }else{
                    Log.w("getEvents", "Sin eventos, no se puede probar getEventPrizes, signUpOnEvent");
                }
            }

            @Override
            public void onUnexpectedError() {
                Log.e("getEvents", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("getEvents", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("getEvents", "onError");
            }
        });
    }

    private void getEventPrizes(long eventId){
        EventAPI.getEventPrizes(eventId, new RadiotulResponse.GetEventPrizes() {
            @Override
            public void onSuccess(List<Prize> prizes) {
                Log.i("getEventPrizes", "onSuccess");
                Log.i("getEventPrizes size", prizes.size()+"");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("getEventPrizes", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("getEventPrizes", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("getEventPrizes", "onError");
            }
        });
    }

    private void getMyNotSeenWonEvents(){
        EventAPI.getMyNotSeenWonEvents(new RadiotulResponse.GetEvents() {
            @Override
            public void onSuccess(List<Event> events) {
                Log.i("getMyNotSeenWonEvents", "onSuccess");
                Log.i("getMyNotSeenWonEvents size", events.size()+"");
                markWonEventsAsViwed(events);
            }

            @Override
            public void onUnexpectedError() {
                Log.e("getMyNotSeenWonEvents", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("getMyNotSeenWonEvents", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("getMyNotSeenWonEvents", "onError");
            }
        });
    }

    private void getMyWonEvents(){
        EventAPI.getMyWonEvents(new RadiotulResponse.GetEvents() {
            @Override
            public void onSuccess(List<Event> events) {
                Log.i("getMyWonEvents", "onSuccess");
                Log.i("getMyWonEvents", events.size()+"");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("getMyWonEvents", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("getMyWonEvents", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("getMyWonEvents", "onError");
            }
        });
    }

    private void markWonEventsAsViwed(List<Event> events){
        EventAPI.markWonEventsAsViwed(events, new RadiotulResponse.SimpleCallback() {

            @Override
            public void onSuccess() {
                Log.i("markWonEventsAsViwed", "onSuccess");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("markWonEventsAsViwed", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("markWonEventsAsViwed", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("markWonEventsAsViwed", "onError");
            }
        });
    }

    private void signUpOnEvent(long eventId){
        EventAPI.signUpOnEvent(eventId, new RadiotulResponse.SignUp() {
            @Override
            public void onEmailAlreadyExists() {
                Log.i("signUpOnEvent", "onEmailAlreadyExists");
            }

            @Override
            public void onSuccess() {
                Log.e("signUpOnEvent", "onSuccess");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("signUpOnEvent", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("signUpOnEvent", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("signUpOnEvent", "onError");
            }
        });
    }

    private void getWeekShowSchedule(long radioId){
        ShowAPI.getWeekShowSchedule(radioId, new RadiotulResponse.GetWeekShowSchedule() {
            @Override
            public void onSuccess(List<List<Show>> weekSchedule) {
                Log.i("getWeekShowSchedule", "onSuccess");
                Log.i("getWeekShowSchedule size", weekSchedule.size()+"");

                if(!weekSchedule.isEmpty() && !weekSchedule.get(0).isEmpty())
                    sendMessageToShow(weekSchedule.get(0).get(0).getId(), "Hola programa desde lib");
                else
                    Log.w("getWeekShowSchedule", "Sin programas para el dia seleccionado, no se puede probar sendMessageToShow");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("getWeekShowSchedule", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("getWeekShowSchedule", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("getWeekShowSchedule", "onError");
            }
        });
    }

    private void sendMessageToShow( long showId, String message){
        MessageAPI.sendMessageToShow(showId, message, new RadiotulResponse.SimpleCallback() {
            @Override
            public void onSuccess() {
                Log.i("sendMessageToShow", "onSuccess");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("sendMessageToShow", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("sendMessageToShow", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("sendMessageToShow", "onError");
            }
        });
    }

    private void sendMessageToCompany(String message, long commentType){
        MessageAPI.sendMessageToCompany(message, commentType, new RadiotulResponse.SimpleCallback() {
            @Override
            public void onSuccess() {
                Log.i("sendMessageToCompany", "onSuccess");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("sendMessageToCompany", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("sendMessageToCompany", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("sendMessageToCompany", "onError");
            }
        });
    }

    private void updateProfile(){
        ProfileAPI.updateProfile("nom_cambiado", "ape_cambiado", "F", "444", Constants.PHONE_COMPANY_CLARO, "888", "1950-08-20", new RadiotulResponse.SimpleCallback() {
            @Override
            public void onSuccess() {
                Log.i("updateProfile", "onSuccess");
            }

            @Override
            public void onUnexpectedError() {
                Log.e("updateProfile", "onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                Log.e("updateProfile", "onRequestError");
            }

            @Override
            public void onError() {
                Log.e("updateProfile", "onError");
            }
        });
    }
}
