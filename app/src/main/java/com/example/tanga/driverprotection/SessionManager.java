package com.example.tanga.driverprotection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(_context.getResources().getString(R.string.prefs), PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, User user) {

        editor.putString(_context.getResources().getString(R.string.user_name), user.getFirstName());
     //   editor.putString("social", user.getSocialPlatform());
        editor.putInt(_context.getResources().getString(R.string.user_id), user.getId());
        editor.putString(_context.getResources().getString(R.string.user_lastname), user.getLastName());
        editor.putString(_context.getResources().getString(R.string.user_photo), user.getPhoto());
        editor.putString(_context.getResources().getString(R.string.user_mail), user.getEmail());
        editor.putString(_context.getResources().getString(R.string.user_password), user.getPassword());
     //   editor.putString(_context.getResources().getString(R.string.user_role), user.getRole());
      /*  if(!createdAccount)
        {
            editor.putString(_context.getResources().getString(R.string.user_role), user.getrole());
        }*/
        editor.putBoolean(_context.getResources().getString(R.string.isconnected), isLoggedIn);
        editor.apply();
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }
        public void setVehicule(int idcar)
        {
            editor.putInt("idcar", idcar);
            editor.apply();
            // commit changes
            editor.commit();
        }
        public int getVeichule()
        { int id = 0;
            SharedPreferences prefs = _context.getSharedPreferences(_context.getResources().getString(R.string.prefs), PRIVATE_MODE);
         id = prefs.getInt("idcar", 4);
         Log.d("sessionmanageridcar ",String.valueOf(id));
         return id;


        }
    public void setRole(User user) {

    //   editor.putString(_context.getResources().getString(R.string.user_role), user.getRole());
       editor.apply();
        // commit changes
        editor.commit();

        Log.d(TAG, "User role modified!");
    }

    public boolean getLogin(User user) {
        SharedPreferences prefs = _context.getSharedPreferences(_context.getResources().getString(R.string.prefs), PRIVATE_MODE);
        if(prefs.getBoolean(_context.getResources().getString(R.string.isconnected), false))
        {
            user.setFirstName(prefs.getString(_context.getResources().getString(R.string.user_name), null));
           // user.setSocialPlatform(prefs.getString("social", null));
            user.setPassword(prefs.getString(_context.getResources().getString(R.string.user_password), null));
            user.setEmail(prefs.getString(_context.getResources().getString(R.string.user_mail), null));
            user.setLastName(prefs.getString(_context.getResources().getString(R.string.user_lastname), null));
            user.setId(prefs.getInt(_context.getResources().getString(R.string.user_id), 0));
           // user.setRole(prefs.getString(_context.getResources().getString(R.string.user_role), null));
            user.setPhoto(prefs.getString(_context.getResources().getString(R.string.user_photo), null));
            Log.d(TAG, "User data retrieved!");
            return true;
        }
        return false;

    }
    public void logoff()
    {
        pref.edit().clear().commit();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        return  preferences.getString("FIREBASE_TOKEN", null);


    }
    public static void setToken(String token, Context context) {

        SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        preferences.edit().putString("FIREBASE_TOKEN", token).apply();

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(_context.getResources().getString(R.string.isconnected), false);
    }
}
