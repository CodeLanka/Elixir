package googleio.ingloriousmasters.elixir.MainFragments;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import googleio.ingloriousmasters.elixir.Location.GPSTracker;
import googleio.ingloriousmasters.elixir.MainActivity;
import googleio.ingloriousmasters.elixir.Objects.Profile;
import googleio.ingloriousmasters.elixir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements OnMapReadyCallback {

    Firebase ref;

    private GoogleMap googleMap;
    double latitude = 6.9344;
    double longtitude = 79.8428;

    EditText profile_email;
    EditText profile_name;
    RadioGroup profile_gender;
    RadioButton profile_male;
    RadioButton profile_female;
    Spinner profile_blood;
    TextView profile_birthdate;

    ProgressDialog mDialog;

    String uid;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        ref = new Firebase(getResources().getString(R.string.MY_FIREBASE_APP));

        MapFragment mapFragment = (MapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_profile_fragment);
        mapFragment.getMapAsync(this);

        uid = ((MainActivity)getActivity()).uid;

        ImageView create = (ImageView) rootView.findViewById(R.id.createBtn);
        create.setImageResource(R.drawable.ok);

        ImageView cancel = (ImageView) rootView.findViewById(R.id.cancelBtn);
        cancel.setImageResource(R.drawable.cancel);

        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Processing...");
        mDialog.setCancelable(false);

        profile_email = (EditText) rootView.findViewById(R.id.input_profile_email);
        profile_name = (EditText) rootView.findViewById(R.id.input_profile_name);
        profile_gender = (RadioGroup) rootView.findViewById(R.id.gender_group);
        profile_male = (RadioButton) rootView.findViewById(R.id.radio_profile_male);
        profile_female = (RadioButton) rootView.findViewById(R.id.radio_profile_female);
        profile_blood = (Spinner) rootView.findViewById(R.id.spinner_profile_blood);
        profile_birthdate = (TextView) rootView.findViewById(R.id.text_profile_birthdate);

        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if(checkFields()){
                        mDialog.show();
                        createProfile();
                    }
                } catch (Exception e) {
                    Log.d("EXCEPTION", "LINE create.setOnClickListener");
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadProfile();
            }
        });


        profile_birthdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String string_month;
                        String string_day;

                        if(month < 9){
                            string_month = "0"+(month+1);
                        }else{
                            string_month = Integer.toString(month + 1);
                        }
                        if(day < 10){
                            string_day = "0"+Integer.toString(day);
                        }else{
                            string_day = Integer.toString(day);
                        }

                        profile_birthdate.setText(year + "/" + string_month  + "/" + string_day);
                    }
                };
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        profile_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Male Selected", Toast.LENGTH_SHORT).show();
            }
        });

        profile_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Female Selected", Toast.LENGTH_SHORT).show();
            }
        });

        mDialog.show();
        loadProfile();

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        manualSetMap();

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

    private String getGender(){
        if(profile_male.isChecked()){
            return "Male";
        }else{
            return "Female";
        }
    }

    private String getBloodType(){
        return profile_blood.getItemAtPosition(profile_blood.getSelectedItemPosition()).toString();
    }

    private void setBloodGroup(String blood){
        String compareValue = blood;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.bloodgroups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_blood.setAdapter(adapter);
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            profile_blood.setSelection(spinnerPosition);
        }
    }

    private void setGender(String gender){
        if(gender.equals("Male")){
            profile_male.setChecked(true);
        }else{
            profile_female.setChecked(true);
        }
    }

    private boolean checkFields(){
        if(profile_email.getText().toString().trim().length() == 0){
            Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        }else if(profile_name.getText().toString().trim().length() == 0){
            Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }else if(profile_birthdate.getText().toString().trim().equals("Tap to set Birthdate...") ){
            Toast.makeText(getActivity(), "Enter Birthdate", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void loadProfile(){
        Firebase profileRef = ref.child("profile");

        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("Profile Changed");
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Profile p = postSnapshot.getValue(Profile.class);
                    System.out.println(p.getEmail());

                    if(p.getUid().equals(uid)){
                        //Load profile data
                        System.out.println(p.getUid()+" => "+p.getEmail());

                        profile_email.setText(p.getEmail());
                        profile_name.setText(p.getName());
                        profile_birthdate.setText(p.getBirthdate());
                        setGender(p.getGender());
                        setBloodGroup(p.getBloodGroup());
                        latitude = p.getLatitude();
                        longtitude= p.getLongtitude();

                        manualSetMap();

                        Toast.makeText(getActivity(), "Profile Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("Error " + firebaseError.getMessage());
            }
        });

        mDialog.hide();
    }

    private void createProfile(){
        setCurrentLocation();

        Profile p = new Profile();
        p.setUid(uid);
        p.setEmail(profile_email.getText().toString());
        p.setName(profile_name.getText().toString());
        p.setGender(getGender());
        p.setBloodGroup(getBloodType());
        p.setBirthdate(profile_birthdate.getText().toString());
        p.setLongtitude(longtitude);
        p.setLatitude(latitude);

        Firebase profileRef = ref.child("profile").child(uid);
        profileRef.setValue(p);

        mDialog.hide();
        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
    }
}
