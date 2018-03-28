package e.kefch_000.privacystealer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by kefch_000 on 3/28/2018.
 */

public abstract class DataSaver {

    public static boolean addFavoriteItem(Activity activity,String favoriteItem) {
        String favoriteList = null;
        try {
            favoriteList = getStringFromPreferences(activity, null, "favorites");
            if (favoriteList != null) {
                favoriteList = favoriteList + "," + favoriteItem;
            } else {
                favoriteList = favoriteItem;
            }

        }
        catch (Exception e)
        {
            Log.v("TAG","datasaver add favourite item");
        }
        Log.v("TAG", "Sent request to save data");
        return putStringInPreferences(activity, favoriteList, "favorites");

    }
    public static String[] getFavoriteList(Activity activity){
        String favoriteList = getStringFromPreferences(activity,null,"favorites");

            return convertStringToArray(favoriteList);

    }
    private static boolean putStringInPreferences(Activity activity,String nick,String key){
        SharedPreferences sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, nick);
        editor.commit();
        Log.v("TAG", "saved data");
        return true;
    }
    private static String getStringFromPreferences(Activity activity, String defaultValue, String key){
        SharedPreferences sharedPreferences = activity.getPreferences(Activity.MODE_PRIVATE);
        String temp = sharedPreferences.getString(key, defaultValue);
        Log.v("TAG", "Executed getting saved data");
        return temp;
    }

    private static String[] convertStringToArray(String str){

            String[] arr = str.split(", ");
            return arr;


    }
}
