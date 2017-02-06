package com.amla.radiotulsdktestcase;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.amla.radiotulsdk.RadiotulResponse;
import com.amla.radiotulsdk.RadiotulSdk;
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
        RadiotulSdk.initialize(InstrumentationRegistry.getTargetContext(), "RTUL_APP3a526ce2-1c04-4b50-adba-e2bc__AR__14861777", "t7PU20IUO0qtT52VKi_qV9hL68nQPZ7gHl-gaD4Pmvbb80F6E6sBzhLzkMEpBc--cb0eLM58TVwoSENQQ9hm9KGuTFgFh7Y4blmMyVMHIH84-Uh6ovph7goD-IKVR2GaDJT6G-wDL9KA323VmjZ2iYkPiXX1u_rmBfBrU4U3IHTvi2gEobaBcX1hFd92W2tOXmKbakIHuqA2D5RFC8gdBG3WRj0cp87vcMCb7FwN4WKEHyXhWB11cA6G-ei4z6lwdQVPCUIXK6T0RYyXMuZSPuQAGiIvXguYkKjRQuPSTqyjzy8K-LpCYQK8E248-GaLHdpUHEGYkb3eY-X5ACkFew ", 3, true);
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
