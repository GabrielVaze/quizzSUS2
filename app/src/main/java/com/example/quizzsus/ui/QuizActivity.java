package com.example.quizzsus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzsus.MainActivity;
import com.example.quizzsus.R;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private RadioButton radioOptionA, radioOptionB, radioOptionC, radioOptionD;
    private Button buttonNext, buttonMainMenu;

    private String[] questions = {
            "Quais são as principais doenças associadas à obesidade?",
            "Como hábitos alimentares e atividade física influenciam na prevenção da obesidade?",
            "Qual das opções abaixo é uma dica para prevenir a obesidade?",
            "Qual destes fatores é considerado um dos principais causadores da obesidade?",
            "Como a genética pode influenciar o peso de uma pessoa?",
            "Qual das alternativas é um benefício da prática regular de exercícios físicos?",
            "O que pode acontecer se a obesidade não for tratada?",
            "Qual é a recomendação diária de atividade física para adultos para a manutenção de um peso saudável?",
            "Qual alimento deve ser evitado para prevenir a obesidade?",
            "Como o estresse pode afetar o peso de uma pessoa?"
    };

    private String[][] options = {
            {"A) Diabetes", "B) Hipertensão", "C) Doenças cardíacas", "D) Todas as anteriores"},
            {"A) Não influenciam", "B) A boa alimentação e a atividade física regular podem ajudar a prevenir a obesidade", "C) Apenas a atividade física é importante", "D) Apenas a alimentação é importante"},
            {"A) Comer em excesso", "B) Reduzir o consumo de açúcar e gorduras saturadas", "C) Evitar a prática de exercícios físicos", "D) Fazer refeições irregulares"},
            {"A) Consumo excessivo de calorias", "B) Hidratação inadequada", "C) Exposição ao sol", "D) Uso de eletrônicos"},
            {"A) Não tem influência", "B) Pode predispor ao ganho de peso", "C) Apenas influencia o apetite", "D) Gera uma resistência ao emagrecimento"},
            {"A) Aumento do apetite", "B) Melhora da saúde mental", "C) Estímulo ao sedentarismo", "D) Nenhuma das anteriores"},
            {"A) Aumento da autoestima", "B) Melhora na saúde geral", "C) Desenvolvimento de doenças crônicas", "D) Nenhuma alteração significativa"},
            {"A) 30 minutos, 5 vezes por semana", "B) 10 minutos, 2 vezes por semana", "C) 15 minutos, 3 vezes por semana", "D) 60 minutos, diariamente"},
            {"A) Frutas", "B) Legumes", "C) Refrigerantes", "D) Grãos integrais"},
            {"A) Não influencia", "B) Pode levar ao ganho de peso", "C) Ajuda a emagrecer", "D) Apenas afeta a saúde mental"}
    };

    private String[] answers = {"D", "B", "B", "A", "B", "B", "C", "A", "C", "B"};

    private int currentQuestionIndex = 0;
    private int correctCount = 0;
    private int wrongCount = 0;

    private QuizDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Inicializando o banco de dados
        databaseHelper = new QuizDatabaseHelper(this);

        // Inicializando os elementos da interface
        textViewQuestion = findViewById(R.id.textViewQuestion);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        radioOptionA = findViewById(R.id.radioOptionA);
        radioOptionB = findViewById(R.id.radioOptionB);
        radioOptionC = findViewById(R.id.radioOptionC);
        radioOptionD = findViewById(R.id.radioOptionD);
        buttonNext = findViewById(R.id.buttonNext);
        buttonMainMenu = findViewById(R.id.buttonMainMenu);

        // Exibindo a primeira pergunta
        displayQuestion();

        // Definindo ação para o botão "Próximo"
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                currentQuestionIndex++;

                if (currentQuestionIndex < questions.length) {
                    displayQuestion();
                } else {
                    // Salva o score no banco de dados e exibe os resultados
                    saveScoreToDatabase();
                    showResults();
                }
            }
        });

        // Ação para o botão de voltar ao menu principal
        buttonMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayQuestion() {
        textViewQuestion.setText(questions[currentQuestionIndex]);
        radioOptionA.setText(options[currentQuestionIndex][0]);
        radioOptionB.setText(options[currentQuestionIndex][1]);
        radioOptionC.setText(options[currentQuestionIndex][2]);
        radioOptionD.setText(options[currentQuestionIndex][3]);
        radioGroupOptions.clearCheck();
    }

    private void checkAnswer() {
        int selectedId = radioGroupOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Selecione uma resposta.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedOption = findViewById(selectedId);
        if (selectedOption.getText().toString().startsWith(answers[currentQuestionIndex])) {
            correctCount++;
            Toast.makeText(this, "Correto!", Toast.LENGTH_SHORT).show();
        } else {
            wrongCount++;
            Toast.makeText(this, "Errado!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveScoreToDatabase() {
        // Adiciona os acertos e erros ao banco de dados
        databaseHelper.addScore(correctCount, wrongCount);
    }

    private void showResults() {
        // Passa os resultados para a ResultActivity
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("CORRECT_ANSWERS", correctCount);
        intent.putExtra("WRONG_ANSWERS", wrongCount);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}
