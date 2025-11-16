package com.Cube.myapp;

import android.app.Activity;
import android.os.Bundle;
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
        createLayoutProgrammatically();
        initializeViews();
        setupNumberButtons();
        setupControlButtons();
        generateNewNumber();
        updateStats();
    }

    private void createLayoutProgrammatically() {
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(50, 50, 50, 50);

        // Статистика
        tvStats = new TextView(this);
        tvStats.setText("Правильно: 0 | Неправильно: 0 | 0%");
        tvStats.setTextSize(16);
        mainLayout.addView(tvStats);

        // Куб числа
        tvCubeNumber = new TextView(this);
        tvCubeNumber.setText("000");
        tvCubeNumber.setTextSize(80);
        tvCubeNumber.setGravity(android.view.Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 
            0, 1
        );
        mainLayout.addView(tvCubeNumber, params);

        // Поле ввода
        tvInput = new TextView(this);
        tvInput.setText("--");
        tvInput.setTextSize(48);
        tvInput.setGravity(android.view.Gravity.CENTER);
        mainLayout.addView(tvInput);

        // Результат
        tvResult = new TextView(this);
        tvResult.setText("Введите число от 1 до 100");
        tvResult.setTextSize(18);
        tvResult.setGravity(android.view.Gravity.CENTER);
        mainLayout.addView(tvResult);

        setContentView(mainLayout);
    }

    // остальные методы остаются без изменений
    private void initializeViews() {
        // Views уже инициализированы в createLayoutProgrammatically
    }

    private void setupNumberButtons() {
        // Нужно будет добавить кнопки программно
    }

    private void setupControlButtons() {
        // Нужно будет добавить кнопки управления
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
    }

    private void checkAnswer() {
        if (currentInput.isEmpty()) {
            tvResult.setText("Введите число!");
            return;
        }

        int userAnswer = Integer.parseInt(currentInput);
        
        if (userAnswer == currentNumber) {
            tvResult.setText("Правильно! ✓");
            correctAnswers++;
            generateNewNumber();
        } else {
            tvResult.setText("Неверно! ✗ Попробуйте снова");
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
