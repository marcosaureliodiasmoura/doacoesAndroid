package com.example.doacoes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.doacoes.R;

import org.w3c.dom.Text;

public class DoacaoHolder extends RecyclerView.ViewHolder{

    //Aqui registramos o que será mostrados na view da lista
    //Depois jogamos na DoacaoAdapter

//    RelativeLayout parentLayout;


    public TextView titulo;
    public TextView descricao;
    public TextView regiao;
    public ImageButton btnEditar;
    public ImageButton btnExcluir;
    public ImageView imageView;
    public TextView responsavel;
    public TextView tipo;


    public DoacaoHolder(View itemView) {
    super(itemView);
        titulo = (TextView) itemView.findViewById(R.id.txtTitulo);
        descricao = (TextView) itemView.findViewById(R.id.txtDescricao);
        regiao = (TextView)itemView.findViewById(R.id.txtRegiao01);
        btnEditar = (ImageButton) itemView.findViewById(R.id.btnEdit);
        btnExcluir = (ImageButton) itemView.findViewById(R.id.btnDelete);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
        responsavel = (TextView) itemView.findViewById(R.id.txtResponsavel);
        tipo = (TextView) itemView.findViewById(R.id.txtTipo);


    }

}
