package com.projects.educacidadaoapp.controller;

import static com.projects.educacidadaoapp.model.HashMD5.hashMD5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.projects.educacidadaoapp.R;
import com.projects.educacidadaoapp.model.Database;
import com.projects.educacidadaoapp.model.Usuario;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsuario, editTextSenha;
    Button buttonEntrar, buttonCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsuario = findViewById(R.id.editTextCadUsuario);
        editTextSenha = findViewById(R.id.editTextCadSenha);
        buttonEntrar = findViewById(R.id.buttonEntrar);
        buttonCadastrarUsuario = findViewById(R.id.buttonAtualizarUsuario);

        buttonEntrar.setOnClickListener(v -> {
            String usuario = editTextUsuario.getText().toString();
            String senha_tmp = editTextSenha.getText().toString();
            String senha;
            try {
                senha = hashMD5(senha_tmp);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            try (Database db = new Database(getApplicationContext(), null)) {

                Usuario conta = db.entrar(usuario, senha);

                if (usuario.length() == 0 || senha.length() == 0) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Usuário ou senha em branco!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (conta == null) {
                        Toast.makeText(
                                LoginActivity.this,
                                "Usuário ou senha inválidos!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(
                                LoginActivity.this,
                                "Usuário validado com sucesso!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("conta", conta);
                        startActivity(intent);
                    }

                }

            }

        });

        buttonCadastrarUsuario.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CadastrarUsuarioActivity.class)));

    }

}
