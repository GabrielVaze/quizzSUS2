package com.example.quizzsus.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_SCORE = "score";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CORRECT = "correct";
    private static final String COLUMN_WRONG = "wrong";
    private static final String COLUMN_DATE = "date";

    public QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela de pontuação
        String createScoreTable = "CREATE TABLE " + TABLE_SCORE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CORRECT + " INTEGER," +
                COLUMN_WRONG + " INTEGER," +
                COLUMN_DATE + " TEXT)";
        db.execSQL(createScoreTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualização do banco de dados (removendo a tabela antiga e criando uma nova)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    public void addScore(int correct, int wrong) {
        // Adiciona um novo registro de pontuação
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CORRECT, correct);
            values.put(COLUMN_WRONG, wrong);

            // Formata a data para uma string legível
            String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
            values.put(COLUMN_DATE, currentDate);

            db.insert(TABLE_SCORE, null, values);
        }
    }

    // Método que retorna um Cursor com todos os registros
    public Cursor getAllScoresCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SCORE + " ORDER BY " + COLUMN_ID + " DESC", null);
    }

    public List<ScoreRecord> getAllScores() {
        List<ScoreRecord> scoreList = new ArrayList<>();

        // Usando try-with-resources para garantir que o cursor seja fechado automaticamente
        try (Cursor cursor = getAllScoresCursor()) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int correct = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CORRECT));
                    int wrong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WRONG));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));

                    scoreList.add(new ScoreRecord(correct, wrong, date));
                } while (cursor.moveToNext());
            }
        }
        return scoreList;
    }

    // Classe ScoreRecord adicionada dentro de QuizDatabaseHelper
    public static class ScoreRecord {
        private final int correct;
        private final int wrong;
        private final String date;

        public ScoreRecord(int correct, int wrong, String date) {
            this.correct = correct;
            this.wrong = wrong;
            this.date = date;
        }

        public int getCorrect() {
            return correct;
        }

        public int getWrong() {
            return wrong;
        }

        public String getDate() {
            return date;
        }
    }
}
