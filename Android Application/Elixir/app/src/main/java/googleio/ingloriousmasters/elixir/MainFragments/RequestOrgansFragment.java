package googleio.ingloriousmasters.elixir.MainFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import googleio.ingloriousmasters.elixir.R;

public class RequestOrgansFragment extends Fragment {


    public RequestOrgansFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_organs, container, false);

        return rootView;
    }

}
