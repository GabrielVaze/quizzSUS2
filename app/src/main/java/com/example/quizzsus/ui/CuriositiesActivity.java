package com.example.quizzsus.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizzsus.R;
import java.util.ArrayList;
import java.util.List;

public class CuriositiesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CuriositiesAdapter curiositiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curiosities);

        recyclerView = findViewById(R.id.recyclerViewCuriosities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dados de curiosidades
        List<String> curiositiesList = new ArrayList<>();
        curiositiesList.add("A dengue é transmitida pelo mosquito Aedes aegypti.");
        curiositiesList.add("O Brasil registra milhares de casos de dengue anualmente.");
        curiositiesList.add("Eliminar água parada ajuda a prevenir a proliferação do mosquito.");
        curiositiesList.add("O Aedes aegypti também pode transmitir Zika e Chikungunya.");
        curiositiesList.add("O mosquito Aedes aegypti se reproduz em locais com água limpa e parada.");
        // Adicionando mais curiosidades
        curiositiesList.add("O Aedes aegypti é originário da África, mas está presente em vários países tropicais.");
        curiositiesList.add("A principal forma de prevenção da dengue é o combate ao mosquito.");
        curiositiesList.add("O ciclo de vida do mosquito Aedes aegypti dura cerca de uma semana.");
        curiositiesList.add("O uso de repelentes e mosquiteiros pode ajudar a reduzir o risco de picadas.");
        curiositiesList.add("Em casos graves, a dengue pode evoluir para a forma hemorrágica, que pode ser fatal.");
        curiositiesList.add("A dengue foi identificada pela primeira vez no século XVIII.");

        curiositiesAdapter = new CuriositiesAdapter(curiositiesList);
        recyclerView.setAdapter(curiositiesAdapter);
    }
}
