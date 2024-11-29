package com.example.quizzsus.ui;




import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzsus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DengueActivity extends AppCompatActivity {

    private TextView tvTotalCasos;
    private Button btnFetchData;
    private OkHttpClient client = new OkHttpClient();
    private int totalCasos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dengue);

        tvTotalCasos = findViewById(R.id.tvTotalCasos);
        btnFetchData = findViewById(R.id.btnFetchData);

        btnFetchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData();
            }
        });
    }

    private void fetchData() {
        totalCasos = 0;
        int limit = 1000;
        int offset = 1;
        fetchCases(limit, offset);
    }

    private void fetchCases(int limit, int offset) {
        String url = "https://apidadosabertos.saude.gov.br/arboviroses/dengue?nu_ano=2022&limit=" + limit + "&offset=" + offset;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        // Resposta JSON
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray dataArray = jsonResponse.getJSONArray("parametros");

                        // Adiciona as informações dos casos ao TextView
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject caseData = dataArray.getJSONObject(i);
                            String dataNotificacao = caseData.getString("dt_notific");
                            String sexo = caseData.getString("cs_sexo");
                            String municipio = caseData.optString("municipio", "Não informado");
                            String dataObito = caseData.optString("con_dt_obi", "Não informada");

                            sb.append("Data de Notificação: ").append(dataNotificacao).append("\n");
                            sb.append("Sexo: ").append(sexo).append("\n");
                            sb.append("Município: ").append(municipio).append("\n");
                            sb.append("Data de Óbito: ").append(dataObito).append("\n\n");
                        }

                        // Atualiza a interface
                        runOnUiThread(() -> tvTotalCasos.setText(sb.toString()));

                        // Se o número de casos for menor que 100.000, continue buscando
                        if (dataArray.length() == limit) {
                            fetchCases(limit, offset + limit);
                        }
                    } catch (JSONException e) {
                        Log.e("DengueActivity", "Erro ao processar dados JSON: " + e.getMessage());
                        runOnUiThread(() -> tvTotalCasos.setText("Erro ao carregar dados"));
                    }
                } else {
                    runOnUiThread(() -> tvTotalCasos.setText("Erro na requisição"));
                }
            }
        });
    }



}
