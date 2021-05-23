package ru.sorokin.lesson2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;

public class MainActivity extends AppThemeChooser {

    public static final String DATA_PARAM = "CoreData";
    public static final String APP_SETTINGS = "AppSettings";
    private Core data;
    private final static int SETTINGS_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppThemeX((savedInstanceState != null) ? savedInstanceState.getInt(APP_THEME) : currentThemeId);
        setContentView(R.layout.activity_main);

        buttonSetOnClick(R.id.btn0);
        buttonSetOnClick(R.id.btn1);
        buttonSetOnClick(R.id.btn2);
        buttonSetOnClick(R.id.btn3);
        buttonSetOnClick(R.id.btn4);
        buttonSetOnClick(R.id.btn5);
        buttonSetOnClick(R.id.btn6);
        buttonSetOnClick(R.id.btn7);
        buttonSetOnClick(R.id.btn8);
        buttonSetOnClick(R.id.btn9);
        buttonSetOnClick(R.id.btnC);
        buttonSetOnClick(R.id.btnCe);
        buttonSetOnClick(R.id.btnDel);
        buttonSetOnClick(R.id.btnDot);
        buttonSetOnClick(R.id.btnMinus);
        buttonSetOnClick(R.id.btnPlus);
        buttonSetOnClick(R.id.btnSplit);
        buttonSetOnClick(R.id.btnMultiply);
        buttonSetOnClick(R.id.btnEqually);

        data = (savedInstanceState != null) ? (Core) savedInstanceState.getSerializable(DATA_PARAM) : new Core();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuSettings:
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra(APP_THEME, currentThemeId);
                startActivityForResult(intent, SETTINGS_REQUEST_CODE );
                break;

            case R.id.menuThemeLight:
                setAppThemeX(AppThemeChooser.THEME_LIGHT);
                break;

            case R.id.menuThemeNight:
                setAppThemeX(AppThemeChooser.THEME_NIGHT);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != SETTINGS_REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                currentThemeId = extras.getInt(APP_THEME);
                recreate();
            }
        }
    }

    private void buttonSetOnClick(int id) {
        ((Button) findViewById(id)).setOnClickListener(this::btnClick);
    }

    public void btnClick(View v)
    {
        Button btn = (Button)v;

        switch (v.getId())
        {
            case R.id.btnC:
                data.clearAll();
                break;

            case R.id.btnCe:
                data.clearCurrent();
                break;

            case R.id.btnDel:
                data.delete();
                break;

            case R.id.btnDot:
                data.input(',');
                break;

            case R.id.btnEqually:
                data.input('=');
                break;

            case R.id.btnMinus:
                data.input('-');
                break;

            case R.id.btnMultiply:
                data.input('*');
                break;

            case R.id.btnPlus:
                data.input('+');
                break;

            case R.id.btnSplit:
                data.input('/');
                break;

            default:
                data.input(btn.getText().toString().charAt(0));
                break;
        }

        showData(data.getHis(), data.getCurValue());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DATA_PARAM, data);
        outState.putSerializable(APP_THEME, currentThemeId);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        data = (Core) savedInstanceState.getSerializable(DATA_PARAM);

        showData(data.getHis(), data.getCurValue());
    }

    private void showData(String his, String curValue) {
        ((TextView) this.findViewById(R.id.lbHistory)).setText(his);
        ((TextView) this.findViewById(R.id.lbCurrentValue)).setText(curValue);
    }
}