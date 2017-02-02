package com.amla.radiotulsdk;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class RadiotulResponse {

    /** Callback interface for delivering parsed responses. */
    public interface SignIn<User> {
        /** Called when a response is received and parsed succefully */
        public void onSuccess(User response);
        /** Called when a response is received but the signin data was wrong */
        public void onWrongData();
        /** Called when a response is received but something went wrong with our code like parsing errors*/
        public void onUnexpectedError();
        /** Called when there was a problem with the communication, like authorization or network status */
        public void onConnectionError();
        /** Called when there was a problem: onUnexpectedError or onConnectionError.
         *  This callback can replace others errors callbacks because is the general case */
        public void onError();
    }
}
