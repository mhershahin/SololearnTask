package am.learn.task.utils;

import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class PhoneUtil {

    private static PhoneUtil INSTANCE = null;

    public static PhoneUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PhoneUtil();
        }
        return INSTANCE;
    }

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null)
        {
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                return true;
            }
            else
            {
                return true;
            }
        }
    }



}
