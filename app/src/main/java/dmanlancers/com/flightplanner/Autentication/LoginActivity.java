package dmanlancers.com.flightplanner.Autentication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dmanlancers.com.flightplanner.R;
import dmanlancers.com.flightplanner.fragments.FlightPlannerFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadFlightPlanner();
    }

    private void loadFlightPlanner() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flight_plan_container, new FlightPlannerFragment())
                .commit();
    }
}







