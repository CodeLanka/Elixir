package googleio.ingloriousmasters.elixir;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Sathindu on 2016-05-12.
 */
public class Elixir extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
