package com.example.doacoes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DoacaoDAO {

    private final String TABELA_DOACOES = "Doacoes";
    private DbGateway dbGateway;


    public DoacaoDAO (Context ctx){
        dbGateway = DbGateway.getInstance(ctx);
    }

    public boolean salvar(String titulo, String descricao, String regiao, String responsavel, String tipo){
        return salvar(0, titulo, descricao, regiao, responsavel, tipo);
    }

    public boolean salvar(int id, String titulo, String descricao, String regiao, String responsavel, String tipo){
        ContentValues cv = new ContentValues();

        cv.put("Titulo", titulo);
        cv.put("Descricao", descricao);
        cv.put("Regiao", regiao);
        cv.put("Responsavel", responsavel);
        cv.put("Tipo", tipo);

        if(id > 0)
            return dbGateway.getDatabase().update(TABELA_DOACOES, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return dbGateway.getDatabase().insert(TABELA_DOACOES, null, cv) > 0;
    }

    public List<Doacao> retornarTodos(){
        List<Doacao> clientes = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM Doacoes", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("Titulo"));
            String sexo = cursor.getString(cursor.getColumnIndex("Descricao"));
            String regiao = cursor.getString(cursor.getColumnIndex("Regiao"));
            String responsavel = cursor.getString(cursor.getColumnIndex("Responsavel"));
            String tipo = cursor.getString(cursor.getColumnIndex("Tipo"));

//            clientes.add(new Doacao(id, nome, sexo, regiao));

            clientes.add(new Doacao(id, nome, sexo, regiao, responsavel, tipo));
        }
        cursor.close();
        return clientes;
    }

    public Doacao retornarUltimo(){
        Cursor cursor = dbGateway.getDatabase().rawQuery("SELECT * FROM Doacoes ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String titulo = cursor.getString(cursor.getColumnIndex("Titulo"));
            String descricao = cursor.getString(cursor.getColumnIndex("Descricao"));
            String regiao = cursor.getString(cursor.getColumnIndex("Regiao"));
            String responsavel = cursor.getString(cursor.getColumnIndex("Responsavel"));
            String tipo = cursor.getString(cursor.getColumnIndex("Tipo"));

            cursor.close();
//                        return new Doacao(id, titulo, descricao, regiao);
            return new Doacao(id, titulo, descricao, regiao, responsavel, tipo);

        }

        return null;
    }

    public boolean excluir (int id){
        return dbGateway.getDatabase().delete(TABELA_DOACOES, "ID=?", new String[]{ id + "" }) > 0;
    }

}
