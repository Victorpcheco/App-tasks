package com.example.apptarefa;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {

    private ListView listaTarefas;
    private Button botaoAdicionarTarefa;
    private Button botaoRelatorio;
    private ArrayList<Tarefa> tarefas;
    private TarefaAdapter adaptadorTarefas;
    private BancoDadosHelper bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaTarefas = findViewById(R.id.listaTarefas);
        botaoAdicionarTarefa = findViewById(R.id.botaoAdicionarTarefa);
        botaoRelatorio = findViewById(R.id.botaoRelatorio);
        bancoDados = new BancoDadosHelper(this);

        tarefas = new ArrayList<>();
        carregarTarefas();

        adaptadorTarefas = new TarefaAdapter(this, tarefas);
        listaTarefas.setAdapter(adaptadorTarefas);

        botaoAdicionarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, AdicionarTarefaActivity.class);
                startActivity(intent);
            }
        });

        botaoRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this, RelatorioActivity.class);
                startActivity(intent);
            }
        });

        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa tarefa = tarefas.get(position);
                Intent intent = new Intent(PrincipalActivity.this, EditarTarefaActivity.class);
                intent.putExtra("tarefaId", tarefa.getId());
                startActivity(intent);
            }
        });
    }

    private void carregarTarefas() {
        Cursor cursor = bancoDados.obterTodasTarefas();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String descricao = cursor.getString(2);
                String dataTermino = cursor.getString(3);
                boolean concluida = cursor.getInt(4) == 1;
                tarefas.add(new Tarefa(id, titulo, descricao, dataTermino, concluida));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tarefas.clear();
        carregarTarefas();
        adaptadorTarefas.notifyDataSetChanged();
    }
}