package others;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

    static String ct_userId = "USER_ID";
    static String ct_lastOpenedTeamId= "LAST_OPENED_TEAM_ID";  //the team for which we pressed the open button
    static String ct_lastOpenedTeamChanelId= "LAST_OPENED_TEAM_CHANEL_ID";
    static String ct_lastOpenedTeamPostReplyingId= "LAST_OPENED_TEAM_POST_REPLYING_ID";  //not the last post replied to, but the last post where reply was pressed, no matter if it was created or canceled the reply
    static String ct_lastTeamPressedEditId= "LAST_TEAM_PRESSED_EDIT_ID";  //the team for which we pressed the EDIT button the last time

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

    public static void saveLastOpenedTeamChanelId(Context context, String teamChanelId)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ct_lastOpenedTeamChanelId, teamChanelId);
        editor.apply();
    }

    public static String getLastOpenedTeamChanelId(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ct_lastOpenedTeamChanelId, null);
    }

    public static void saveLastOpenedTeamPostReplyingId(Context context, String teamPostId)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ct_lastOpenedTeamPostReplyingId, teamPostId);
        editor.apply();
    }

    public static String getLastOpenedTeamPostReplyingId(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ct_lastOpenedTeamPostReplyingId, null);
    }

    public static void saveLastTeamPressedEditId(Context context, String teamId)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ct_lastTeamPressedEditId, teamId);
        editor.apply();
    }

    public static String getLastTeamPressedEditId(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ct_lastTeamPressedEditId, null);
    }
}
