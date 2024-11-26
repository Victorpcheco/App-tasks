package com.example.apptarefa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private EditText emailRecuperarSenha;
    private Button botaoEnviar;
    private Button botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        emailRecuperarSenha = findViewById(R.id.emailRecuperarSenha);
        botaoEnviar = findViewById(R.id.botaoEnviar);
        botaoVoltar = findViewById(R.id.botaoVoltar);

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailRecuperarSenha.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(RecuperarSenhaActivity.this, "Por favor, preencha o campo de email", Toast.LENGTH_SHORT).show();
                } else {
                    // Lógica para enviar email de recuperação de senha
                    Toast.makeText(RecuperarSenhaActivity.this, "Instruções de recuperação enviadas para " + email, Toast.LENGTH_SHORT).show();
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