package googleio.ingloriousmasters.elixir.MainFragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import googleio.ingloriousmasters.elixir.Location.GPSTracker;
import googleio.ingloriousmasters.elixir.R;

public class RequestBloodFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    double latitude = 6.9344;
    double longtitude = 79.8428;


    public RequestBloodFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_blood, container, false);

        MapFragment mapFragment = (MapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_request_location);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        manualSetMap();
        setCurrentLocation();
        //code to set latitude longitude of current location when press location button in map
        this.googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                setCurrentLocation();
                return false;
            }
        });
    }

    public void manualSetMap(){
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longtitude)).title("Your Saved Location");
        Log.e("MAP SET", longtitude + "/" + latitude);

        this.googleMap.clear();
        this.googleMap.addMarker(marker);
        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
        this.googleMap.getUiSettings().setZoomGesturesEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longtitude), 15);
        this.googleMap.animateCamera(update);
    }

    private void setCurrentLocation(){
        GPSTracker gps = new GPSTracker(getActivity());
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longtitude = gps.getLongitude();
            Toast.makeText(getActivity(), "Location Set to Your Current Location", Toast.LENGTH_SHORT).show();
        }else{
            gps.showSettingsAlert();
        }

    }

}
