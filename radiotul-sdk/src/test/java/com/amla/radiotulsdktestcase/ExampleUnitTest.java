package com.amla.radiotulsdktestcase;

import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
import com.amla.radiotulsdk.access.AccessAPI;
import com.amla.radiotulsdk.access.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void signIn(){
        RadiotulSdk.initialize(ShadowApplication.getInstance().getApplicationContext(), "xxx", "xx", 2, true);
        AccessAPI.signIn("prueba@prueba.com", "admin", new RadiotulResponse.SignIn() {
            @Override
            public void onSuccess(User userSignedIn) {
                fail("onSuccess");
                //assertThat(userSignedIn.getId(), notNullValue());
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