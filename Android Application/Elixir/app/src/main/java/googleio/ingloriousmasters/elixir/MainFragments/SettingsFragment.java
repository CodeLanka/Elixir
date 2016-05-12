package googleio.ingloriousmasters.elixir.MainFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import googleio.ingloriousmasters.elixir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        final String[] settingsList = new String[]{"Deactivate Account","Mobile Notifications (Dialog)"};

        final ArrayList<String> settings = new ArrayList<String>();
        for(int i = 0;i<settingsList.length;++i){
            settings.add(settingsList[i]);
            Log.d(i+" ==> ",settingsList[i]);
        }

        final ListView settingView = (ListView) rootView.findViewById(R.id._list_settings_actions);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,settingsList);
        settingView.setAdapter(adapter);

        return rootView;
    }

}
