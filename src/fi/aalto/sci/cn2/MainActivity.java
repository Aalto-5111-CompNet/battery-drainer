package fi.aalto.sci.cn2;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import fi.aalto.sci.cn2.drainer.Dispatcher;
import fi.aalto.sci.cn2.drainer.Flash;

public class MainActivity extends Activity {

    private Flash flash = new Flash();
    private Dispatcher dispatcher;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dispatcher = new Dispatcher(this);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dispatcher.start();
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    flash.open();
                    flash.on();
                } else {
                    dispatcher.stop();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    flash.off();
                    flash.close();
                }
            }
        });
    }

}
