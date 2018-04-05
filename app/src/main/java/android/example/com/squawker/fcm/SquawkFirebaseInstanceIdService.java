package android.example.com.squawker.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by figengungor on 4/4/2018.
 */



/* A service that extends FirebaseInstanceIdService to handle the creation, rotation, and updating
 * of registration tokens.This is required for sending to specific devices or for creating device
 * groups.
 *
 * https://firebase.google.com/docs/cloud-messaging/android/client
 * */

public class SquawkFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = SquawkFirebaseInstanceIdService.class.getSimpleName();

   /* Access the device registration token

    On initial startup of your app, the FCM SDK generates a registration token for the client
    app instance. If you want to target single devices or create device groups, you'll need to
    access this token by extending FirebaseInstanceIdService.

    This section describes how to retrieve the token and how to monitor changes to the token.
    Because the token could be rotated after initial startup, you are strongly recommended to
    retrieve the latest updated registration token.

    The registration token may change when:

    The app deletes Instance ID
    The app is restored on a new device
    The user uninstalls/reinstall the app
    The user clears app data.*/

   /* The onTokenRefreshcallback fires whenever a new token is generated, so calling getToken in its
    context ensures that you are accessing a current, available registration token. Make sure you
    have added the service to your manifest, then call getToken in the context of onTokenRefresh,
    and log the value as shown:*/

    @Override
    public void onTokenRefresh() {
        //Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

    }
}
