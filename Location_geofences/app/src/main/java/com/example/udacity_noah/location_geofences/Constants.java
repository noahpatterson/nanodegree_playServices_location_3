package com.example.udacity_noah.location_geofences;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by noahpatterson on 11/4/15.
 */
public final class Constants {
    private Constants() {}

    public static final HashMap DC_LOCS = new HashMap<String, LatLng>() {{
            put("Home", new LatLng(38.938802, -77.037224));
        }};
}
