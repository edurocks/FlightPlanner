package dmanlancers.com.flightplanner.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import dmanlancers.com.flightplanner.R;
import dmanlancers.com.flightplanner.activities.FlightPlanActivity;
import dmanlancers.com.flightplanner.activities.LoginActivity;
import dmanlancers.com.flightplanner.managers.RealmManager;
import dmanlancers.com.flightplanner.model.Login;
import io.realm.RealmResults;

public class LoginFragment extends Fragment {
    public static final String PREFS_NAME = "MyApp_Settings";
    RealmManager realmManager;
    private LoginActivity lActivity;
    private CoordinatorLayout loginLayout;
    private EditText inputEmail, inputPassword;
    private TextInputLayout usernameWrapper, passwordWrapper;
    private String id;
    private String user;
    private String password;
    private Button btn_login, btn_register;
    private String username;
    private String value;

    public LoginFragment() {

        realmManager = new RealmManager();

    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_login, parentViewGroup, false);
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id
                .login);
        usernameWrapper = (TextInputLayout) view.findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) view.findViewById(R.id.passwordWrapper);
        inputEmail = (EditText) view.findViewById(R.id.username);
        inputPassword = (EditText) view.findViewById(R.id.password);
        loginLayout = (CoordinatorLayout) view.findViewById(R.id.login);
        usernameWrapper.setHint(getString(R.string.hint_email));
        passwordWrapper.setHint(getString(R.string.hint_password));
        lActivity = (LoginActivity) getActivity();
        btn_login = (Button) view.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                user = usernameWrapper.getEditText().getText().toString().trim();
                password = passwordWrapper.getEditText().getText().toString().trim();

                if (!haveNetworkConnection()) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                    alertDialog.setTitle("No Internet");

                    alertDialog.setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                                }
                            });


                    alertDialog.setNegativeButton("Exit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    dialog.cancel();
                                }
                            });


                    alertDialog.show();

                }

                if (submitForm()) {

                    RealmResults<Login> validateUser = realmManager.getAllUsers();

                    for (Login c : validateUser) {

                        if (c.getEmail().equals(user) && c.getPassword().equals(password)) {

                            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("username", user);
                            editor.commit();

                            Intent i = new Intent(getActivity(), FlightPlanActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

                            Log.d("TRUE", c.getEmail());


                        }


                    }


                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, getString(R.string.msg_error_login_access), Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
                    snackbar.show();

                }

            }


        });

        return view;
    }

    private boolean submitForm() {


        if (!validateEmail()) {
            return false;
        }

        return validatePassword();


    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            usernameWrapper.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            usernameWrapper.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            passwordWrapper.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            passwordWrapper.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            lActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {

            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            switch (view.getId()) {

                case R.id.username:
                    validateEmail();
                    break;
                case R.id.password:
                    validatePassword();
                    break;

            }

        }
    }
}

