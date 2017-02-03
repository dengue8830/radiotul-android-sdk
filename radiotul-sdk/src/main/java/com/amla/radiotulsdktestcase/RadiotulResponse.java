package com.amla.radiotulsdktestcase;

import com.amla.radiotulsdktestcase.access.User;
import com.amla.radiotulsdktestcase.events.Event;

import java.util.List;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class RadiotulResponse {

    public interface RadioTulCallback {
        /** Called when a response is received but something went wrong with our code like parsing errors*/
        public void onUnexpectedError();
        /** Called when there was a problem with the communication, like authorization or network status */
        public void onRequestError();
        /** Called when there was a problem: onUnexpectedError or onRequestError.
         *  This callback can replace others errors callbacks because is the general case */
        public void onError();
    }

    /** Callback interface for delivering parsed responses. */
    public interface SignIn extends RadioTulCallback {
        /** Called when a response is received and parsed succefully */
        public void onSuccess(User userSignedIn);
        /** Called when a response is received but the signin data was wrong */
        public void onUserNotFound();
    }

    public interface SimpleCallback extends RadioTulCallback {
        public void onSuccess();
    }

    /** Callback interface for delivering parsed responses. */
    public interface SignUp extends SimpleCallback {
        /** Called when a response is received but the signin data was wrong */
        public void onEmailAlreadyExists();
    }

    public interface GetEvents extends RadioTulCallback {
        /** Called when a response is received and parsed succefully */
        public void onSuccess(List<Event> events);
    }

    public interface GetMyWonEventsNotSeen extends RadioTulCallback {
        public void onSuccess(List<Event> events);
    }
}
