package com.example.apptarefa;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private String dataTermino;
    private boolean concluida;

    public Tarefa(int id, String titulo, String descricao, String dataTermino, boolean concluida) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataTermino = dataTermino;
        this.concluida = concluida;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public boolean isConcluida() {
        return concluida;
    }
}