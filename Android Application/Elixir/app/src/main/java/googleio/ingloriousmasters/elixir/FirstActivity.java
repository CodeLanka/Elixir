package googleio.ingloriousmasters.elixir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Sathindu on 2016-05-12.
 */
public class FirstActivity extends Activity {

    Firebase ref;
    public static Bus firstBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstBus = new Bus(ThreadEnforcer.MAIN);
        firstBus.register(this);
        ref = new Firebase(getResources().getString(R.string.MY_FIREBASE_APP));

        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    createMainActivity(authData);
                } else {
                    createLoginActivity();
                    Toast.makeText(getApplicationContext(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createMainActivity(AuthData authData) {
        Intent i = new Intent(FirstActivity.this, MainActivity.class);
        i.putExtra("uid", authData.getUid());
        finish();
        FirstActivity.this.startActivity(i);
    }

    private void createLoginActivity() {
        Intent i = new Intent(FirstActivity.this, LoginActivity.class);
        finish();
        FirstActivity.this.startActivity(i);
    }
}
