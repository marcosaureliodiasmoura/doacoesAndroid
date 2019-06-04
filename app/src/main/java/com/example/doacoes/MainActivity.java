package com.example.doacoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DoacaoAdapter mAdapter;

    Doacao doacaoEditada = null;
    public Doacao doacao;

    private ArrayList<Doacao> mNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        // Configurando o gerenciador de layout para ser uma lista.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        // Está sendo criado com lista vazia, pois será preenchida posteriormente.
        mAdapter = new DoacaoAdapter(this, new ArrayList<Doacao>(0),  this);
//        mAdapter = new DoacaoAdapter(this, new ArrayList<Doacao>(0), this, this);

        mRecyclerView.setAdapter(mAdapter);


        // Configurando um dividr entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        configurarRecycler();

        Intent intent = getIntent();
        if (intent.hasExtra("doacao")) {
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            doacaoEditada = (Doacao) intent.getSerializableExtra("doacao");
            EditText editTitulo = (EditText) findViewById(R.id.edtTitulo);
            EditText editDescricao = (EditText) findViewById(R.id.edtDescricao);
            Spinner spnEstado = (Spinner) findViewById(R.id.spnEstado);
            EditText editResponsavel = (EditText) findViewById(R.id.edtResponsavel);
            Spinner spnTipo = (Spinner) findViewById(R.id.spnTipo);


            editTitulo.setText(doacaoEditada.getTitulo());
            editDescricao.setText(doacaoEditada.getDescricao());
            spnEstado.setSelection(getIndex(spnEstado, doacaoEditada.getRegiao()));
            spnTipo.setSelection(getIndex(spnTipo, doacaoEditada.getTipo()));
            editResponsavel.setText(doacaoEditada.getResponsavel());





        }


        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //carregando os campos
                EditText txtTitulo = (EditText) findViewById(R.id.edtTitulo);
                EditText txtDescricao = (EditText) findViewById(R.id.edtDescricao);
                Spinner spnEstado = (Spinner) findViewById(R.id.spnEstado);
                EditText txtResponsavel = (EditText) findViewById(R.id.edtResponsavel);
                Spinner spnTipo = (Spinner) findViewById(R.id.spnTipo);

                //pegando os valores
                String titulo = txtTitulo.getText().toString();
                String descricao = txtDescricao.getText().toString();
                String regiao = spnEstado.getSelectedItem().toString();
                String responsavel = txtResponsavel.getText().toString();
                String tipo = spnTipo.getSelectedItem().toString();


                //salvando os dados
                DoacaoDAO dao = new DoacaoDAO(getBaseContext());
//                boolean sucesso = dao.salvar(titulo, descricao, regiao);
                boolean sucesso;

                if (doacaoEditada != null)
                    sucesso = dao.salvar(doacaoEditada.getId(), titulo, descricao, regiao, responsavel, tipo);
                else
                    sucesso = dao.salvar(titulo, descricao, regiao, responsavel, tipo);

                if (sucesso) {
                    Doacao doacao = dao.retornarUltimo();
                    if (doacaoEditada != null) {
                        mAdapter.atualizarDoacao(doacao);
                        doacaoEditada = null;
                        configurarRecycler();

                    } else
                        mAdapter.adicionarDoacao(doacao);



                    //limpa os campos
                    txtTitulo.setText("");
                    txtDescricao.setText("");
                    txtResponsavel.setText("");
                    spnTipo.setSelection(0);
                    spnEstado.setSelection(0);

                    Snackbar.make(view, "Salvou!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                    findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                    findViewById(R.id.fab).setVisibility(View.VISIBLE);
//                    configurarRecycler();

                } else {
                    Snackbar.make(view, "Erro ao salvar, consulte os logs!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.includemain).setVisibility(View.VISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecadastro).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            }
        });
    }


    private void configurarRecycler() {
        // Configurando o gerenciador de layout para ser uma lista.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        // Adiciona o adapter que irá anexar os objetos à lista.
        DoacaoDAO dao = new DoacaoDAO(this);
        mAdapter = new DoacaoAdapter(this, dao.retornarTodos(),  this);
//        mAdapter = new DoacaoAdapter(this, dao.retornarTodos(), this, this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }



}