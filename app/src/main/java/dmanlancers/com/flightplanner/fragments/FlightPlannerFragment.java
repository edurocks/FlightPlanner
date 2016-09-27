package dmanlancers.com.flightplanner.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import dmanlancers.com.flightplanner.R;
import dmanlancers.com.flightplanner.activities.FlightPlanActivity;
import dmanlancers.com.flightplanner.managers.RealmManager;
import dmanlancers.com.flightplanner.model.AirportCode;
import dmanlancers.com.flightplanner.utils.Utils;
import io.realm.RealmResults;

public class FlightPlannerFragment extends Fragment {

    private RealmManager realmManager;
    private AppCompatTextView mCurrentDate;
    private AppCompatTextView mCurrentTime;
    private AppCompatSpinner mMessageType;
    private AppCompatSpinner mOriginAirport;
    private AppCompatSpinner mDestinationAirport;
    private FlightPlanActivity mActivity;

    public FlightPlannerFragment() {
        realmManager = new RealmManager();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = (FlightPlanActivity) getActivity();
        return inflater.inflate(R.layout.fragment_flight_planner, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCurrentDate = (AppCompatTextView) view.findViewById(R.id.date);
        mCurrentTime = (AppCompatTextView) view.findViewById(R.id.time);
        mMessageType = (AppCompatSpinner) view.findViewById(R.id.spinner_message_type);
        mOriginAirport = (AppCompatSpinner) view.findViewById(R.id.origin_airport_code);
        mDestinationAirport = (AppCompatSpinner) view.findViewById(R.id.destination_airport_code);
        populateAirportCodeSpinner();
        mCurrentDate.setText(Utils.getCurrentDate());
        mCurrentTime.setText(Utils.getCurrentTime());
    }

    private void populateAirportCodeSpinner() {
        RealmResults<AirportCode> results = realmManager.getAllAirportCode();
        List<String> airportCode = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            airportCode.add(results.get(i).getAirportCode());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_spinner_item, airportCode);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOriginAirport.setAdapter(arrayAdapter);
        mDestinationAirport.setAdapter(arrayAdapter);
    }
}
