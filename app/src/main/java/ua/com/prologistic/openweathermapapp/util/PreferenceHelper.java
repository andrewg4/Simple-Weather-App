package ua.com.prologistic.openweathermapapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import ua.com.prologistic.openweathermapapp.R;

public class PreferenceHelper {

    public static final String PREF_KEY_LOCATION = "pref_key_location";

    private static PreferenceHelper instance;

    private Context context;

    private SharedPreferences preferences;

    public PreferenceHelper() {
    }

    public static PreferenceHelper getInstance() {
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return preferences.getString(key, context.getString(R.string.default_location_value));
    }

}
