package com.example.quizzsus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzsus.ui.QuizActivity;
import com.example.quizzsus.ui.HistoryActivity; // Importa a HistoryActivity

public class MainActivity extends AppCompatActivity {

    private Button buttonStartQuiz;
    private Button buttonViewScore;
    private Button buttonViewHistory; // Novo botão para ver histórico

    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);
        buttonViewHistory = findViewById(R.id.buttonViewH); // Inicializa o novo botão

        // Recebe o score passado de QuizActivity, se houver
        correctAnswers = getIntent().getIntExtra("CORRECT_ANSWERS", 0);
        wrongAnswers = getIntent().getIntExtra("WRONG_ANSWERS", 0);

        // Iniciar o Quiz ao clicar no botão
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });


        // Abrir a HistoryActivity ao clicar no botão
        buttonViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHistory();
            }
        });
    }

    private void startQuiz() {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent); // Inicia o QuizActivity
    }


    private void viewHistory() { // Novo método para abrir a HistoryActivity
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent); // Inicia a HistoryActivity
    }
}
