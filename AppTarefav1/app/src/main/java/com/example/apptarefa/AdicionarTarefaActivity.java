package com.example.apptarefa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private EditText tituloTarefa;
    private EditText descricaoTarefa;
    private EditText dataTerminoTarefa;
    private Button botaoSalvarTarefa;
    private Button botaoVoltar;
    private BancoDadosHelper bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        tituloTarefa = findViewById(R.id.tituloTarefa);
        descricaoTarefa = findViewById(R.id.descricaoTarefa);
        dataTerminoTarefa = findViewById(R.id.dataTerminoTarefa);
        botaoSalvarTarefa = findViewById(R.id.botaoSalvarTarefa);
        botaoVoltar = findViewById(R.id.botaoVoltar);
        bancoDados = new BancoDadosHelper(this);

        // Configura o DatePickerDialog no campo de data
        dataTerminoTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém a data atual
                final Calendar calendario = Calendar.getInstance();
                int ano = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                // Cria o DatePickerDialog
                DatePickerDialog datePicker = new DatePickerDialog(
                        AdicionarTarefaActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int anoSelecionado, int mesSelecionado, int diaSelecionado) {
                                // Ajusta o texto no EditText com a data escolhida
                                String dataFormatada = String.format("%02d/%02d/%04d", diaSelecionado, mesSelecionado + 1, anoSelecionado);
                                dataTerminoTarefa.setText(dataFormatada);
                            }
                        },
                        ano, mes, dia
                );

                // Exibe o DatePickerDialog
                datePicker.show();
            }
        });

        botaoSalvarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloTarefa.getText().toString();
                String descricao = descricaoTarefa.getText().toString();
                String dataTermino = dataTerminoTarefa.getText().toString();

                if (titulo.isEmpty() || descricao.isEmpty() || dataTermino.isEmpty()) {
                    Toast.makeText(AdicionarTarefaActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean inserido = bancoDados.adicionarTarefa(titulo, descricao, dataTermino);
                    if (inserido) {
                        Toast.makeText(AdicionarTarefaActivity.this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AdicionarTarefaActivity.this, "Erro ao adicionar tarefa", Toast.LENGTH_SHORT).show();
                    }
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
}
