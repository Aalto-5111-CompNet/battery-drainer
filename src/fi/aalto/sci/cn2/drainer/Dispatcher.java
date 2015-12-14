package fi.aalto.sci.cn2.drainer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import fi.aalto.sci.cn2.RefreshScreen;

public class Dispatcher {
    private boolean state = false;
    private Context context;

    private float originalBrightness;
    private GpsDrainerListener gpsListener;
    private BroadcastReceiver bReciever;

    public Dispatcher(Context context) {
        this.context = context;
    }

    public void start (){
        this.state = true;

        this.vibrate(true);
        this.brightness(true);
        this.gps(true);
        this.bluetooth();
        this.httpRequests(true);

        Log.i("Drainer", "Start draining");
    }

    public void stop (){
        state = false;

        this.vibrate(false);
        this.brightness(false);
        this.gps(false);
        this.httpRequests(false);
    }

    /**
     * Infinite quering of Google.com
     * @param state
     */
    private void httpRequests(boolean state){
        if(state){
            HttpRequests.ON = true;
            new HttpRequests().execute("");
        }else{
            HttpRequests.ON = false;
        }
    }

    /**
     * Infinite vibrating
     * @param state
     */
    private void vibrate(boolean state){
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        if (state) {
            long[] pattern = {0, 1000, 1};
            v.vibrate(pattern, 0);
        }else{
            v.cancel();
        }
    }

    /**
     * Maximal brightness
     * @param state
     */
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

    /**
     * Periodically fetching accurate location through GPS, every 1 ms.
     * @param state
     */
    private void gps(boolean state){
        LocationManager locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);

        if(state){
            if(this.gpsListener == null) this.gpsListener = new GpsDrainerListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, this.gpsListener);
        }else{
            locationManager.removeUpdates(this.gpsListener);
        }

    }

    /**
     * Infinite scanning for BT devices
     */
    private void bluetooth(){
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        if(this.bReciever == null){
            this.bReciever = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    Log.i("Drainer", "BT discovery finished");
                    String action = intent.getAction();
                    if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        Log.i("Drainer", "BT device found - " + device.getName());
                    }

                    else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
                    {
                        if(state){
                            adapter.startDiscovery();
                        }
                    }
                }
            };

            this.context.registerReceiver(bReciever,
                    new IntentFilter(BluetoothDevice.ACTION_FOUND));

            IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            this.context.registerReceiver(bReciever,
                    intentFilter);
        }

        adapter.startDiscovery();
    }
}
