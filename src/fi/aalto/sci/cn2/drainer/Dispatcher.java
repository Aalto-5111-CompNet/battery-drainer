package fi.aalto.sci.cn2.drainer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.WindowManager;
import fi.aalto.sci.cn2.RefreshScreen;

public class Dispatcher {
    private boolean state = false;
    private Context context;

    private float originalBrightness;

    public Dispatcher(Context context) {
        this.context = context;
    }

    public void start (){
        this.state = true;

        this.vibrate(true);
        this.brightness(true);
    }

    public void stop (){
        state = false;

        this.vibrate(false);
        this.brightness(false);
    }

    private void vibrate(boolean state){
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        if (state) {
            long[] pattern = {0, 1000, 10};
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
}
