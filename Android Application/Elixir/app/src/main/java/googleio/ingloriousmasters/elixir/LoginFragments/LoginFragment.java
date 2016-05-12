package googleio.ingloriousmasters.elixir.LoginFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import googleio.ingloriousmasters.elixir.LoginActivity;
import googleio.ingloriousmasters.elixir.R;


public class LoginFragment extends Fragment {

    ImageView reg_mail;
    ImageView reg_google;
    Button button_login;

    String email;
    String password;

    TextView text_email;
    TextView text_password;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        reg_mail = (ImageView)rootView.findViewById(R.id.image_reg_mail);
        reg_google = (ImageView)rootView.findViewById(R.id.image_reg_google);
        button_login = (Button) rootView.findViewById(R.id.button_login_login);
        text_email = (TextView) rootView.findViewById(R.id.input_login_email);
        text_password = (TextView) rootView.findViewById(R.id.input_login_password);

        reg_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).changeFragmentRegister();
            }
        });

        reg_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).googleLogin();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = text_email.getText().toString();
                password = text_password.getText().toString();
                ((LoginActivity) getActivity()).mDialog.show();
                ((LoginActivity) getActivity()).login(email,password);
            }
        });

        return rootView;
    }

}
