package com.example.apptarefa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    private EditText emailCadastro;
    private EditText senhaCadastro;
    private EditText confirmarSenhaCadastro;
    private Button botaoCadastrar;
    private Button botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        emailCadastro = findViewById(R.id.emailCadastro);
        senhaCadastro = findViewById(R.id.senhaCadastro);
        confirmarSenhaCadastro = findViewById(R.id.confirmarSenhaCadastro);
        botaoCadastrar = findViewById(R.id.botaoCadastrar);
        botaoVoltar = findViewById(R.id.botaoVoltar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailCadastro.getText().toString();
                String senha = senhaCadastro.getText().toString();
                String confirmarSenha = confirmarSenhaCadastro.getText().toString();

                if (email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else if (!senha.equals(confirmarSenha)) {
                    Toast.makeText(CadastroActivity.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                } else {
                    // Lógica de cadastro
                    // Se cadastrado com sucesso:
                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
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
