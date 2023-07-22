package com.projects.educacidadaoapp.model;

public class Curso {

    private long id_curso;
    private String titulo, descricao, inicio, termino, horario, instrutor, condicao, valor;

    public Curso(long id_curso, String titulo, String descricao, String inicio, String termino, String horario, String instrutor, String condicao, String valor) {
        this.id_curso = id_curso;
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.termino = termino;
        this.instrutor = instrutor;
        this.horario = horario;
        this.condicao = condicao;
        this.valor = valor;
    }

    public Curso(String titulo, String descricao, String inicio, String termino, String horario, String instrutor, String condicao, String valor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.inicio = inicio;
        this.termino = termino;
        this.instrutor = instrutor;
        this.horario = horario;
        this.condicao = condicao;
        this.valor = valor;
    }

    public long getId_curso() {
        return id_curso;
    }

    public void setId_curso(long id_curso) {
        this.id_curso = id_curso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(String instrutor) {
        this.instrutor = instrutor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
