package fi.aalto.sci.cn2;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by adam on 4.12.15.
 */
public class RefreshScreen extends Activity{


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        finish();
    }
}
