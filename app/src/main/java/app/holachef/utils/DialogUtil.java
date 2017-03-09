package app.holachef.utils;

import android.content.Context;
import android.view.WindowManager;

import app.holachef.R;

public class DialogUtil {

    /**
     * Displays no network alert dialog. Msg - You are not connected to the
     * internet. Please check your internet connection. title - app name
     *
     * @param ctx
     */
    public static void showNoNetworkAlert(Context ctx) {
        try {
            new android.app.AlertDialog.Builder(ctx).setTitle(R.string.app_name).setMessage(R.string.no_internet)
                    .setPositiveButton(R.string.ok, null).create().show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

}
