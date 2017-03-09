package app.holachef.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {
    private static final String TAG = "StringUtils";

    public static String formatRsString(String totalRs)
    {
        String formatString="";
        try {
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,##,##,##,###");
            formatString = String.format("₹ " + formatter.format(Double.parseDouble(totalRs)));
        } catch (Exception e) {
            Log.i(TAG, "formatRsString: "+e.getMessage());
        }

        return formatString;
    }
}
