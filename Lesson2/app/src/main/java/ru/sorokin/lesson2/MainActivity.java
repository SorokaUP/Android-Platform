package ru.sorokin.lesson2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Core data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btn0)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn1)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn2)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn3)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn4)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn5)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn6)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn7)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn8)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btn9)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnC)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnCe)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnDel)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnDot)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnMinus)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnPlus)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnSplit)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnMultiply)).setOnClickListener(this::btnClick);
        ((Button)findViewById(R.id.btnEqually)).setOnClickListener(this::btnClick);

        data = new Core();
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

        ((TextView)findViewById(R.id.lbCurrentValue)).setText(data.getCurValue());
        ((TextView)findViewById(R.id.lbHistory)).setText(data.getHis());
    }
}