package others;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

    static String ct_userId = "USER_ID";
    static String ct_lastOpenedTeamId= "LAST_OPENED_TEAM_ID";

    public static void saveUserId(Context context, String userId)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ct_userId, userId);
        editor.apply();
    }

    public static String getUserId(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ct_userId, null);
    }

    public static void saveLastOpenedTeamId(Context context, String teamId)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ct_lastOpenedTeamId, teamId);
        editor.apply();
    }

    public static String getLastOpenedTeamId(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ct_lastOpenedTeamId, null);
    }
}
