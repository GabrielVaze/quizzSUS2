// QuizActivity.java

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private RadioGroup radioGroupOptions;
    private RadioButton radioOptionA, radioOptionB, radioOptionC, radioOptionD;
    private Button buttonNext, buttonMainMenu;

    private List<Question> questionList; // Lista de perguntas
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

        // Carregar e embaralhar perguntas
        loadQuestions();
        Collections.shuffle(questionList); // Embaralha as perguntas

        // Exibindo a primeira pergunta
        displayQuestion();

        // Definindo ação para o botão "Próximo"
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                currentQuestionIndex++;

                if (currentQuestionIndex < questionList.size()) {
                    displayQuestion();
                } else {
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

    // Carrega perguntas sobre o tema dengue
    private void loadQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question("Qual mosquito é o principal transmissor da dengue?", new String[]{"A) Anopheles", "B) Culex", "C) Aedes aegypti", "D) Aedes albopictus"}, "C"));
        questionList.add(new Question("Qual dos seguintes sintomas é comum na dengue?", new String[]{"A) Tosse", "B) Febre alta", "C) Dor de garganta", "D) Congestão nasal"}, "B"));
        questionList.add(new Question("Qual é o principal método de prevenção contra a dengue?", new String[]{"A) Tomar vacina", "B) Usar repelente", "C) Eliminar focos de água parada", "D) Tomar vitaminas"}, "C"));
        questionList.add(new Question("Qual dos seguintes não é um sintoma de dengue?", new String[]{"A) Dor de cabeça", "B) Febre", "C) Dor muscular", "D) Manchas na pele"}, "D"));
        questionList.add(new Question("Qual período do dia o Aedes aegypti costuma picar mais?", new String[]{"A) Durante a noite", "B) De manhã e ao entardecer", "C) A tarde", "D) Ao meio-dia"}, "B"));
        questionList.add(new Question("Qual é o ciclo de vida do Aedes aegypti?", new String[]{"A) Ovo, larva, pupa, adulto", "B) Ovo, pupa, larva, adulto", "C) Larva, ovo, pupa, adulto", "D) Adulto, pupa, larva, ovo"}, "A"));
        questionList.add(new Question("Quanto tempo leva para o Aedes aegypti se tornar adulto?", new String[]{"A) 2 dias", "B) 7 a 10 dias", "C) 15 dias", "D) 1 mês"}, "B"));
        questionList.add(new Question("Qual destes lugares é o mais comum para a reprodução do mosquito da dengue?", new String[]{"A) Água parada em pneus", "B) Águas correntes", "C) Áreas secas", "D) Áreas com sombra"}, "A"));
        questionList.add(new Question("Qual é a principal causa do aumento dos casos de dengue em algumas regiões?", new String[]{"A) Falta de vacinas", "B) Aumento da temperatura e umidade", "C) Poluição do ar", "D) Falta de hospitais"}, "B"));
        questionList.add(new Question("Quantos tipos de vírus da dengue existem?", new String[]{"A) 1", "B) 2", "C) 3", "D) 4"}, "D"));
        questionList.add(new Question("Qual medida NÃO ajuda a prevenir a dengue?", new String[]{"A) Limpar calhas", "B) Guardar garrafas com a boca para baixo", "C) Deixar pneus ao ar livre", "D) Tampar caixas d'água"}, "C"));
        // Adicione mais perguntas aqui para enriquecer o banco de perguntas
    }

    // Exibe a pergunta e as opções
    private void displayQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        textViewQuestion.setText(currentQuestion.getQuestion());
        radioOptionA.setText(currentQuestion.getOptions()[0]);
        radioOptionB.setText(currentQuestion.getOptions()[1]);
        radioOptionC.setText(currentQuestion.getOptions()[2]);
        radioOptionD.setText(currentQuestion.getOptions()[3]);
        radioGroupOptions.clearCheck();
    }

    private void checkAnswer() {
        int selectedId = radioGroupOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Selecione uma resposta.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedOption = findViewById(selectedId);
        Question currentQuestion = questionList.get(currentQuestionIndex);
        if (selectedOption.getText().toString().startsWith(currentQuestion.getCorrectAnswer())) {
            correctCount++;
            Toast.makeText(this, "Correto!", Toast.LENGTH_SHORT).show();
        } else {
            wrongCount++;
            Toast.makeText(this, "Errado!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveScoreToDatabase() {
        databaseHelper.addScore(correctCount, wrongCount);
    }

    private void showResults() {
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
