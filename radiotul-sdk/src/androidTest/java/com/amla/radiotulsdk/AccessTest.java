package com.amla.radiotulsdk;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.amla.radiotulsdk.access.Access;
import com.amla.radiotulsdk.access.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by dengue8830 on 2/2/17.
 */

@RunWith(AndroidJUnit4.class)
public class AccessTest {

    @Test
    public void signIn(){
        RadiotulSdk.initialize(InstrumentationRegistry.getTargetContext(), "clientId", "token", 2, true);
        Access.signIn("prueba@prueba.com", "admin", new RadiotulResponse.SignIn() {
            @Override
            public void onSuccess(User userSignedIn) {
                assertThat(userSignedIn.getId(), notNullValue());
            }

            @Override
            public void onUserNotFound() {
                fail("onUserNotFound");
            }

            @Override
            public void onUnexpectedError() {
                fail("onUnexpectedError");
            }

            @Override
            public void onRequestError() {
                fail("onRequestError");
            }

            @Override
            public void onError() {
                fail("onError");
            }
        });
    }
}
