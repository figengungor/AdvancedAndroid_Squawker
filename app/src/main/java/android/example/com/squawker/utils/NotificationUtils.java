package android.example.com.squawker.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.example.com.squawker.MainActivity;
import android.example.com.squawker.R;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * Created by figengungor on 4/4/2018.
 */

public class NotificationUtils {
    private static final int NOTIFICATION_ID = 1;
    private static final int OPEN_ACTIVITY_PENDING_INTENT_REQUEST_CODE = 1;

    public static void notifyUser(Context context, String title, String message) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(
                    context.getString(R.string.default_notification_channel_id),
                    context.getString(R.string.default_notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, context.getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_duck)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivity = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(
                context,
                OPEN_ACTIVITY_PENDING_INTENT_REQUEST_CODE,
                startActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);

    }
}
