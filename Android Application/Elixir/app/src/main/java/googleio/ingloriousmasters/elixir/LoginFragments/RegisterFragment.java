package googleio.ingloriousmasters.elixir.LoginFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import googleio.ingloriousmasters.elixir.LoginActivity;
import googleio.ingloriousmasters.elixir.Objects.User;
import googleio.ingloriousmasters.elixir.R;

public class RegisterFragment extends Fragment {

    ImageView reg_ok;
    ImageView reg_cancel;
    EditText text_name;
    EditText text_email;
    EditText text_password;
    EditText text_confirmpassword;

    String email;
    String password;
    String confirmPassword;
    User user;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        reg_ok = (ImageView)rootView.findViewById(R.id.image_register_ok);
        reg_cancel = (ImageView)rootView.findViewById(R.id.image_register_cancel);

        text_name = (EditText) rootView.findViewById(R.id.input_signup_name);
        text_email = (EditText) rootView.findViewById(R.id.input_signup_email);
        text_password = (EditText) rootView.findViewById(R.id.input_signup_password);
        text_confirmpassword = (EditText) rootView.findViewById(R.id.input_signup_confirmpassword);

        reg_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity) getActivity()).changeFragmentLogin();
            }
        });

        reg_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){
                    registerUser();
                }
            }
        });

        return rootView;
    }

    private boolean checkFields(){
        email = text_email.getText().toString().trim();
        password = text_password.getText().toString();
        confirmPassword = text_confirmpassword.getText().toString();

        if (text_name.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (text_email.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (text_password.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(password.equals(confirmPassword))) {
            Toast.makeText(getActivity(), "Password Mismatch", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            user = new User();
            user.setName(text_name.getText().toString().trim());
            user.setEmail(email);

            return true;
        }
    }

    private void registerUser(){
        ((LoginActivity) getActivity()).mDialog.show();
        ((LoginActivity) getActivity()).register(email,password,user);
    }

}
