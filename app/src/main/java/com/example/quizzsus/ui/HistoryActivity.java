package com.example.quizzsus.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizzsus.R;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private static final String COLUMN_CORRECT = "correct"; // Nome da coluna para acertos
    private static final String COLUMN_WRONG = "wrong";     // Nome da coluna para erros
    private static final String COLUMN_DATE = "date";       // Nome da coluna para a data

    private RecyclerView recyclerViewHistory;
    private HistoryAdapter historyAdapter;
    private QuizDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Inicializa o helper do banco de dados e o RecyclerView
        databaseHelper = new QuizDatabaseHelper(this);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));

        // Busca o histórico de pontuações e configura o adapter
        List<QuizDatabaseHelper.ScoreRecord> scoreRecords = fetchHistory();
        historyAdapter = new HistoryAdapter(scoreRecords);
        recyclerViewHistory.setAdapter(historyAdapter);
    }

    private List<QuizDatabaseHelper.ScoreRecord> fetchHistory() {
        List<QuizDatabaseHelper.ScoreRecord> scoreRecords = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = databaseHelper.getAllScoresCursor(); // Chama o método correto

            if (cursor != null && cursor.moveToFirst()) {
                // Obtém os índices das colunas de forma mais segura
                int correctIndex = cursor.getColumnIndex(COLUMN_CORRECT);
                int wrongIndex = cursor.getColumnIndex(COLUMN_WRONG);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);

                // Verifica se os índices das colunas são válidos
                if (correctIndex == -1 || wrongIndex == -1 || dateIndex == -1) {
                    Toast.makeText(this, "Erro: uma ou mais colunas não foram encontradas.", Toast.LENGTH_SHORT).show();
                } else {
                    do {
                        int correct = cursor.getInt(correctIndex);
                        int wrong = cursor.getInt(wrongIndex);
                        String date = cursor.getString(dateIndex);
                        scoreRecords.add(new QuizDatabaseHelper.ScoreRecord(correct, wrong, date));
                    } while (cursor.moveToNext());
                }
            } else {
                Toast.makeText(this, "Nenhum histórico encontrado.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao buscar o histórico: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close(); // Certifique-se de fechar o cursor para evitar vazamentos de memória
            }
        }

        return scoreRecords;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close(); // Fecha o banco de dados ao destruir a activity
    }
}
