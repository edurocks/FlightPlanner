package dmanlancers.com.flightplanner.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import dmanlancers.com.flightplanner.R;

public class Utils {

    private static final String PREFS_NAME = "MyApp_Settings";

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("HHmm", Locale.getDefault()).format(calendar.getTime());
    }

    public static void setTime(Context context, final AppCompatTextView appCompatTextView) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String formattedMinute = null;
                String formattedHour = null;
                String time;

                if (minute <= 9) {
                    formattedMinute = "0" + minute;
                } else if (minute > 9) {
                    formattedMinute = "" + minute;
                }

                if (hour <= 9) {
                    formattedHour = "0" + hour;
                } else if (hour > 9) {
                    formattedHour = "" + hour;
                }

                time = formattedHour + "" + formattedMinute;
                appCompatTextView.setText(time);
            }
        }, hour, minutes, true);
        timePicker.setTitle(context.getString(R.string.choose_time));
        timePicker.show();
    }

    public static void setDate(Context context, final AppCompatTextView appCompatTextView) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String formattedDay = null;
                String formattedMonth = null;
                StringBuilder stringBuilder = new StringBuilder();
                String date;

                if (day <= 9) {
                    formattedDay = "0" + day;
                } else if (day > 9) {
                    formattedDay = "" + day;
                }

                if (month + 1 <= 9) {
                    formattedMonth = stringBuilder.append("0").append(month + 1).toString();
                } else if (month + 1 > 9) {
                    formattedMonth = stringBuilder.append(month + 1).toString();
                }

                date = year + "" + formattedMonth + "" + formattedDay;
                appCompatTextView.setText(date);

            }
        }, year, month, day);
        datePicker.setTitle(context.getString(R.string.choose_date));
        datePicker.show();
    }

    public static void sendEmail(Context context, String destinationEmail, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{destinationEmail});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            context.startActivity(Intent.createChooser(emailIntent, context.getResources().getString(R.string.send_email_chooser)));
        } catch (ActivityNotFoundException e) {
            Log.e("Error", "No program do send email.");
        }
    }

    public static boolean matchFlightCodePattern(AppCompatEditText appCompatEditText) {
        Pattern pattern = Pattern.compile("[A-Z]{3}\\d{4}");
        return pattern.matcher(appCompatEditText.getText().toString()).matches();
    }

    public static boolean validateAirportCode(AppCompatAutoCompleteTextView origin, AppCompatAutoCompleteTextView destination) {
        return origin.getText().toString().isEmpty() || destination.getText().toString().isEmpty();
    }

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null) { // connected to the internet
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                if (netInfo.isConnected())
                    haveConnectedWifi = true;
            } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (netInfo.isConnected())
                    haveConnectedMobile = true;
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static void putSharedPrefs(Context context, String key, String text) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public static String getSharedPrefs(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    public static void showDialog(Context context, String title, String body, String message, String negativeMessage, String positiveMessage, AlertDialog.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(body + " " + message + "?")
                .setNegativeButton(negativeMessage, listener)
                .setPositiveButton(positiveMessage, listener)
                .create().show();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void startActivity(Context context, Class<?> appCompatActivity) {
        Intent i = new Intent(context, appCompatActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
