package com.example.udacity_noah.location_geofences;

import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.HashMap;

/**
 * Created by noahpatterson on 11/4/15.
 */
public final class Constants {
    private Constants() {}

    public static final float GEOFENCE_RADIUS_IN_METERS = 1609;
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

    public static final HashMap<String, LatLng> DC_LOCS;
    static {
        HashMap<String, LatLng> aMap = new HashMap<>();
        aMap.put("Home", new LatLng(38.938802, -77.037224));
        DC_LOCS = aMap;
    }
}
