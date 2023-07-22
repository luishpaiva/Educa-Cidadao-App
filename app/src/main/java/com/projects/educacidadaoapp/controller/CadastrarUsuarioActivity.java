package com.projects.educacidadaoapp.controller;

import static com.projects.educacidadaoapp.model.ValidaEmail.validaEmail;
import static com.projects.educacidadaoapp.model.ValidaSenha.validaSenha;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.projects.educacidadaoapp.R;
import com.projects.educacidadaoapp.model.Database;
import com.projects.educacidadaoapp.model.Usuario;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    EditText editTextRegUsuario, editTextRegEmail, editTextRegSenha, editTextRegConfirmarSenha;
    Button buttonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        setTitle(R.string.cadastrar_usuario);

        editTextRegUsuario = findViewById(R.id.editTextCadUsuario);
        editTextRegEmail = findViewById(R.id.editTextCadEmail);
        editTextRegSenha = findViewById(R.id.editTextCadSenha);
        editTextRegConfirmarSenha = findViewById(R.id.editTextCadConfirmarSenha);
        buttonRegistrar = findViewById(R.id.buttonCadastrarUsuario);

        buttonRegistrar.setOnClickListener(v -> {
            String usuario = editTextRegUsuario.getText().toString();
            String email = editTextRegEmail.getText().toString();
            String senha = editTextRegSenha.getText().toString();
            String confirmarSenha = editTextRegConfirmarSenha.getText().toString();

            try (Database db = new Database(getApplicationContext(), null)) {
                if (usuario.length() == 0
                        || email.length() == 0
                        || senha.length() == 0
                        || confirmarSenha.length() == 0) {
                    Toast.makeText(
                            CadastrarUsuarioActivity.this,
                            "Algum campo está em branco!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (senha.compareTo(confirmarSenha) == 0) {
                        if (validaSenha(senha)) {
                            if (validaEmail(email)) {
                                Usuario conta = new Usuario();
                                conta.setUsuario(editTextRegUsuario.getText().toString());
                                conta.setEmail(editTextRegEmail.getText().toString());
                                conta.setSenha(editTextRegSenha.getText().toString());

                                Usuario temp = db.checarUsuario(editTextRegUsuario.getText().toString());

                                if (temp == null) {
                                    if (db.registrarUsuario(conta)) {
                                        Toast.makeText(
                                                CadastrarUsuarioActivity.this,
                                                "Usuário cadastrado com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CadastrarUsuarioActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        throw new Exception();
                                    }
                                } else {
                                    Toast.makeText(
                                            CadastrarUsuarioActivity.this,
                                            "Nome de usuário já cadastrado no aplicativo!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(
                                        CadastrarUsuarioActivity.this,
                                        "E-mail informado não está em formato correto!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(
                                    CadastrarUsuarioActivity.this,
                                    "Senha informada não possui 8 dígitos, " +
                                            "letras, números ou caracteres especiais!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(
                                CadastrarUsuarioActivity.this,
                                "As senhas informadas não são iguais!",
                                Toast.LENGTH_SHORT).show();
                    }

                }

            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.erro);
                builder.setMessage(e.getMessage());
                builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.cancel());
                builder.show();
            }

        });

    }

}
