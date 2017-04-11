package app.ar.labtiassistant.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by ghost on 08/04/17.
 */

public class MyPreferences {

    private static final String PREF_NAME = "LabtiAsisten";

    private static final String KEY_HAS_SET_PIN = "isSetPin";
    private static final String KEY_PIN = "pin";
    private static final String KEY_REKAPAN = "rekapan";
    private static final String KEY_TOTAL_REKAPAN = "total_rekapan";

    private static final String KEY_ACCESS_TOKEN = "access_token";


    // LogCat tag
    private static String TAG = MyPreferences.class.getSimpleName();
    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    // Shared pref mode
    private int PRIVATE_MODE = 0;

    public MyPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setKeyHasSetPin(boolean pin){
        editor.putBoolean(KEY_HAS_SET_PIN, pin);
        editor.commit();
    }

    public boolean getKeyHasSetPin() {
        return pref.getBoolean(KEY_HAS_SET_PIN, false);
    }

    public void setKeyPin(String pin) {
        editor.putString(KEY_PIN, pin);
        editor.commit();

        Log.d(TAG, "User pin has set");
    }


    public void setKeyRekapan(String upah) {
        editor.putString(KEY_REKAPAN, upah);
        editor.commit();

        Log.d(TAG, "Rekapan has set");
    }

    public void setKeyTotalRekapan(String upah) {
        editor.putString(KEY_TOTAL_REKAPAN, upah);
        editor.commit();

        Log.d(TAG, "Total Rekapan has set");
    }

    public String getKeyPin() {
        return pref.getString(KEY_PIN, "0");
    }

    public String getKeyRekapan() {
        return pref.getString(KEY_REKAPAN, "0");
    }

    public String getKeyTotalRekapan() {
        return pref.getString(KEY_TOTAL_REKAPAN, "0");
    }


}