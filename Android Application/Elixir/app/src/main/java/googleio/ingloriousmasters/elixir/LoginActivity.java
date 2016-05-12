package googleio.ingloriousmasters.elixir;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.HashMap;
import java.util.Map;

import googleio.ingloriousmasters.elixir.LoginFragments.LoginFragment;
import googleio.ingloriousmasters.elixir.LoginFragments.RegisterFragment;
import googleio.ingloriousmasters.elixir.Objects.User;

public class LoginActivity extends AppCompatActivity {

    android.app.Fragment fragment;

    public ProgressDialog mDialog;
    Firebase ref;
    public static Bus loginBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBus = new Bus(ThreadEnforcer.MAIN);
        loginBus.register(this);

        ref = new Firebase(getResources().getString(R.string.MY_FIREBASE_APP));

        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Processing...");
        mDialog.setCancelable(false);

        setContentView(R.layout.activity_login);
        changeFragmentLogin();

    }

    public void changeFragmentLogin() {
        try {
            fragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_login_activity, fragment).commit();
        } catch (Exception e) {
            Log.d("LOGIN", e.getMessage());
        }
    }

    public void changeFragmentRegister() {
        try {
            fragment = new RegisterFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_login_activity, fragment).commit();
        } catch (Exception e) {
            Log.d("LOGIN", e.getMessage());
        }
    }

    public void register(String email, String password, final User user){
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                createUser(user, result.get("uid"));
                mDialog.hide();
                Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                changeFragmentLogin();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                mDialog.hide();
                Toast.makeText(getApplicationContext(), "Error in Creating Account", Toast.LENGTH_SHORT).show();
                Log.d("SIGNUP ERROR IS", firebaseError.getDetails());
                Log.d("ERROR ===>", firebaseError.toString());
            }
        });
    }

    private void createUser(User u,Object id){
        Firebase userRef = ref.child("users").child(id.toString());
        userRef.setValue(u);
    }

    public void googleLogin(){
        ref.authWithOAuthToken("google", "<OAuth Token>", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.d("DONE ===>", authData.getUid());
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.d("ERROR ===>", firebaseError.getDetails());
            }
        });
    }

    public void login(String email, String password){
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                createMainActivity(authData);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                mDialog.hide();

                switch (firebaseError.getCode()) {
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        Toast.makeText(getApplicationContext(), "Register First", Toast.LENGTH_SHORT).show();
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Error in Login", Toast.LENGTH_SHORT).show();
                        break;
                }

                Log.d("LOGIN ERROR",firebaseError.getDetails());
            }
        });
    }

    public void createMainActivity(AuthData authData) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("uid", authData.getUid());
        finish();
        mDialog.hide();
        LoginActivity.this.startActivity(i);
    }

    private void getUserName(){

    }
}
