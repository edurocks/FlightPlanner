package dmanlancers.com.flightplanner.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import dmanlancers.com.flightplanner.R;
import dmanlancers.com.flightplanner.activities.FlightPlanActivity;
import dmanlancers.com.flightplanner.managers.RealmManager;
import dmanlancers.com.flightplanner.model.AirportCode;
import dmanlancers.com.flightplanner.model.MessageType;
import dmanlancers.com.flightplanner.utils.Utils;
import io.realm.RealmResults;

public class FlightPlannerFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private RealmManager realmManager;
    private AppCompatTextView mCurrentDate;
    private AppCompatTextView mCurrentTime;
    private AppCompatSpinner mMessageType;
    private AppCompatSpinner mOriginAirport;
    private AppCompatSpinner mDestinationAirport;
    private FlightPlanActivity mActivity;
    private String mMessageTypeSelectedValue;
    private String mOriginAirportValue;
    private String mDestinationAirportValue;
    private AppCompatEditText mFlightCode;
    private String mDestinationEmail;
    private LinearLayout mFlightPlanLayout;

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
        mFlightCode = (AppCompatEditText) view.findViewById(R.id.flight_code);
        AppCompatButton mSendEmail = (AppCompatButton) view.findViewById(R.id.send_email);
        mFlightPlanLayout = (LinearLayout) view.findViewById(R.id.flight_plan_layout);
        mMessageType.setOnItemSelectedListener(this);
        mOriginAirport.setOnItemSelectedListener(this);
        mDestinationAirport.setOnItemSelectedListener(this);
        mSendEmail.setOnClickListener(this);
        populateAirportCodeSpinner();
        populateMessageTypeSpinner();
        populateDateAndTime();
    }

    private void populateDateAndTime() {
        mCurrentDate.setText(Utils.getCurrentDate());
        mCurrentTime.setText(Utils.getCurrentTime());
    }

    private void populateMessageTypeSpinner() {
        RealmResults<MessageType> results = realmManager.getAllMessageType();
        List<String> messageType = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            messageType.add(results.get(i).getMessageType());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mActivity,
                android.R.layout.simple_spinner_item, messageType);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMessageType.setAdapter(arrayAdapter);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

        switch (adapterView.getId()) {
            case R.id.spinner_message_type:
                mMessageTypeSelectedValue = adapterView.getItemAtPosition(pos).toString();
                selectDestinationEmail(pos);
                break;

            case R.id.origin_airport_code:
                mOriginAirportValue = adapterView.getItemAtPosition(pos).toString();
                break;

            case R.id.destination_airport_code:
                mDestinationAirportValue = adapterView.getItemAtPosition(pos).toString();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void sendEmail() {
        Log.e("template do email", "(" + mMessageTypeSelectedValue + "-" + mFlightCode.getText().toString().toUpperCase()
                + "-" + mOriginAirportValue + mCurrentTime.getText().toString() + "-" + mDestinationAirportValue
                + "/" + mCurrentDate.getText().toString() + ")");
    }

    @Override
    public void onClick(View view) {
        if (!Utils.validateFlightCode(mFlightCode)) {
            Utils.sendEmail(getActivity(), mDestinationEmail, getString(R.string.email_subject),
                    String.format(getResources().getString(R.string.email_template),
                            mMessageTypeSelectedValue, mFlightCode.getText().toString().toUpperCase()
                            , mOriginAirportValue + mCurrentTime.getText().toString(), mDestinationAirportValue
                            , mCurrentDate.getText().toString()));
        } else {
            Snackbar.make(mFlightPlanLayout, R.string.flight_code_error_message, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void selectDestinationEmail(int pos) {
        switch (pos) {
            case 0:
                mDestinationEmail = "fjvvasco@hotmail.com";
                break;
            case 1:
                mDestinationEmail = "celsorodrigues@sapo.pt";
                break;
        }
    }
}
