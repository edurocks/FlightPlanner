package dmanlancers.com.flightplanner.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("HHmm", Locale.getDefault()).format(calendar.getTime());
    }
}
