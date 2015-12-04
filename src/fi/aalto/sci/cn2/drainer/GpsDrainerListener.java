package fi.aalto.sci.cn2.drainer;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by adam on 4.12.15.
 */
public class GpsDrainerListener implements LocationListener {

    public void onLocationChanged(Location loc) {
        Log.v("Info", "lat:" + loc.getLatitude());
        Log.v("Info", "lon:" + loc.getLongitude());
    }

    public void onProviderDisabled(String provider) {}

    public void onProviderEnabled(String provider) {}

    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
