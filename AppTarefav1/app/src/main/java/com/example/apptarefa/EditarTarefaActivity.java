package com.example.apptarefa;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditarTarefaActivity extends AppCompatActivity {

    private EditText tituloTarefa;
    private EditText descricaoTarefa;
    private EditText dataTerminoTarefa;
    private Button botaoSalvarTarefa;
    private Button botaoExcluirTarefa;
    private Button botaoConcluirTarefa;
    private Button botaoVoltar;
    private BancoDadosHelper bancoDados;
    private int tarefaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);

        tituloTarefa = findViewById(R.id.tituloTarefa);
        descricaoTarefa = findViewById(R.id.descricaoTarefa);
        dataTerminoTarefa = findViewById(R.id.dataTerminoTarefa);
        botaoSalvarTarefa = findViewById(R.id.botaoSalvarTarefa);
        botaoExcluirTarefa = findViewById(R.id.botaoExcluirTarefa);
        botaoConcluirTarefa = findViewById(R.id.botaoConcluirTarefa);
        botaoVoltar = findViewById(R.id.botaoVoltar);
        bancoDados = new BancoDadosHelper(this);

        // Configura o DatePickerDialog no campo de data
        dataTerminoTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();
                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(
                        EditarTarefaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                                String dataFormatada = String.format("%02d/%02d/%04d", diaSelecionado, mesSelecionado + 1, anoSelecionado);
                                dataTerminoTarefa.setText(dataFormatada);
                            }
                        },
                        ano, mes, dia
                );

                datePicker.show();
            }
        });

        Intent intent = getIntent();
        tarefaId = intent.getIntExtra("tarefaId", -1);

        if (tarefaId != -1) {
            carregarTarefa(tarefaId);
        }

        botaoSalvarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloTarefa.getText().toString();
                String descricao = descricaoTarefa.getText().toString();
                String dataTermino = dataTerminoTarefa.getText().toString();

                if (titulo.isEmpty() || descricao.isEmpty() || dataTermino.isEmpty()) {
                    Toast.makeText(EditarTarefaActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean atualizado = bancoDados.atualizarTarefa(tarefaId, titulo, descricao, dataTermino);
                    if (atualizado) {
                        Toast.makeText(EditarTarefaActivity.this, "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditarTarefaActivity.this, "Erro ao atualizar tarefa", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        botaoExcluirTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean excluido = bancoDados.excluirTarefa(tarefaId);
                if (excluido) {
                    Toast.makeText(EditarTarefaActivity.this, "Tarefa excluída com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarTarefaActivity.this, "Erro ao excluir tarefa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoConcluirTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean concluido = bancoDados.marcarTarefaComoConcluida(tarefaId);
                if (concluido) {
                    Toast.makeText(EditarTarefaActivity.this, "Tarefa marcada como concluída", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditarTarefaActivity.this, "Erro ao marcar tarefa como concluída", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void carregarTarefa(int id) {
        Cursor cursor = bancoDados.obterTarefaPorId(id);
        if (cursor.moveToFirst()) {
            String titulo = cursor.getString(1);
            String descricao = cursor.getString(2);
            String dataTermino = cursor.getString(3);
            tituloTarefa.setText(titulo);
            descricaoTarefa.setText(descricao);
            dataTerminoTarefa.setText(dataTermino);
        }
        cursor.close();
    }
}
