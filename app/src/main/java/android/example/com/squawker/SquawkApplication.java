package android.example.com.squawker;

import android.app.Application;
import com.facebook.stetho.Stetho;

/**
 * Created by figengungor on 4/5/2018.
 */

public class SquawkApplication extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
