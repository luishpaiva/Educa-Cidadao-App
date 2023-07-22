package com.projects.educacidadaoapp.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.projects.educacidadaoapp.R;
import com.projects.educacidadaoapp.model.Curso;
import com.projects.educacidadaoapp.model.CursoDataModel;

public class CursoDetailsActivity extends AppCompatActivity {

    EditText editTextTitulo, editTextDescricao, editTextInicio, editTextTermino,
             editTextHorario, editTextInstrutor, editTextCondicao, editTextValor;
    Button buttonAtualizarCurso;

    int index;
    public static int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_details);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        editTextInicio = findViewById(R.id.editTextInicio);
        editTextTermino = findViewById(R.id.editTextTermino);
        editTextHorario = findViewById(R.id.editTextHorario);
        editTextInstrutor = findViewById(R.id.editTextInstrutor);
        editTextCondicao = findViewById(R.id.editTextCondicao);
        editTextValor = findViewById(R.id.editTextValor);

        buttonAtualizarCurso = findViewById(R.id.buttonAtualizarUsuario);

        Bundle extra = getIntent().getExtras();
        index = extra.getInt("index");

        if (index != -1) {
            setTitle(R.string.editar_curso);

            Curso c = CursoDataModel.getInstance().getCurso(index);

            editTextTitulo.setText(c.getTitulo());
            editTextDescricao.setText(c.getDescricao());
            editTextInicio.setText(c.getInicio());
            editTextTermino.setText(c.getTermino());
            editTextHorario.setText(c.getHorario());
            editTextInstrutor.setText(c.getInstrutor());
            editTextCondicao.setText(c.getCondicao());
            editTextValor.setText(c.getValor());
        } else {
            setTitle(R.string.adicionar_curso);
            buttonAtualizarCurso.setText(R.string.adicionar_curso);
        }

    }

    public void atualizarCursoOnClick(View v) {
        String titulo = editTextTitulo.getText().toString();
        String descricao = editTextDescricao.getText().toString();
        String inicio = editTextInicio.getText().toString();
        String termino = editTextTermino.getText().toString();
        String horario = editTextHorario.getText().toString();
        String instrutor = editTextInstrutor.getText().toString();
        String condicao = editTextCondicao.getText().toString();
        String valor = editTextValor.getText().toString();

        Bundle extra = getIntent().getExtras();
        long idUsuario = extra.getLong("id_usuario");

        if (titulo.length() > 0 && descricao.length() > 0 &&
            inicio.length() > 0 && termino.length() > 0 &&
            horario.length() > 0 && instrutor.length() > 0 &&
            condicao.length() > 0 && valor.length() > 0) {

            if (index == -1) {
                CursoDataModel.getInstance().addCurso(
                        new Curso(titulo, descricao, inicio, termino, horario, instrutor, condicao, valor), idUsuario
                );
            } else {
                Curso c = CursoDataModel.getInstance().getCurso(index);
                c.setTitulo(titulo);
                c.setDescricao(descricao);
                c.setInicio(inicio);
                c.setTermino(termino);
                c.setHorario(horario);
                c.setInstrutor(instrutor);
                c.setCondicao(condicao);
                c.setValor(valor);
                CursoDataModel.getInstance().updateCurso(c, index);
            }

            if (index != -1) {
                Toast.makeText(
                        CursoDetailsActivity.this,
                        "Curso atualizado com sucesso!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(
                        CursoDetailsActivity.this,
                        "Curso adicionado com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }

            position = index;

            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(CursoDetailsActivity.this);
            builder.setTitle(R.string.atencao);
            builder.setMessage(R.string.campos_vazios);
            builder.setPositiveButton(android.R.string.yes, (dialog, which) -> finish());
            builder.setNegativeButton(android.R.string.no, null);
            builder.create().show();
        }

    }

}
