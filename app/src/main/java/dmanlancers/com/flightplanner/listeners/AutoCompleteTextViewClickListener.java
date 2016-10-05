package dmanlancers.com.flightplanner.listeners;


import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.AdapterView;

public class AutoCompleteTextViewClickListener implements AdapterView.OnItemClickListener {
    AppCompatAutoCompleteTextView mAutoComplete;
    AdapterView.OnItemClickListener mOriginalListener;

    public AutoCompleteTextViewClickListener(AppCompatAutoCompleteTextView acTextView,
                                             AdapterView.OnItemClickListener originalListener) {
        mAutoComplete = acTextView;
        mOriginalListener = originalListener;
    }

    public void onItemClick(AdapterView<?> adView, View view, int position,
                            long id) {
        mOriginalListener.onItemClick(adView, mAutoComplete, position, id);
    }
}
