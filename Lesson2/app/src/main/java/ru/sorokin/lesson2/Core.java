package ru.sorokin.lesson2;

import java.io.Serializable;

public class Core implements Serializable {
    private float value;
    private String his;
    private char operation;
    private String oldValue;
    private String curValue;
    private boolean isNewValue;

    public final char PLUS = '+';
    public final char MINUS = '-';
    public final char MULTIPLY = '*';
    public final char SPLIT = '/';
    public final char DOT = ',';
    public final char CALCULATE = '=';

    public final char EMPTY = 'X';

    public Core()
    {
        his = "";
        operation = EMPTY;
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
                case PLUS:
                    setOperation(x);
                    break;

                case MINUS:
                    setOperation(x);
                    break;

                case MULTIPLY:
                    setOperation(x);
                    break;

                case SPLIT:
                    setOperation(x);
                    break;

                case DOT:
                    // TODO: Доделать как нибудь...
                    break;

                case CALCULATE:
                    setOperation(x);
                    calculation();
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
        operation = EMPTY;
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
            curValue = (curValue.equals("0")) ? String.valueOf(x) : curValue + x;
        }
        else
        {
            curValue = (curValue.length() > 1) ? curValue.substring(0, curValue.length()-1) : "0";
        }
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

    private void calculation() {
        float x = 0f;
        String formula = his;
        String curParam = "";
        char curOperation = EMPTY;
        char oldOperation = EMPTY;
        boolean isFirst = true;

        do {
            curParam = "";
            curOperation = EMPTY;

            for (char c : formula.toCharArray()) {
                if (Character.isDigit(c))
                    curParam += c;
                else {
                    curOperation = c;
                    break;
                }
            }

            formula = helperFormulaChange(formula, curOperation);
            x = Float.parseFloat(curParam);

            if (curOperation == CALCULATE)
            {
                curOperation = helperGetLastOperation();
            }

            if (isFirst)
            {
                value = x;
                oldOperation = curOperation;
                isFirst = false;
            }
            else
            {
                oldOperation = calculationProcess(x, oldOperation, curOperation);
            }
        }
        while (formula.length() > 0);

        curValue = String.valueOf(value);
        clearHis();
    }

    private String helperFormulaChange(String formula, char curOperation) {
        return (curOperation != CALCULATE) ? formula.substring(formula.indexOf(curOperation) + 1) : "";
    }

    private char helperGetLastOperation() {
        for (int i = his.length()-1; i >= 0; i--) {
            if (his.charAt(i) == CALCULATE || Character.isDigit(his.charAt(i)))
                continue;
            else
            {
                return his.charAt(i);
            }
        }

        return EMPTY;
    }

    private char calculationProcess(float x, char oldOperation, char curOperation) {
        if (curOperation != CALCULATE)
        {
            char tempO = curOperation;
            curOperation = oldOperation;
            oldOperation = tempO;
        }

        switch (curOperation)
        {
            case PLUS:
                value += x;
                break;

            case MINUS:
                value -= x;
                break;

            case MULTIPLY:
                value *= x;
                break;

            case SPLIT:
                value /= x;
                break;
        }

        return oldOperation;
    }
}
