package com.example.numberguesser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvCubeNumber, tvInput, tvResult, tvStats;
    private Button[] numberButtons;
    private Button btnClear, btnDelete, btnSubmit;
    
    private int currentNumber;
    private String currentInput = "";
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupNumberButtons();
        setupControlButtons();
        generateNewNumber();
        updateStats();
    }

    private void initializeViews() {
        tvCubeNumber = findViewById(R.id.tvCubeNumber);
        tvInput = findViewById(R.id.tvInput);
        tvResult = findViewById(R.id.tvResult);
        tvStats = findViewById(R.id.tvStats);
        
        numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            int buttonId = getResources().getIdentifier("btn" + i, "id", getPackageName());
            numberButtons[i] = findViewById(buttonId);
        }
        
        btnClear = findViewById(R.id.btnClear);
        btnDelete = findViewById(R.id.btnDelete);
        btnSubmit = findViewById(R.id.btnSubmit);
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
