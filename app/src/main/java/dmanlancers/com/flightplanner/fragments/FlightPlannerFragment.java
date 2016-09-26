package dmanlancers.com.flightplanner.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dmanlancers.com.flightplanner.R;

public class FlightPlannerFragment extends Fragment {


    public FlightPlannerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flight_planner, container, false);
    }

}
