package com.workshop.hilpitome.flightscheduler.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {
    //    Shared Preferences
    private SharedPreferences sharedPreferences;

    //    Shared preferences editor
    private SharedPreferences.Editor editor;

    //    Context
    private Context mContext;

    //    SharedPreferences mode
    private int PRIVATE_MODE = 0;

    //    SharedPreferences file name TODO: the string to be renamned later

    private static final String PREF_NAME = "flightSchedulerPrefs";

    private static final String KEY_IS_LOGGED_IN = "isUserLoggedIn";

    private static final String KEY_ACCESS_TOKEN = "access_token";

    private static final String KEY_TOKEN_TYPE = "token_type";

    /**
     * @method .edit() enables saving and deleting data from shared preferences
     */
    public PrefUtils(Context mContext) {
        this.mContext = mContext;
        this.sharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.sharedPreferences.edit();
    }


    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    //    Setters
    public void setKeyTokenType(String token_type) {
        editor.putString(KEY_TOKEN_TYPE, token_type);
        editor.commit();
    }

    void setKeyAccessToken(String access_token) {
        editor.putString(KEY_ACCESS_TOKEN, access_token);
        editor.commit();
    }

    //Getters
    public String getKeyTokenType() {
        return sharedPreferences.getString(KEY_TOKEN_TYPE, null);
    }

    public String getKeyAccessToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }


    public void logout() {
        editor.remove(KEY_TOKEN_TYPE);
        editor.remove(KEY_ACCESS_TOKEN);
        editor.commit();
    }

    // Save user data to the shared preference
    public void storeUserDetails(String token_type, String access_token, String refresh_token) {
        editor.putString(KEY_TOKEN_TYPE, token_type);
        editor.putString(KEY_ACCESS_TOKEN, access_token);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }
}
