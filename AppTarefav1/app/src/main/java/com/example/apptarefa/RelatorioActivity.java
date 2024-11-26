package com.example.apptarefa;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RelatorioActivity extends AppCompatActivity {

    private ListView listaTarefasCompletas;
    private ListView listaTarefasIncompletas;
    private ArrayList<String> tarefasCompletas;
    private ArrayList<String> tarefasIncompletas;
    private ArrayAdapter<String> adaptadorTarefasCompletas;
    private ArrayAdapter<String> adaptadorTarefasIncompletas;
    private BancoDadosHelper bancoDados;
    private Button botaoVoltarRelatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        listaTarefasCompletas = findViewById(R.id.listaTarefasCompletas);
        listaTarefasIncompletas = findViewById(R.id.listaTarefasIncompletas);
        botaoVoltarRelatorio = findViewById(R.id.botaoVoltarRelatorio);
        bancoDados = new BancoDadosHelper(this);

        tarefasCompletas = new ArrayList<>();
        tarefasIncompletas = new ArrayList<>();
        carregarTarefas();

        adaptadorTarefasCompletas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tarefasCompletas);
        adaptadorTarefasIncompletas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tarefasIncompletas);

        listaTarefasCompletas.setAdapter(adaptadorTarefasCompletas);
        listaTarefasIncompletas.setAdapter(adaptadorTarefasIncompletas);

        botaoVoltarRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void carregarTarefas() {
        Cursor cursor = bancoDados.obterTodasTarefas();
        if (cursor.moveToFirst()) {
            do {
                String titulo = cursor.getString(1);
                String descricao = cursor.getString(2);
                String dataTermino = cursor.getString(3);
                boolean completa = cursor.getInt(4) == 1; // Supondo que a coluna 4 indica se a tarefa está completa

                if (completa) {
                    tarefasCompletas.add(titulo + "\n" + descricao + "\n" + dataTermino);
                } else {
                    tarefasIncompletas.add(titulo + "\n" + descricao + "\n" + dataTermino);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}