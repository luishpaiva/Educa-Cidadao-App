package com.projects.educacidadaoapp.controller;

import static com.projects.educacidadaoapp.model.ValidaEmail.validaEmail;
import static com.projects.educacidadaoapp.model.ValidaSenha.validaSenha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.projects.educacidadaoapp.R;
import com.projects.educacidadaoapp.model.Usuario;
import com.projects.educacidadaoapp.model.Database;

public class UsuarioDetailActivity extends AppCompatActivity {

    EditText editTextUsuario, editTextLogradouro, editTextNumero, editTextBairro,
            editTextEmail, editTextCelular, editTextSenha, editTextConfirmarSenha;
    private Usuario conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detail);

        setTitle(R.string.editar_usuario);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCelular = findViewById(R.id.editTextCelular);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextConfirmarSenha = findViewById(R.id.editTextConfirmarSenha);

        carregarDados();

        editTextUsuario.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UsuarioDetailActivity.this, MainActivity.class);
        intent.putExtra("conta", conta);
        startActivity(intent);
    }

    public void atualizarUsuarioOnClick(View v) {
        try (Database db = new Database(getApplicationContext(), null)) {

            String usuario = editTextUsuario.getText().toString();
            String logradouro = editTextLogradouro.getText().toString();
            String numero = editTextNumero.getText().toString();
            String bairro = editTextBairro.getText().toString();
            String email = editTextEmail.getText().toString();
            String celular = editTextCelular.getText().toString();
            String senha = editTextSenha.getText().toString();
            String confirmarSenha = editTextConfirmarSenha.getText().toString();

            Usuario conta = db.checarUsuario(editTextUsuario.getText().toString());

            conta.setUsuario(usuario);
            conta.setLogradouro(logradouro);
            conta.setNumero(numero);
            conta.setBairro(bairro);
            conta.setEmail(email);
            conta.setCelular(celular);
            conta.setSenha(senha);

            if (senha.compareTo(confirmarSenha) == 0) {
                if (validaEmail(email)) {
                    if (senha.length() == 0) {
                        if (db.atualizarUsuarioSemSenha(conta)) {
                            Toast.makeText(
                                    UsuarioDetailActivity.this,
                                    "Usuário atualizado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UsuarioDetailActivity.this, MainActivity.class);
                            intent.putExtra("conta", conta);
                            startActivity(intent);
                        } else {
                            throw new Exception();
                        }
                    } else {
                        if (validaSenha(senha)) {
                            if (db.atualizarUsuarioComSenha(conta)) {
                                Toast.makeText(
                                        UsuarioDetailActivity.this,
                                        "Usuário atualizado com sucesso!",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UsuarioDetailActivity.this, MainActivity.class);
                                intent.putExtra("conta", conta);
                                startActivity(intent);
                            } else {
                                throw new Exception();
                            }
                        } else {
                            Toast.makeText(
                                    UsuarioDetailActivity.this,
                                    "Senha informada não possui 8 dígitos, " +
                                            "letras, números ou caracteres especiais!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(UsuarioDetailActivity.this, MainActivity.class);
                        intent.putExtra("conta", conta);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(
                            UsuarioDetailActivity.this,
                            "E-mail informado não está em formato correto!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(
                        UsuarioDetailActivity.this,
                        "As senhas informadas não são iguais!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(R.string.erro);
            builder.setMessage(e.getMessage());
            builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.cancel());
            builder.show();
        }

    }

    private void carregarDados() {
        Intent intent = getIntent();

        conta = (Usuario) intent.getSerializableExtra("conta");

        editTextUsuario.setText(conta.getUsuario());
        editTextLogradouro.setText(conta.getLogradouro());
        editTextNumero.setText(conta.getNumero());
        editTextBairro.setText(conta.getBairro());
        editTextEmail.setText(conta.getEmail());
        editTextCelular.setText(conta.getCelular());

    }

}
