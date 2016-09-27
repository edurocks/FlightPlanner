package dmanlancers.com.flightplanner.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dmanlancers.com.flightplanner.R;

public class Utils {

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("HHmm", Locale.getDefault()).format(calendar.getTime());
    }

    public static void sendEmail(Context context, String destinationEmail, String subject, String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, destinationEmail);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            context.startActivity(Intent.createChooser(emailIntent, context.getResources().getString(R.string.send_email_chooser)));
        } catch (ActivityNotFoundException e) {
            Log.e("Error", "Sem programa para instalar o email.");
        }
    }
}
