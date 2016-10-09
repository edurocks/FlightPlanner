package dmanlancers.com.flightplanner.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import dmanlancers.com.flightplanner.R;
import dmanlancers.com.flightplanner.activities.FlightPlanActivity;
import dmanlancers.com.flightplanner.activities.LoginActivity;
import dmanlancers.com.flightplanner.managers.RealmManager;
import dmanlancers.com.flightplanner.model.Login;
import dmanlancers.com.flightplanner.utils.Utils;
import io.realm.RealmResults;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private final RealmManager realmManager;
    private LoginActivity mActivity;
    private AppCompatEditText inputEmail, inputPassword;
    private TextInputLayout usernameWrapper, passwordWrapper;
    private LinearLayout loginLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

    public LoginFragment() {
        realmManager = new RealmManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, parentViewGroup, false);
        mActivity = (LoginActivity) getActivity();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mActivity);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameWrapper = (TextInputLayout) view.findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) view.findViewById(R.id.passwordWrapper);
        inputEmail = (AppCompatEditText) view.findViewById(R.id.username);
        inputPassword = (AppCompatEditText) view.findViewById(R.id.password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login);
        usernameWrapper.setHint(getString(R.string.hint_email));
        passwordWrapper.setHint(getString(R.string.hint_password));
        AppCompatButton mBtnlogin = (AppCompatButton) view.findViewById(R.id.btn_login);
        mBtnlogin.setOnClickListener(this);
    }

    private boolean submitForm() {
        return validateEmail() && validatePassword();
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !Utils.isValidEmail(email)) {
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
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {

        @SuppressWarnings("ConstantConditions")
        String user = usernameWrapper.getEditText().getText().toString().trim();
        @SuppressWarnings("ConstantConditions")
        String password = passwordWrapper.getEditText().getText().toString().trim();



        if (submitForm()) {

            RealmResults<Login> validateUser = realmManager.getAllUsers();

            for (Login c : validateUser) {

                if (c.getEmail().equals(user) && c.getPassword().equals(password)) {

                    Utils.putSharedPrefs(mActivity, "username", user);

                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.VALUE, user);
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);

                    Utils.startActivity(mActivity, FlightPlanActivity.class);
                }
            }
        } else {
            Snackbar snackbar = Snackbar
                    .make(loginLayout, getString(R.string.msg_error_login_access), Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.RED);
            snackbar.show();
        }
    }
}

