package com.projects.educacidadaoapp.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;
import com.projects.educacidadaoapp.R;
import com.projects.educacidadaoapp.model.Curso;
import com.projects.educacidadaoapp.model.CursoDataModel;
import com.projects.educacidadaoapp.model.Usuario;

public class MainActivity extends AppCompatActivity {

    TextView textViewBemVindo;
    RecyclerView recyclerView;
    CursoAdapter cursoAdapter = new CursoAdapter();
    private Usuario conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBemVindo = findViewById(R.id.textViewBemVindo);
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        conta = (Usuario) intent.getSerializableExtra("conta");
        textViewBemVindo.setText(String.format("%s %s!", getString(R.string.bem_vindo), conta.getUsuario()));

        CursoDataModel.getInstance().createDatabase(getApplicationContext(), conta.getId_usuario());

        recyclerView.setAdapter(cursoAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(MainActivity.this)
        );

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                MainActivity.this,
                DividerItemDecoration.VERTICAL
        );

        recyclerView.addItemDecoration(itemDecoration);

        cursoAdapter.setOnItemClickListener((view, position) -> irParaCurso(position));

        cursoAdapter.setOnItemLongClickListener((view, position) -> {
            Curso c = CursoDataModel.getInstance().getCurso(position);
            CursoDataModel.getInstance().removeCurso(position);
            cursoAdapter.notifyItemRemoved(position);

            View contextView = findViewById(android.R.id.content);
            Snackbar.make(contextView, R.string.remover_curso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.desfazer, v -> {
                        CursoDataModel.getInstance().insertCurso(c, position, conta.getId_usuario());
                        cursoAdapter.notifyItemInserted(position);
                    })
                    .show();
            return true;
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        int position = CursoDetailsActivity.position;
        cursoAdapter.notifyItemChanged(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);

        if (id == R.id.itemEditarUsuario) {
            Intent intentEditarUsuario = new Intent(MainActivity.this, UsuarioDetailActivity.class);
            intentEditarUsuario.putExtra("conta", conta);
            startActivity(intentEditarUsuario);
        }

        if (id == R.id.itemSobreOProjeto) {
            Intent intentSobreOProjeto = new Intent(MainActivity.this, SobreProjetoActivity.class);
            startActivity(intentSobreOProjeto);
        }

        if (id == R.id.itemSobreNos) {
            Intent intentSobreNos = new Intent(MainActivity.this, SobreNosActivity.class);
            startActivity(intentSobreNos);
        }

        if (id == R.id.itemSair) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void adicionarCurso(View view) {
        irParaCurso(-1);
    }

    void irParaCurso(int index) {
        Intent intent = new Intent(MainActivity.this, CursoDetailsActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("id_usuario", conta.getId_usuario());
        startActivity(intent);
    }

}
