package id.rumahawan.belajarfisika.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Session {
    private static final String SHARED_PREFERENCES = "shared_preferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Session(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setSessionString(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }
    public String getSessionString(String key){
        return sharedPreferences.getString(key, null);
    }

    public void setSessionInt(String key, int value){
        editor.putInt(key, value);
        editor.commit();
    }
    public int getSessionInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public void setSessionArraylistString(String key, ArrayList<String> arrayList){
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<String> getSessionArraylistString(String key){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void removeSession(String key){
        editor.remove(key);
        editor.commit();
    }

    public boolean isExist(String key){
        return sharedPreferences.contains(key);
    }

}
