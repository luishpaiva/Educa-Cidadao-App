package com.projects.educacidadaoapp.model;

import static com.projects.educacidadaoapp.model.HashMD5.hashMD5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "educacidadao.sqlite";

    //Tabela usuarios
    private static final String TABELA_USUARIOS = "usuarios";
    private static final String COLUNA_ID_USUARIO = "id_usuario";
    private static final String COLUNA_USUARIO = "usuario";
    private static final String COLUNA_LOGRADOURO = "logradouro";
    private static final String COLUNA_NUMERO = "numero";
    private static final String COLUNA_BAIRRO = "bairro";
    private static final String COLUNA_EMAIL = "email";
    private static final String COLUNA_CELULAR = "celular";
    private static final String COLUNA_SENHA = "senha";

    //Tabela cursos
    private static final String TABELA_CURSOS = "cursos";
    private static final String COLUNA_ID_CURSO = "id_curso";
    private static final String COLUNA_TITULO = "titulo";
    private static final String COLUNA_DESCRICAO = "descricao";
    private static final String COLUNA_INICIO = "inicio";
    private static final String COLUNA_TERMINO = "termino";
    private static final String COLUNA_HORARIO = "horario";
    private static final String COLUNA_INSTRUTOR = "instrutor";
    private static final String COLUNA_CONDICAO = "condicao";
    private static final String COLUNA_VALOR = "valor";
    private static final String COLUNA_USUARIO_SENHA = "usuario_curso";

    private static final int DB_VERSION = 1;

    public Database(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIOS + "(" +
                COLUNA_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUNA_USUARIO + " TEXT," +
                COLUNA_LOGRADOURO + " TEXT," +
                COLUNA_NUMERO + " TEXT," +
                COLUNA_BAIRRO + " TEXT," +
                COLUNA_EMAIL + " TEXT," +
                COLUNA_CELULAR + " TEXT," +
                COLUNA_SENHA + " TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE IF NOT EXISTS " + TABELA_CURSOS + "(" +
                COLUNA_ID_CURSO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUNA_TITULO + " TEXT," +
                COLUNA_DESCRICAO + " TEXT," +
                COLUNA_INICIO + " TEXT," +
                COLUNA_TERMINO + " TEXT," +
                COLUNA_HORARIO + " TEXT," +
                COLUNA_INSTRUTOR + " TEXT," +
                COLUNA_CONDICAO + " TEXT," +
                COLUNA_VALOR + " INTEGER," +
                COLUNA_USUARIO_SENHA + " INTEGER," +
                "FOREIGN KEY(" + COLUNA_USUARIO_SENHA + ") " +
                "REFERENCES " + TABELA_USUARIOS + "(" + COLUNA_ID_USUARIO + "))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean registrarUsuario(Usuario conta) {
        boolean resultado;

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUNA_USUARIO, conta.getUsuario());
            cv.put(COLUNA_EMAIL, conta.getEmail());
            cv.put(COLUNA_SENHA, hashMD5(conta.getSenha()));
            resultado = db.insert(TABELA_USUARIOS, null, cv) > 0;
            db.close();
        } catch (Exception e) {
            resultado = false;
        }

        return resultado;
    }

    public boolean atualizarUsuarioSemSenha(Usuario conta) {
        boolean resultado;

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUNA_USUARIO, conta.getUsuario());
            cv.put(COLUNA_LOGRADOURO, conta.getLogradouro());
            cv.put(COLUNA_NUMERO, conta.getNumero());
            cv.put(COLUNA_BAIRRO, conta.getBairro());
            cv.put(COLUNA_EMAIL, conta.getEmail());
            cv.put(COLUNA_CELULAR, conta.getCelular());
            resultado = db.update(TABELA_USUARIOS, cv, COLUNA_ID_USUARIO + " = ?",
                    new String[] { String.valueOf(conta.getId_usuario()) }) > 0;
        } catch (Exception e) {
            resultado = false;
        }

        return resultado;
    }

    public boolean atualizarUsuarioComSenha(Usuario conta) {
        boolean resultado;

        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUNA_USUARIO, conta.getUsuario());
            cv.put(COLUNA_LOGRADOURO, conta.getLogradouro());
            cv.put(COLUNA_NUMERO, conta.getNumero());
            cv.put(COLUNA_BAIRRO, conta.getBairro());
            cv.put(COLUNA_EMAIL, conta.getEmail());
            cv.put(COLUNA_CELULAR, conta.getCelular());
            cv.put(COLUNA_SENHA, hashMD5(conta.getSenha()));
            resultado = db.update(TABELA_USUARIOS, cv, COLUNA_ID_USUARIO + " = ?",
                    new String[] { String.valueOf(conta.getId_usuario()) }) > 0;
            db.close();
        } catch (Exception e) {
            resultado = false;
        }

        return resultado;
    }

    public Usuario entrar(String usuario, String senha) {
        Usuario conta = null;

        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery(
                    "SELECT * FROM " + TABELA_USUARIOS +
                            " WHERE " + COLUNA_USUARIO + " = ? AND " + COLUNA_SENHA + " = ?",
                    new String[]{usuario, senha}
            );

            if (c.moveToFirst()) {
                conta = new Usuario();
                conta.setId_usuario(c.getLong(0));
                conta.setUsuario(c.getString(1));
                conta.setLogradouro(c.getString(2));
                conta.setNumero(c.getString(3));
                conta.setBairro(c.getString(4));
                conta.setEmail(c.getString(5));
                conta.setCelular(c.getString(6));
                conta.setSenha(c.getString(7));
            }

            c.close();
        } catch (Exception e) {
            conta = null;
        }

        return conta;
    }

    public Usuario checarUsuario(String usuario) {
        Usuario conta = null;

        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.rawQuery(
                    "SELECT * FROM " + TABELA_USUARIOS + " WHERE " + COLUNA_USUARIO + " = ?",
                    new String[]{usuario}
            );

            if (c.moveToFirst()) {
                conta = new Usuario();
                conta.setId_usuario(c.getLong(0));
                conta.setUsuario(c.getString(1));
                conta.setLogradouro(c.getString(2));
                conta.setNumero(c.getString(3));
                conta.setBairro(c.getString(4));
                conta.setEmail(c.getString(5));
                conta.setCelular(c.getString(6));
                conta.setSenha(c.getString(7));
            }

            c.close();
        } catch (Exception e) {
            conta = null;
        }

        return conta;
    }

    public long criarCurso(Curso c, long idUsuario) {
        ContentValues values = new ContentValues();
        String getIdUsuario = String.valueOf(idUsuario);

        values.put(COLUNA_TITULO, c.getTitulo());
        values.put(COLUNA_DESCRICAO, c.getDescricao());
        values.put(COLUNA_INICIO, c.getInicio());
        values.put(COLUNA_TERMINO, c.getTermino());
        values.put(COLUNA_HORARIO, c.getHorario());
        values.put(COLUNA_INSTRUTOR, c.getInstrutor());
        values.put(COLUNA_CONDICAO, c.getCondicao());
        values.put(COLUNA_VALOR, c.getValor());
        values.put(COLUNA_USUARIO_SENHA, getIdUsuario);

        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(TABELA_CURSOS, null, values);

        db.close();

        return id;
    }

    public long inserirCurso(Curso c, long idUsuario) {
        ContentValues values = new ContentValues();
        String getIdUsuario = String.valueOf(idUsuario);

        values.put(COLUNA_ID_CURSO, c.getId_curso());
        values.put(COLUNA_TITULO, c.getTitulo());
        values.put(COLUNA_DESCRICAO, c.getDescricao());
        values.put(COLUNA_INICIO, c.getInicio());
        values.put(COLUNA_TERMINO, c.getTermino());
        values.put(COLUNA_HORARIO, c.getHorario());
        values.put(COLUNA_INSTRUTOR, c.getInstrutor());
        values.put(COLUNA_CONDICAO, c.getCondicao());
        values.put(COLUNA_VALOR, c.getValor());
        values.put(COLUNA_USUARIO_SENHA, getIdUsuario);

        SQLiteDatabase db = getWritableDatabase();

        long id = db.insert(TABELA_CURSOS, null, values);

        db.close();

        return id;
    }

    public ArrayList<Curso> consultarCurso(long idUsuario) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA_CURSOS +
                " WHERE " + COLUNA_USUARIO_SENHA + " = ?", new String[]{ String.valueOf(idUsuario) });

        ArrayList<Curso> cursos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                long idCurso = cursor.getLong(cursor.getColumnIndexOrThrow(COLUNA_ID_CURSO));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_TITULO));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_DESCRICAO));
                String inicio = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_INICIO));
                String termino = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_TERMINO));
                String horario = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_HORARIO));
                String instrutor = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_INSTRUTOR));
                String condicao = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_CONDICAO));
                String valor = cursor.getString(cursor.getColumnIndexOrThrow(COLUNA_VALOR));
                cursos.add(new Curso(idCurso, titulo, descricao, inicio, termino, horario, instrutor, condicao, valor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return cursos;
    }

    public int atualizarCurso(Curso c) {
        ContentValues values = new ContentValues();

        values.put(COLUNA_TITULO, c.getTitulo());
        values.put(COLUNA_DESCRICAO, c.getDescricao());
        values.put(COLUNA_INICIO, c.getInicio());
        values.put(COLUNA_TERMINO, c.getTermino());
        values.put(COLUNA_HORARIO, c.getHorario());
        values.put(COLUNA_INSTRUTOR, c.getInstrutor());
        values.put(COLUNA_CONDICAO, c.getCondicao());
        values.put(COLUNA_VALOR, c.getValor());

        String idCurso = String.valueOf(c.getId_curso());

        SQLiteDatabase db = getWritableDatabase();

        int count = db.update(TABELA_CURSOS, values, COLUNA_ID_CURSO + " = ?", new String[]{idCurso});

        db.close();

        return count;
    }

    public int removerCurso(Curso c) {
        String idCurso = String.valueOf(c.getId_curso());

        SQLiteDatabase db = getWritableDatabase();

        int count = db.delete(TABELA_CURSOS, COLUNA_ID_CURSO + " = ?", new String[]{idCurso});

        db.close();

        return count;
    }

}
