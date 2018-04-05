package android.example.com.squawker.fcm;

import android.content.ContentValues;
import android.example.com.squawker.provider.SquawkContract;
import android.example.com.squawker.provider.SquawkProvider;
import android.example.com.squawker.utils.NotificationUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

/**
 * Created by figengungor on 4/4/2018.
 */

/* Notes about Notification and Data messages

    1. Notification

            {"to":"<registration_token>",
            "notification":
                {"author":"TestAccount",
                 "body":"Pug cold-pressed swag, enamel pin vexillologist pabst yr",
                 "extra_value":"you can send as many extra value as you like"}
            }

    * Displays notification automatically
    * Predefined key values
    * Extra values in intent extra(You can get these extra from getIntent().getExtras()
      in OnCreate of your MainActivity)
    * Can be sent from firebase console
    * App in background => shows notification
    * App in foreground => triggers onMessageReceived

    WHEN TO USE?
    * You want the system to show notifications on your behalf.
    * You only need to run code in the foreground.

     Example: Advertisements or notifications for a mobile game without the need to change code.

    -------------------------------------------------

    2. Data

            {"to":"<registration_token>",
            "data":
                {"author":"TestAccount",
                "message":"Pug cold-pressed swag, enamel pin vexillologist pabst yr",
                "date":1522917251295,
                "authorKey":"key_test"}
            }
    * Requires you to do all the data processing(You are responsible of notification creation)
    * All custom key value pairs
    * Cannot be sent from Firebase Console
    * App in foreground or background => onMessageReceived is triggered.

    WHEN TO USE?
    * You need to run code, whether the app is in the background or the foreground, choose this option!

    Example: Email app that should sync henever a new message received.
*/

// Squawk Server to send data message => https://squawkerfcmserver.udacity.com/
// Setting up an FCM Server
// https://firebase.google.com/docs/cloud-messaging/server
// https://firebase.googleblog.com/2016/08/sending-notifications-between-android.html
// https://www.npmjs.com/package/fcm-node

public class SquawkFirebaseMessagingService extends FirebaseMessagingService {

    private static final String JSON_KEY_AUTHOR = "author";
    private static final String JSON_KEY_MESSAGE = "message";
    private static final String JSON_KEY_DATE = "date";
    private static final String JSON_KEY_AUTHOR_KEY = "authorKey";
    private static final String TAG = SquawkFirebaseMessagingService.class.getSimpleName();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: ");
        Map<String, String> data = remoteMessage.getData();
        String author = data.get(JSON_KEY_AUTHOR);
        String message = data.get(JSON_KEY_MESSAGE);
        if (message.length() > 30) message = message.substring(30);
        NotificationUtils.notifyUser(getApplicationContext(), author, message);
        addSquawkToDb(data);
    }

    private void addSquawkToDb(Map<String, String> data) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(SquawkContract.COLUMN_AUTHOR, data.get(JSON_KEY_AUTHOR));
        contentValues.put(SquawkContract.COLUMN_MESSAGE, data.get(JSON_KEY_MESSAGE));
        contentValues.put(SquawkContract.COLUMN_DATE, data.get(JSON_KEY_DATE));
        contentValues.put(SquawkContract.COLUMN_AUTHOR_KEY, data.get(JSON_KEY_AUTHOR_KEY));

        getContentResolver().insert(SquawkProvider.SquawkMessages.CONTENT_URI, contentValues);

    }
}
