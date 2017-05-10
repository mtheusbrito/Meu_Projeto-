package com.projeto.tcc.coleta_de_leite.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projeto.tcc.coleta_de_leite.Model.Coletas;
import com.projeto.tcc.coleta_de_leite.Model.Rota;
import com.projeto.tcc.coleta_de_leite.R;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by raphael on 31/03/17.
 */

public class ColetaAdapter  extends ArrayAdapter {
    private Activity context;
    private TextView mText_litros,mText_produtor,mText_data;
    List<Coletas> coletas;

    public ColetaAdapter(Activity context,List<Coletas> coletas ) {
        super(context, R.layout.coleta_layout,coletas);
        this.context=context;
        this.coletas=coletas;

    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.coleta_layout,null,true);
        mText_produtor = (TextView) listViewItem.findViewById(R.id.text_produtor);
        mText_litros = (TextView) listViewItem.findViewById(R.id.text_litros);
        Coletas coleta1 =coletas.get(position);
        mText_produtor.setText(coleta1.getNomeProdutor());
        mText_litros.setText(coleta1.getLitrosColeta());

        return listViewItem;
    }
}
