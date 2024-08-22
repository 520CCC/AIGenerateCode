package com.doow.rubbish;

import android.content.Context;
import android.content.SharedPreferences;

//国际化 多国语言工具，此方法布局全用java实现，不能用xml
public class SharedPreferencesUtil {




    public static String getLanguageLocal() {
        SharedPreferences userInfo = LuckyCApp.mApp.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return userInfo.getString("language", "");

    }

    public static void saveLanguageLocal(String language) {
        SharedPreferences sharedPreferences = LuckyCApp.mApp.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language);
        editor.commit();

    }

}
