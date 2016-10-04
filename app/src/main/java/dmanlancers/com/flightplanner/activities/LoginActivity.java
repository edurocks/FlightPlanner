package dmanlancers.com.flightplanner.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dmanlancers.com.flightplanner.R;
import dmanlancers.com.flightplanner.fragments.LoginFragment;
import dmanlancers.com.flightplanner.managers.RealmManager;
import dmanlancers.com.flightplanner.model.Login;
import dmanlancers.com.flightplanner.utils.Utils;
import io.realm.RealmResults;


public class LoginActivity extends AppCompatActivity {

    private RealmManager realmManager;

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

        String value = Utils.getSharedPrefs(this, "username");

        RealmResults<Login> validateSession = realmManager.getAllUsers();

        for (Login c : validateSession) {
            if (!value.equals("") && value.equals(c.getEmail())) {
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







