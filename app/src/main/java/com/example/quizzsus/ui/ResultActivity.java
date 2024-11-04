package com.example.quizzsus.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizzsus.R;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewCorrectAnswers, textViewWrongAnswers;
    private Button buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewCorrectAnswers = findViewById(R.id.textViewCorrectAnswers);
        textViewWrongAnswers = findViewById(R.id.textViewWrongAnswers);
        buttonFinish = findViewById(R.id.buttonFinish);

        // Obtém os valores passados pela QuizActivity
        int correctAnswers = getIntent().getIntExtra("CORRECT_ANSWERS", 0);
        int wrongAnswers = getIntent().getIntExtra("WRONG_ANSWERS", 0);

        // Define o texto com os valores recebidos
        textViewCorrectAnswers.setText("Acertos: " + correctAnswers);
        textViewWrongAnswers.setText("Erros: " + wrongAnswers);

        // Finaliza o quiz e volta para a tela inicial
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
