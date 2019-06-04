package com.example.doacoes;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    String pegaTitulo;
    String pegaDescricao;
    String pegaRegiao;
    String pegaResponsavel;
    String pegaTipo;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView titulo = (TextView)findViewById(R.id.txtTitulo);
        TextView descricao = (TextView)findViewById(R.id.txtDescricao);
        TextView regiao = (TextView)findViewById(R.id.txtRegiao);
        TextView responsavel = (TextView)findViewById(R.id.txtResponsavel);
        TextView tipo = (TextView)findViewById(R.id.txtTipo);


         Intent receveidIntent = getIntent();


        pegaTitulo = receveidIntent.getStringExtra("doacaotitulo");
        titulo.setText(pegaTitulo);

        pegaDescricao = receveidIntent.getStringExtra("doacaodesc");
        descricao.setText(pegaDescricao);

        pegaRegiao =  receveidIntent.getStringExtra("doacaoregiao");
         regiao.setText(pegaRegiao);

        pegaResponsavel = receveidIntent.getStringExtra("doacaoresponsavel");
         responsavel.setText(pegaResponsavel);

         pegaTipo = receveidIntent.getStringExtra("doacaotipo");
         tipo.setText(pegaTipo);




        }
}
