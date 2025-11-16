package com.Cube.myapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends Activity {

    private TextView tvCubeNumber, tvInput, tvResult, tvStats;
    private Button[] numberButtons = new Button[10];
    private Button btnClear, btnDelete, btnSubmit;
    
    private int currentNumber;
    private String currentInput = "";
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getActionBar().setIcon(R.drawable.ic_launcher);
        } catch (Exception e) {
            // Игнорируем ошибку если ActionBar не доступен
        }
        createLayoutProgrammatically();
        initializeViews();
        setupNumberButtons();
        setupControlButtons();
        generateNewNumber();
        updateStats();
    }

    private void createLayoutProgrammatically() {
        // Главный контейнер
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(50, 50, 50, 50);
        mainLayout.setBackgroundColor(Color.WHITE);

        // Статистика
        tvStats = new TextView(this);
        tvStats.setText("Правильно: 0 | Неправильно: 0 | 0%");
        tvStats.setTextSize(16);
        tvStats.setTextColor(Color.BLACK);
        mainLayout.addView(tvStats);

        // Куб числа (крупно по центру)
        tvCubeNumber = new TextView(this);
        tvCubeNumber.setText("000");
        tvCubeNumber.setTextSize(80);
        tvCubeNumber.setTextColor(Color.BLACK);
        tvCubeNumber.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams cubeParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 
            0, 1
        );
        mainLayout.addView(tvCubeNumber, cubeParams);

        // Поле ввода
        tvInput = new TextView(this);
        tvInput.setText("--");
        tvInput.setTextSize(48);
        tvInput.setTextColor(Color.BLACK);
        tvInput.setGravity(Gravity.CENTER);
        tvInput.setPadding(0, 20, 0, 20);
        mainLayout.addView(tvInput);

        // Результат
        tvResult = new TextView(this);
        tvResult.setText("Введите число от 1 до 100");
        tvResult.setTextSize(18);
        tvResult.setTextColor(Color.BLACK);
        tvResult.setGravity(Gravity.CENTER);
        tvResult.setPadding(0, 10, 0, 30);
        mainLayout.addView(tvResult);

        // Цифровая клавиатура
        addKeyboardLayout(mainLayout);

        setContentView(mainLayout);
    }

    private void addKeyboardLayout(LinearLayout mainLayout) {
        // Создаем GridLayout для цифровой клавиатуры (эмулируем GridLayout через LinearLayout)
        LinearLayout keyboardLayout = new LinearLayout(this);
        keyboardLayout.setOrientation(LinearLayout.VERTICAL);

        // Первый ряд: 1, 2, 3
        LinearLayout row1 = createKeyboardRow();
        for (int i = 1; i <= 3; i++) {
            numberButtons[i] = createNumberButton(String.valueOf(i));
            row1.addView(numberButtons[i]);
        }
        keyboardLayout.addView(row1);

        // Второй ряд: 4, 5, 6
        LinearLayout row2 = createKeyboardRow();
        for (int i = 4; i <= 6; i++) {
            numberButtons[i] = createNumberButton(String.valueOf(i));
            row2.addView(numberButtons[i]);
        }
        keyboardLayout.addView(row2);

        // Третий ряд: 7, 8, 9
        LinearLayout row3 = createKeyboardRow();
        for (int i = 7; i <= 9; i++) {
            numberButtons[i] = createNumberButton(String.valueOf(i));
            row3.addView(numberButtons[i]);
        }
        keyboardLayout.addView(row3);

        // Четвертый ряд: Сброс, 0, Удалить
        LinearLayout row4 = createKeyboardRow();
        
        btnClear = createControlButton("Сброс", Color.RED);
        row4.addView(btnClear);

        numberButtons[0] = createNumberButton("0");
        row4.addView(numberButtons[0]);

        btnDelete = createControlButton("⌫", Color.parseColor("#FF9800"));
        row4.addView(btnDelete);

        keyboardLayout.addView(row4);

        // Кнопка отправки
        btnSubmit = createControlButton("Проверить", Color.parseColor("#4CAF50"));
        LinearLayout.LayoutParams submitParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        submitParams.setMargins(0, 20, 0, 0);
        btnSubmit.setLayoutParams(submitParams);
        btnSubmit.setTextSize(20);
        keyboardLayout.addView(btnSubmit);

        mainLayout.addView(keyboardLayout);
    }

    private LinearLayout createKeyboardRow() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return row;
    }

    private Button createNumberButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(24);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(Color.parseColor("#03A9F4"));
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            0, 
            ViewGroup.LayoutParams.WRAP_CONTENT, 
            1
        );
        params.setMargins(5, 5, 5, 5);
        button.setLayoutParams(params);
        
        return button;
    }

    private Button createControlButton(String text, int color) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(18);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(color);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            0, 
            ViewGroup.LayoutParams.WRAP_CONTENT, 
            1
        );
        params.setMargins(5, 5, 5, 5);
        button.setLayoutParams(params);
        
        return button;
    }

    private void initializeViews() {
        // Все views уже созданы в createLayoutProgrammatically
    }

    private void setupNumberButtons() {
        for (int i = 0; i < 10; i++) {
            final int number = i;
            numberButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentInput.length() < 2) {
                        currentInput += number;
                        updateInputDisplay();
                    }
                }
            });
        }
    }

    private void setupControlButtons() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                updateInputDisplay();
                tvResult.setText("Введите число от 1 до 100");
                tvResult.setTextColor(Color.BLACK);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentInput.length() > 0) {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                    updateInputDisplay();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void updateInputDisplay() {
        if (currentInput.isEmpty()) {
            tvInput.setText("--");
        } else {
            tvInput.setText(currentInput);
        }
    }

    private void generateNewNumber() {
        Random random = new Random();
        currentNumber = random.nextInt(100) + 1;
        long cube = (long) currentNumber * currentNumber * currentNumber;
        tvCubeNumber.setText(String.valueOf(cube));
        
        currentInput = "";
        updateInputDisplay();
        tvResult.setText("Введите число от 1 до 100");
        tvResult.setTextColor(Color.BLACK);
    }

    private void checkAnswer() {
        if (currentInput.isEmpty()) {
            tvResult.setText("Введите число!");
            tvResult.setTextColor(Color.RED);
            return;
        }

        int userAnswer = Integer.parseInt(currentInput);
        
        if (userAnswer == currentNumber) {
            tvResult.setText("Правильно! ✓");
            tvResult.setTextColor(Color.GREEN);
            correctAnswers++;
            // Задержка перед генерацией нового числа
            tvCubeNumber.postDelayed(new Runnable() {
                @Override
                public void run() {
                    generateNewNumber();
                }
            }, 1000);
        } else {
            tvResult.setText("Неверно! ✗ Попробуйте снова");
            tvResult.setTextColor(Color.RED);
            wrongAnswers++;
        }
        
        updateStats();
    }

    private void updateStats() {
        int totalAnswers = correctAnswers + wrongAnswers;
        double percentage = totalAnswers > 0 ? (double) correctAnswers / totalAnswers * 100 : 0;
        
        String stats = String.format("Правильно: %d | Неправильно: %d | %.1f%%", 
                correctAnswers, wrongAnswers, percentage);
        tvStats.setText(stats);
    }
}
