package ru.sorokin.lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CaN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_n);
    }

    public void goClick(View view) {
        Toast.makeText(getApplicationContext(),
                "Да начнуться ГОЛОДНЫЕ ИГРЫ",
                Toast.LENGTH_SHORT).show();

        TextView infoTextView = (TextView)findViewById(R.id.tvHeader);
        infoTextView.setText("Игра началась");

        setButton(R.id.r0c0, DOT_EMPTY, true);
        setButton(R.id.r0c1, DOT_EMPTY, true);
        setButton(R.id.r0c2, DOT_EMPTY, true);

        setButton(R.id.r1c0, DOT_EMPTY, true);
        setButton(R.id.r1c1, DOT_EMPTY, true);
        setButton(R.id.r1c2, DOT_EMPTY, true);

        setButton(R.id.r2c0, DOT_EMPTY, true);
        setButton(R.id.r2c1, DOT_EMPTY, true);
        setButton(R.id.r2c2, DOT_EMPTY, true);


        refreshMap();
    }

    public void rcClick(View v)
    {
        int r = 0;
        int c = 0;

        switch (v.getId())
        {
            case R.id.r0c0:
                r = 0;
                c = 0;
                break;
            case R.id.r0c1:
                r = 0;
                c = 1;
                break;
            case R.id.r0c2:
                r = 0;
                c = 2;
                break;
            case R.id.r1c0:
                r = 1;
                c = 0;
                break;
            case R.id.r1c1:
                r = 1;
                c = 1;
                break;
            case R.id.r1c2:
                r = 1;
                c = 2;
                break;
            case R.id.r2c0:
                r = 2;
                c = 0;
                break;
            case R.id.r2c1:
                r = 2;
                c = 1;
                break;
            case R.id.r2c2:
                r = 2;
                c = 2;
                break;
        }

        setRC(r, c, v.getId(), DOT_X);
    }

    private static int getIdByXY(int x, int y)
    {
        if (x == 0 && y == 0) return R.id.r0c0;
        if (x == 0 && y == 1) return R.id.r0c1;
        if (x == 0 && y == 2) return R.id.r0c2;

        if (x == 1 && y == 0) return R.id.r1c0;
        if (x == 1 && y == 1) return R.id.r1c1;
        if (x == 1 && y == 2) return R.id.r1c2;

        if (x == 2 && y == 0) return R.id.r2c0;
        if (x == 2 && y == 1) return R.id.r2c1;
        if (x == 2 && y == 2) return R.id.r2c2;

        return 0;
    }

    public static int MAP_SIZE = 3;
    public static int DOTS_TO_WIN = 3;
    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static char[][] map;
    public static Random rd = new Random();

    private void setRC(int r, int c, int id, char DOT)
    {
        boolean winHuman = false;
        boolean winAi = false;
        boolean draw = false;

        // Ход игрока
        setButton(id, DOT, false);
        stepHuman(r, c);
        // Проверка на победу
        winHuman = checkWin(DOT_X);
        if (winHuman)
        {
            theEnd(DOT_X);
            return;
        }
        // Проверка на ничью
        draw = checkDraw();
        if (draw) {
            theEnd(DOT_EMPTY);
            return;
        }
        // Ход ИИ
        stepAi();
        // Проверка на победу
        winAi = checkWin(DOT_O);
        if (winAi) {
            theEnd(DOT_O);
            return;
        }
        // Проверка на  ничью
        draw = checkDraw();
        if (draw) {
            theEnd(DOT_EMPTY);
            return;
        }
    }

    private void theEnd(char DOT_WIN)
    {
        TextView tvHeader = (TextView)findViewById(R.id.tvHeader);

        switch (DOT_WIN)
        {
            case DOT_X:
                tvHeader.setText("Вы победитель!");
                break;

            case DOT_O:
                tvHeader.setText("В этих голодных играх вы проиграли");
                break;

            case DOT_EMPTY:
                tvHeader.setText("Удивительная ситуация! Победителей НЕТ");
                break;
        }

        Toast.makeText(getApplicationContext(),
                "ИГРА ОКОНЧЕНА",
                Toast.LENGTH_SHORT).show();
    }

    private void setButton(int id, char DOT, boolean enabled)
    {
        Button btn = (Button)findViewById(id);

        btn.setText(""+DOT);
        btn.setEnabled(enabled);
    }

    private void stepAi() {
        int qntX = 0;
        int[] coordinates = null;

        for (char[] row : map)
            for (int i = 0; i < row.length; i++)
                qntX += row[i] == DOT_X ? 1 : 0;

        // Был выполнен первый ход
        if (qntX == 1) {
            setO(-1, -1);
            return;
        } else {
            //======================================================================================
            // Анализ на победу Искуственного интелекта

            // Совпадения по строке
            coordinates = checkRow(DOT_O);
            if (coordinates != null)
            {
                setO(coordinates[0], coordinates[1]);
                return;
            }

            // Совпадения по столбцу
            coordinates = checkCol(DOT_O);
            if (coordinates != null)
            {
                setO(coordinates[0], coordinates[1]);
                return;
            }

            // Совпадения по диагоналям
            // Главная диагональ
            coordinates = checkDiagonalMain(DOT_O);
            if (coordinates != null)
            {
                setO(coordinates[0], coordinates[1]);
                return;
            }

            // Побочная диагональ
            coordinates = checkDiagonalSlave(DOT_O);
            if (coordinates != null)
            {
                setO(coordinates[0], coordinates[1]);
                return;
            }


            //======================================================================================
            // Анализ на контратаку

            // Совпадения по строке
            coordinates = checkRow(DOT_X);
            if (coordinates != null)
            {
                setO(coordinates[0], coordinates[1]);
                return;
            }

            // Совпадения по столбцу
            coordinates = checkCol(DOT_X);
            if (coordinates != null)
            {
                setO(coordinates[0], coordinates[1]);
                return;
            }

            // Совпадения по диагоналям
            // Главная диагональ
            coordinates = checkDiagonalMain(DOT_X);
            if (coordinates != null)
            {
                setO(coordinates[0], coordinates[1]);
                return;
            }

            // Побочная диагональ
            coordinates = checkDiagonalSlave(DOT_X);
            if (coordinates != null)
            {
                setO(coordinates[0], coordinates[1]);
                return;
            }

            // Бьем наугад
            setO(-1, -1);
            return;
        }
    }

    private static int[] checkRow(char DOT_CHECKED)
    {
        int[] res = null;
        int qnt = 0;
        int freeId = -1;
        char old = DOT_EMPTY;
        char curr = DOT_EMPTY;
        boolean isFirst = true;

        // Совпадения по строке
        for (int r = 0; r < map.length; r++) {
            qnt = 0;
            freeId = -1;
            old = DOT_EMPTY;
            isFirst = true;

            // Проверяем ячейки на строке
            for (int c = 0; c < map.length; c++) {
                curr = map[r][c];
                qnt = checkIteration(curr, old, DOT_CHECKED, isFirst, qnt)
                        ? qnt + 1
                        : 0;
                old = curr;
                isFirst = false;
                if (curr == DOT_EMPTY)
                    freeId = c;

                // Строка свободна и до победы не хватает 1 элемента (при подряд идущих)
                if (freeId >= 0 && qnt >= DOTS_TO_WIN - 1) {
                    res = new int[2];
                    res[0] = r;
                    res[1] = freeId;
                }
            }
        }

        return res;
    }

    private static int[] checkCol(char DOT_CHECKED)
    {
        int[] res = null;
        int qnt = 0;
        int freeId = -1;
        char old = DOT_EMPTY;
        char curr = DOT_EMPTY;
        boolean isFirst = true;

        // Совпадения по строке столбцу
        for (int c = 0; c < map.length; c++) {
            qnt = 0;
            freeId = -1;
            old = DOT_EMPTY;
            isFirst = true;

            // Проверяем ячейки на столбце
            for (int r = 0; r < map.length; r++) {
                curr = map[r][c];
                qnt = checkIteration(curr, old, DOT_CHECKED, isFirst, qnt)
                        ? qnt + 1
                        : 0;
                old = curr;
                isFirst = false;
                if (curr == DOT_EMPTY)
                    freeId = r;

                // Строка свободна и до победы не хватает 1 элемента (при подряд идущих)
                if (freeId >= 0 && qnt >= DOTS_TO_WIN - 1) {
                    res = new int[2];
                    res[0] = freeId;
                    res[1] = c;
                }
            }
        }

        return res;
    }

    private static int[] checkDiagonalMain(char DOT_CHECKED)
    {
        int[] res = null;
        int qnt = 0;
        int freeIdRow = -1;
        int freeIdCol = -1;
        char old = DOT_EMPTY;
        char curr = DOT_EMPTY;
        int cycleQnt = map.length - DOTS_TO_WIN + 1;
        int x = 0;
        int y = 0;
        boolean isFirst = true;

        // Главная диагональ
        for (int c = 0; c < cycleQnt; c++)
            for (int r = 0; r < cycleQnt; r++) {
                qnt = 0;
                freeIdRow = -1;
                freeIdCol = -1;
                old = DOT_EMPTY;
                isFirst = true;

                for (int i = 0; i < DOTS_TO_WIN; i++) {
                    x = i + r;
                    y = i + c;
                    curr = map[x][y];
                    qnt = checkIteration(curr, old, DOT_CHECKED, isFirst, qnt)
                            ? qnt + 1
                            : 0;
                    old = curr;
                    isFirst = false;
                    if (curr == DOT_EMPTY) {
                        freeIdRow = x;
                        freeIdCol = y;
                    }
                    if (freeIdRow >= 0 && qnt >= DOTS_TO_WIN - 1) {
                        res = new int[2];
                        res[0] = freeIdRow;
                        res[1] = freeIdCol;
                    }
                }
            }

        return res;
    }

    private static int[] checkDiagonalSlave(char DOT_CHECKED)
    {
        int[] res = null;
        int qnt = 0;
        int freeIdRow = -1;
        int freeIdCol = -1;
        char old = DOT_EMPTY;
        char curr = DOT_EMPTY;
        int cycleQnt = map.length - DOTS_TO_WIN + 1;
        int x = 0;
        int y = 0;
        boolean isFirst = true;

        // Побочная диагональ
        for (int c = 0; c < cycleQnt; c++)
            for (int r = 0; r < cycleQnt; r++) {
                qnt = 0;
                freeIdRow = -1;
                freeIdCol = -1;
                old = DOT_EMPTY;
                isFirst = true;

                for (int i = 0; i < DOTS_TO_WIN; i++) {
                    x = i + r;
                    y = DOTS_TO_WIN - 1 - i + c;
                    curr = map[x][y];
                    qnt = checkIteration(curr, old, DOT_CHECKED, isFirst, qnt)
                            ? qnt + 1
                            : 0;
                    old = curr;
                    isFirst = false;
                    if (curr == DOT_EMPTY) {
                        freeIdRow = x;
                        freeIdCol = y;
                    }
                    if (freeIdRow >= 0 && qnt >= DOTS_TO_WIN - 1) {
                        res = new int[2];
                        res[0] = freeIdRow;
                        res[1] = freeIdCol;
                    }
                }
            }

        return res;
    }

    private static boolean checkIteration(char curr, char old, char DOT_CHECKED, boolean isFirst, int qnt)
    {
        return ((curr == DOT_CHECKED) ||

                ((curr == DOT_EMPTY) &&      // Ячейка пуста
                        (old == DOT_CHECKED) &&     // Предыдущая ячейка была искомой
                        !isFirst &&                 // Это не первый цикл
                        (qnt == DOTS_TO_WIN - 1))); // До победы не хватает одного значения
    }


    private void setO(int ix, int iy)
    {
        int x = 0;
        int y = 0;
        do
        {
            x = (ix >= 0) ? ix : rd.nextInt(map.length);
            y = (iy >= 0) ? iy : rd.nextInt(map.length);
        } while (!checkStep(x,y));

        map[x][y] = DOT_O;
        setButton(getIdByXY(x,y), DOT_O, false);
        System.out.println("А я вот так схожу!");
    }

    private static boolean checkWin(char DOT_WINNER)
    {
        int qnt = 0;

        // Совпадения по строке
        for (char[] row : map) {
            qnt = 0;
            for (int i = 0; i < row.length; i++) {
                qnt = row[i] == DOT_WINNER ? qnt + 1 : 0;
                if (qnt == DOTS_TO_WIN)
                    return true;
            }
        }

        // Совпадения по столбцу
        for (int x = 0; x < map.length; x++) {
            qnt = 0;
            for (int y = 0; y < map.length; y++) {
                qnt = map[y][x] == DOT_WINNER ? qnt + 1 : 0;
                if (qnt == DOTS_TO_WIN)
                    return true;
            }
        }

        // Совпадения по диагоналям
        int cycleQnt = map.length - DOTS_TO_WIN + 1;
        // Главная диагональ
        for (int c = 0; c < cycleQnt; c++)
            for (int r = 0; r < cycleQnt; r++) {
                qnt = 0;
                for (int i = 0; i < DOTS_TO_WIN; i++) {
                    qnt = map[i + r][i + c] == DOT_WINNER ? qnt + 1 : 0;
                    if (qnt == DOTS_TO_WIN)
                        return true;
                }
            }

        // Побочная диагональ
        for (int c = 0; c < cycleQnt; c++)
            for (int r = 0; r < cycleQnt; r++) {
                qnt = 0;
                for (int i = 0; i < DOTS_TO_WIN; i++) {
                    qnt = map[i + r][DOTS_TO_WIN - 1 - i + c] == DOT_WINNER ? qnt + 1 : 0;
                    if (qnt == DOTS_TO_WIN)
                        return true;
                }
            }

        return false;
    }

    private static boolean checkDraw()
    {
        for (char[] row : map)
            for (int i = 0; i < map.length; i++)
                if (row[i] == DOT_EMPTY)
                    return false;

        return true;
    }

    private static void stepHuman(int x, int y)
    {
        boolean res,isFirst;
        isFirst = true;
        do
        {
            if (!isFirst)
                System.out.println("Координаты введены не верно, или ячейка уже занята!");

            /*do
            {
                System.out.println("Укажите координату вашего хода (строка) в диапозоне от 1 до " + map.length);
                x = sc.nextInt() - 1;
            } while (x < 0 || x >= map.length);
            do
            {
                System.out.println("Укажите координату вашего хода (столбец) в диапозоне от 1 до " + map.length);
                y = sc.nextInt() - 1;
            } while (y < 0 || y >= map.length);*/

            res = checkStep(x,y);
            isFirst = false;
        } while (!res);

        map[x][y] = DOT_X;
    }

    private static boolean checkStep(int x, int y)
    {
        return ((x >= 0 && x < map.length) &&
                (y >= 0 && y < map.length) &&
                (map[x][y] == DOT_EMPTY)
        );
    }

    /*private static void setMapSize()
    {
        int SIZE;
        do
        {
            System.out.println("Введите размер поля, в диапозоне от 3 до 100 включительно: ");
            SIZE = sc.nextInt();
        } while (SIZE < 3 || SIZE > 100);
        System.out.println("Размер поля установлен как " + SIZE);
        map = new char[SIZE][SIZE];
    }

    private static void setDotsToWin()
    {
        do
        {
            System.out.println("Установите кол-во подряд идущих символов до победы, в диапозоне от 3 до " + map.length);
            DOTS_TO_WIN = sc.nextInt();
        } while (DOTS_TO_WIN < 3 || DOTS_TO_WIN > map.length);
        System.out.println("Кол-во подряд идущих символов до победы установлен как " + DOTS_TO_WIN);
    }*/

    private static void refreshMap()
    {
        map = new char[MAP_SIZE][MAP_SIZE];
        for (char[] row : map)
            for (int i = 0; i < row.length; i++)
                row[i] = DOT_EMPTY;

        System.out.println("Поле подготовлено к игре");
    }

}