package ru.sorokin.lesson2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class AppThemeChooser extends AppCompatActivity {
    public static final String APP_THEME = "AppTheme";
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_NIGHT = 2;

    public static int currentThemeId = THEME_DEFAULT;


    // themeX = int THEME_
    public void setAppThemeX(int themeX) {
        //int themeId = themeX2ThemeId(themeX);

        // Устанавливаем режим работы приложения в режим НЕ/НОЧЬ
        switch (themeX)
        {
            case THEME_LIGHT:
                currentThemeId = themeX;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                setTheme(R.style.AppThemeLight);
                break;

            case THEME_NIGHT:
                currentThemeId = themeX;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                setTheme(R.style.AppThemeNight);
                break;

            default:
                currentThemeId = THEME_DEFAULT;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                setTheme(R.style.AppTheme);
                break;
        }



        // Пример сохранения темы из лекции про Ресурсы
        //getPreferences(Context.MODE_PRIVATE).edit().putInt(APP_THEME, themeX).apply();
        //setTheme(themeId);

        // Пересоздаем активити для применения темы
        //recreate();
    }

    public int themeX2ThemeId(int themeX) {
        switch (themeX) {
            case THEME_LIGHT:
                return R.style.AppThemeLight;

            case THEME_NIGHT:
                return R.style.AppThemeNight;

            default:
                return R.style.AppTheme;
        }
    }





















/*
    public void setAppTheme(int codeStyle) {
        switch (codeStyle)
        {
            case THEME_DEFAULT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;

            case THEME_NIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;

            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

        getPreferences(Context.MODE_PRIVATE).edit().putInt(APP_THEME, codeStyle).apply();
        recreate();

        //setTheme(codeStyleToStyleId(codeStyle));
    }

    public int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    public int getCodeStyle(int codeStyle) {
        return getPreferences(Context.MODE_PRIVATE).getInt(APP_THEME, codeStyle);
    }

    public static int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case THEME_LIGHT:
                return R.style.AppThemeLight;

            case THEME_NIGHT:
                return R.style.AppThemeNight;

            default:
                return R.style.AppTheme;
        }
    }
    */
}
