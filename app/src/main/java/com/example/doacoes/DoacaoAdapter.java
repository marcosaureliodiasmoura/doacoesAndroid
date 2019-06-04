package com.example.doacoes;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import java.util.List;


public class DoacaoAdapter extends RecyclerView.Adapter<DoacaoHolder> {

    public final List<Doacao> doacoes;
    public Doacao doacao;
    private Context mContext;


    public DoacaoAdapter(MainActivity mainActivity, List<Doacao> doacoes, Context context) {
        this.doacoes = doacoes;
        mContext = context;

    }


    //Método que deverá retornar layout criado pelo ViewHolder já inflado em uma view.
    @NonNull
    @Override
    public DoacaoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista, viewGroup, false);
        return new DoacaoHolder(view);

    }


//Método que recebe o ViewHolder e a posição da lista. Aqui é recuperado o objeto da lista de Objetos
// pela posição e associado à ViewHolder. É onde a mágica acontece!
    @Override
    public void onBindViewHolder(final DoacaoHolder holder, final int position) {


        holder.titulo.setText(doacoes.get(position).getTitulo());
        holder.descricao.setText(doacoes.get(position).getDescricao());
        holder.regiao.setText(doacoes.get(position).getRegiao());
        holder.responsavel.setText(doacoes.get(position).getResponsavel());
        holder.tipo.setText(doacoes.get(position).getTipo());

        Picasso.with(mContext)
                .load("https://vaivalerapena.net.br/wp-content/uploads/2018/03/980x.png")
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              String mandaTitulo =  holder.titulo.getText().toString();
              String mandaDescricao = holder.descricao.getText().toString();
              String mandaRegiao = holder.regiao.getText().toString();
              String mandaResponsavel = holder.responsavel.getText().toString();
              String mandaTipo = holder.tipo.getText().toString();

               Intent intent = new Intent(mContext, Main2Activity.class);
//                intent.putExtra("doacao", "Olá, tudo bem?");
                intent.putExtra("doacaotitulo", mandaTitulo);
                intent.putExtra("doacaodesc", mandaDescricao);
                intent.putExtra("doacaoregiao", mandaRegiao);
                intent.putExtra("doacaoresponsavel", mandaResponsavel);
                intent.putExtra("doacaotipo", mandaTipo);



                mContext.startActivity(intent);
            }
        });


        final Doacao doacao = doacoes.get(position);
        holder.btnExcluir.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja excluir este cliente?")
                        .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Doacao doacao = doacoes.get(index);
                                DoacaoDAO dao = new DoacaoDAO(view.getContext());
                                boolean sucesso = dao.excluir(doacao.getId());
                                if(sucesso) {
                                    removerDoacao(doacao);
                                    Snackbar.make(view, "Excluiu!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    Snackbar.make(view, "Erro ao excluir o cliente!", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show();
            }
        });


        holder.btnEditar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(v);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("doacao", doacao);
                activity.finish();
                activity.startActivity(intent);
            }
        });
}

//    Método que deverá retornar quantos itens há na lista.
//    Aconselha-se verificar se a lista não está nula como no exemplo,
//    pois ao tentar recuperar a quantidade da lista nula pode gerar um erro em tempo de execução
//    (NullPointerException).
    @Override
    public int getItemCount() {
        return doacoes != null ? doacoes.size() : 0;
    }

    public void adicionarDoacao(Doacao doacao){
        doacoes.add(doacao);
        notifyItemInserted(getItemCount());
    }

    public void atualizarDoacao(Doacao doacao){
        doacoes.set(doacoes.indexOf(doacao), doacao);
        notifyItemChanged(doacoes.indexOf(doacao));
    }

    public void removerDoacao(Doacao doacao){
        // pega a posicao, remove e atualiza a lista do rcyclerview
        int position = doacoes.indexOf(doacao);
        doacoes.remove(position);
        notifyItemRemoved(position);
    }

    //Possui a logica do click dos botões,
    // este método que permite obter uma Activity a partir de uma View qualquer
    private Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }




}

