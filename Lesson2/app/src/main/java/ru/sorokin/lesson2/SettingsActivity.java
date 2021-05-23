package ru.sorokin.lesson2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppThemeChooser {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppThemeX((savedInstanceState != null) ? savedInstanceState.getInt(APP_THEME) : THEME_DEFAULT);
        setContentView(R.layout.activity_settings);

        initRadioGroup();
        setRadioGroupFromIntent();
    }

    private void initRadioGroup() {

        findViewById(R.id.rbThemeSystem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(APP_THEME, THEME_DEFAULT);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.rbThemeLight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(APP_THEME, THEME_LIGHT);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        findViewById(R.id.rbThemeNight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(APP_THEME, THEME_NIGHT);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private void setRadioGroupFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            int themeId = extras.getInt(APP_THEME, THEME_DEFAULT);
            switch (themeId) {
                case THEME_DEFAULT:
                    ((RadioButton) findViewById(R.id.rbThemeSystem)).setChecked(true);
                    break;

                case THEME_LIGHT:
                    ((RadioButton) findViewById(R.id.rbThemeLight)).setChecked(true);
                    break;

                case THEME_NIGHT:
                    ((RadioButton) findViewById(R.id.rbThemeNight)).setChecked(true);
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(APP_THEME, currentThemeId);
    }
}