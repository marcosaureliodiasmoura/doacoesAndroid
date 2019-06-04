package com.example.doacoes;

import java.io.Serializable;

public class Doacao implements Serializable {

    private int id;
    private String titulo;
    private String descricao;
    private String regiao;
    private String responsavel;
    private String tipo;

//    public Doacao(int id, String titulo, String descricao, String regiao){
    public Doacao(int id, String titulo, String descricao, String regiao, String responsavel, String tipo){
                this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.regiao = regiao;
        this.responsavel = responsavel;
        this.tipo = tipo;
    }

    public int getId(){ return this.id; }
    public String getTitulo(){ return this.titulo; }
    public String getDescricao(){ return this.descricao; }
    public String getRegiao(){ return this.regiao; }
    public String getResponsavel() { return this.responsavel; }
    public String getTipo() { return this.tipo; }

    @Override
    public boolean equals(Object o){
        return this.id == ((Doacao)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}
