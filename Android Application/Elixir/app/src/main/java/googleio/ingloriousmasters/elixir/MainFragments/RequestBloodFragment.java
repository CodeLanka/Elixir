package googleio.ingloriousmasters.elixir.MainFragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import googleio.ingloriousmasters.elixir.R;

public class RequestBloodFragment extends Fragment {


    public RequestBloodFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_blood, container, false);

        return rootView;
    }

}
