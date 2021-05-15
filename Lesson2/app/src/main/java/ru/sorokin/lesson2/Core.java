package ru.sorokin.lesson2;

import java.io.Serializable;

public class Core implements Serializable {
    private float value;
    private String his;
    private char operation;
    private String oldValue;
    private String curValue;
    private boolean isNewValue;

    public Core()
    {
        his = "";
        operation = 'X';
        //oldValue = "";
        curValue = "0";
        isNewValue = false;
        value = 0f;
    }

    public void input(char x) {
        // Числа
        if (Character.isDigit(x))
        {
            setCurValue(x, true);
            isNewValue = true;
        }
        // Операции
        else
        {
            switch (x)
            {
                case '+':
                    setOperation(x);
                    break;

                case '-':
                    setOperation(x);
                    break;

                case '*':
                    setOperation(x);
                    break;

                case '/':
                    setOperation(x);
                    break;

                case ',':
                    // TODO: Доделать как нибудь...
                    break;

                case '=':
                    setOperation(x);
                    go();
                    break;
            }
        }
    }

    public void delete() {
        setCurValue('0', false);
        isNewValue = true;
    }

    public void clearCurrent() {
        curValue = "0";
        isNewValue = false;
    }

    public void clearAll() {
        clearHis();
        clearCurrent();
        value = 0f;
        operation = 'X';
    }

    private void setCurValue(char x, boolean isAdd) {
        if (curValue == null)
            curValue = "";

        if (isAdd)
        {
            if (isNewValue == false)
            {
                curValue = String.valueOf(x);
                return;
            }
            curValue = (curValue == "0") ? String.valueOf(x) : curValue + x;
        }
        else
            curValue = (curValue.length() > 1) ? curValue.substring(0, curValue.length()-1) : "0";
    }

    private void setOperation(char x) {
        operation = x;
        addHis();
        isNewValue = false;
    }

    private void addHis() {
        if (his == "")
        {
            his = curValue + operation;
        }
        else
        {
            if (isNewValue)
                his += curValue + operation;
            else
                his = his.substring(0, his.length()-1) + operation;
        }
    }

    public void clearHis() {
        his = "";
    }

    public String getHis() {
        return his;
    }

    public String getCurValue() {
        return curValue;
    }

    private void go() {
        float x = 0f;
        String f = his;
        String a = "";
        char o = ' ';
        char oldO = ' ';
        boolean isFirst = true;
        boolean isFirstBefore = true;

        do {
            a = "";
            o = ' ';

            for (char c : f.toCharArray()) {
                if (Character.isDigit(c))
                    a += c;
                else {
                    o = c;
                    break;
                }
            }

            f = (o != '=') ? f.substring(f.indexOf(o) + 1) : "";
            x = Float.parseFloat(a);

            if (o == '=')
            {
                for (int i = his.length()-1; i >= 0; i--) {
                    if (his.charAt(i) == '=' || Character.isDigit(his.charAt(i)))
                        continue;
                    else
                    {
                        o = his.charAt(i);
                        break;
                    }
                }
            }

            if (isFirst)
            {
                value = x;
                oldO = o;
            }
            else
            {
                if (o != '=')
                {
                    char tempO = o;
                    o = oldO;
                    oldO = tempO;
                }

                switch (o)
                {
                    case '+':
                        value += x;
                        break;

                    case '-':
                        value -= x;
                        break;

                    case '*':
                        value *= x;
                        break;

                    case '/':
                        value /= x;
                        break;
                }
            }

            isFirst = false;
        }
        while (f.length() > 0);

        curValue = String.valueOf(value);
        clearHis();
    }

    private String helperSubstring(String s, char c) {
        if (c == '=')
            return "";

        int start = s.indexOf(c) + 1;

        String res = s.substring(start);
        return res;
    }
}
