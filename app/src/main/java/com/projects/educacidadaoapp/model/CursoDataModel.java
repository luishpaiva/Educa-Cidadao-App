package com.projects.educacidadaoapp.model;

import android.content.Context;

import java.util.ArrayList;

public class CursoDataModel {

    private static final CursoDataModel instance = new CursoDataModel();

    private CursoDataModel() {}

    public static CursoDataModel getInstance() {
        return instance;
    }

    private ArrayList<Curso> cursos;

    private Database database;

    public void createDatabase(Context context, long idUsuario) {
        database = new Database(context, null);
        cursos = database.consultarCurso(idUsuario);
    }

    public Curso getCurso(int position) {
        return cursos.get(position);
    }

    public int getCursosSize() {
        return cursos.size();
    }

    public void addCurso(Curso c, long idUsuario) {
        long id = database.criarCurso(c, idUsuario);

        if (id > 0) {
            c.setId_curso(id);
            cursos.add(c);
        }

    }

    public void insertCurso(Curso c, int position, long idUsuario) {
        long id = database.inserirCurso(c, idUsuario);

        if (id > 0) {
            cursos.add(position, c);
        }

    }

    public void updateCurso(Curso c, int position) {
        int count = database.atualizarCurso(c);

        if (count == 1) {
            cursos.set(position, c);
        }

    }

    public void removeCurso(int position) {
        int count = database.removerCurso(
                getCurso(position)
        );

        if (count == 1) {
            cursos.remove(position);
        }

    }

}
