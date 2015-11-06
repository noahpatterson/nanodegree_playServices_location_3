package com.example.udacity_noah.location_geofences;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.nfc.Tag;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;


import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GeofenceTransitionsIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "intent service";

    public GeofenceTransitionsIntentService() {
        super("GeofenceTransitionsIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "handle intent");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            return;
        }

        // get the transition type
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        String geofenceTranstionTypeString = getGeofenceTransitionTypeString(geofenceTransition);

        //check if transition is enter or exit
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
            String geoFenceIds = getGeofenceTransitionDetails(geofenceList, geofenceTranstionTypeString);
            Log.d(TAG, geoFenceIds);
            sendNotification(geoFenceIds);
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            Log.d(TAG, "Dwelling");
        }
    }

    private String getGeofenceTransitionDetails(List<Geofence> geofences, String transitionType) {
        ArrayList triggeringIds = new ArrayList();
        for (Geofence geofence : geofences) {
            triggeringIds.add(geofence.getRequestId());
        }
        return transitionType + ": " + TextUtils.join(", ", triggeringIds);
    }

    private String getGeofenceTransitionTypeString(int geofenceTransitionType) {
        switch (geofenceTransitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "Enter";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "Exit";
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return "Dwell";
            default:
                return "Unknown transition";
        }
    }

    private void sendNotification(String geofencesCrossedString) {
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent;

        //maintain proper backstack
        TaskStackBuilder stackBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(this);


            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);

            resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Geofence Crossed!")
                .setContentText(geofencesCrossedString)
                .setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
