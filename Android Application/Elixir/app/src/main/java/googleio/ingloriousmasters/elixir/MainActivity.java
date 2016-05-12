package googleio.ingloriousmasters.elixir;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.Firebase;

import googleio.ingloriousmasters.elixir.MainFragments.AboutFragment;
import googleio.ingloriousmasters.elixir.MainFragments.EventFragment;
import googleio.ingloriousmasters.elixir.MainFragments.NotificationsFragment;
import googleio.ingloriousmasters.elixir.MainFragments.ProfileFragment;
import googleio.ingloriousmasters.elixir.MainFragments.RequestBloodFragment;
import googleio.ingloriousmasters.elixir.MainFragments.RequestOrgansFragment;
import googleio.ingloriousmasters.elixir.MainFragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String uid;
    String username;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        //username = intent.getStringExtra("username");
        Log.d("UID =======> ", uid);
        //Log.d("USER =======> ", username);

        ref = new Firebase(getResources().getString(R.string.MY_FIREBASE_APP));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        changeProfileFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_profile) {
            getSupportActionBar().setTitle("Profile");
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_requestBlood) {
            getSupportActionBar().setTitle("Request Blood");
            fragment = new RequestBloodFragment();
        } else if (id == R.id.nav_requestOrgans) {
            getSupportActionBar().setTitle("Request Organs");
            fragment = new RequestOrgansFragment();
        } else if (id == R.id.nav_events) {
            getSupportActionBar().setTitle("Organize Events");
            fragment = new EventFragment();
        } else if (id == R.id.nav_notifications) {
            getSupportActionBar().setTitle("Notifications");
            fragment = new NotificationsFragment();
        } else if (id == R.id.nav_logout) {
            logout();
        } else if (id == R.id.nav_settings) {
            getSupportActionBar().setTitle("Settings");
            fragment = new SettingsFragment();
        } else if (id == R.id.nav_about) {
            getSupportActionBar().setTitle("About");
            fragment = new AboutFragment();
            Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        } else {
            // Error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeProfileFragment(){
        getSupportActionBar().setTitle("Profile");
        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void logout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Log Out");
        alert.setMessage("Are you sure you want to Log Out?");
        alert.setIcon(R.drawable.logout);
        alert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
                ref.unauth();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Successfully Cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
    }
}
