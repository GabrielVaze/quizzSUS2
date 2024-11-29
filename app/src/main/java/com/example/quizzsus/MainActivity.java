package com.example.quizzsus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizzsus.ui.QuizActivity;
import com.example.quizzsus.ui.HistoryActivity;
import com.example.quizzsus.ui.CuriositiesActivity;
import com.example.quizzsus.ui.DengueActivity; // Nova Activity para casos de dengue

public class MainActivity extends AppCompatActivity {

    private Button buttonStartQuiz;
    private Button buttonViewHistory;
    private Button buttonCuriosities;
    private Button buttonDengueCases; // Botão para casos de dengue

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);
        buttonViewHistory = findViewById(R.id.buttonViewH);
        buttonCuriosities = findViewById(R.id.buttonCuriosities);
        buttonDengueCases = findViewById(R.id.buttonDengueCases); // Inicialize o novo botão

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        buttonViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHistory();
            }
        });

        buttonCuriosities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCuriosities();
            }
        });

        buttonDengueCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDengueCases(); // Chama a função para abrir a atividade de casos de dengue
            }
        });
    }

    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    private void viewHistory() {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    private void openCuriosities() {
        Intent intent = new Intent(MainActivity.this, CuriositiesActivity.class);
        startActivity(intent);
    }

    private void openDengueCases() {
        Intent intent = new Intent(MainActivity.this, DengueActivity.class);
        startActivity(intent);
    }
}
