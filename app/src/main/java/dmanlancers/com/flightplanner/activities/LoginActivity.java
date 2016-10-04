package dmanlancers.com.flightplanner.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import dmanlancers.com.flightplanner.R;
import dmanlancers.com.flightplanner.fragments.LoginFragment;
import dmanlancers.com.flightplanner.managers.RealmManager;
import dmanlancers.com.flightplanner.model.Login;
import io.realm.RealmResults;


public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyApp_Settings";
    RealmManager realmManager;
    private String username;
    private String value;

    public LoginActivity() {

        realmManager = new RealmManager();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionStored();
        loadFlightPlanner();
    }


    private void sessionStored() {

        SharedPreferences settings = LoginActivity.this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        value = settings.getString("username", "");
        Log.e("Shared", value);
        RealmResults<Login> validateSession = realmManager.getAllUsers();

        for (Login c : validateSession) {
            if (value != "" && value.equals(c.getEmail())) {
                Intent myIntent = new Intent(LoginActivity.this, FlightPlanActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(myIntent, 0);
            }
        }


    }
    private void loadFlightPlanner() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_container, new LoginFragment())
                .commit();
    }
}







