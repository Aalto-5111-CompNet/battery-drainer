package fi.aalto.sci.cn2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import fi.aalto.sci.cn2.drainer.Dispatcher;

public class MainActivity extends Activity {

    private Dispatcher dispatcher;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dispatcher = new Dispatcher();

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dispatcher.start();
                } else {
                    dispatcher.stop();
                }
            }
        });

        setContentView(R.layout.main);
    }
}
