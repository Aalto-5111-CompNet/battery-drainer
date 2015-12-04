package fi.aalto.sci.cn2.drainer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Vibrator;
import android.view.WindowManager;
import fi.aalto.sci.cn2.RefreshScreen;

public class Dispatcher {
    private boolean state = false;
    private Context context;

    private float originalBrightness;
    private GpsDrainerListener gpsListener;

    public Dispatcher(Context context) {
        this.context = context;
    }

    public void start (){
        this.state = true;

        this.vibrate(true);
        this.brightness(true);
        this.gps(true);
    }

    public void stop (){
        state = false;

        this.vibrate(false);
        this.brightness(false);
        this.gps(false);
    }

    private void vibrate(boolean state){
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        if (state) {
            long[] pattern = {0, 1000, 1};
            v.vibrate(pattern, 0);
        }else{
            v.cancel();
        }
    }

    private void brightness(boolean state){
        Activity activity = (Activity) this.context;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

        if (state) {
            this.originalBrightness = lp.screenBrightness;
            lp.screenBrightness = (float)100 / (float)255;
        }else{
            lp.screenBrightness = this.originalBrightness;
        }

        activity.getWindow().setAttributes(lp);
        this.context.startActivity(new Intent(this.context, RefreshScreen.class));
    }

    private void gps(boolean state){
        LocationManager locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);

        if(state){
            if(this.gpsListener == null) this.gpsListener = new GpsDrainerListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this.gpsListener);
        }else{
            locationManager.removeUpdates(this.gpsListener);
        }

    }
}
