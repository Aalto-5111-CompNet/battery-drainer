package fi.aalto.sci.cn2.drainer;

import android.content.Context;
import android.os.Vibrator;

public class Dispatcher {
    private boolean state = false;
    private Context context;

    public Dispatcher(Context context) {
        this.context = context;
    }

    public void start (){
        this.state = true;

        this.vibrate(true);
    }

    public void stop (){
        state = false;

        this.vibrate(false);
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
}
