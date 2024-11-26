package com.example.apptarefa;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class TarefaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Tarefa> tarefas;
    private LayoutInflater inflater;

    public TarefaAdapter(Context context, ArrayList<Tarefa> tarefas) {
        this.context = context;
        this.tarefas = tarefas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_tarefa, parent, false);
        }

        TextView tituloTarefa = convertView.findViewById(R.id.tituloTarefa);
        TextView descricaoTarefa = convertView.findViewById(R.id.descricaoTarefa);
        TextView dataTerminoTarefa = convertView.findViewById(R.id.dataTerminoTarefa);
        TextView concluidaTarefa = convertView.findViewById(R.id.concluidaTarefa);

        Tarefa tarefa = tarefas.get(position);

        tituloTarefa.setText("Título: " + tarefa.getTitulo());
        descricaoTarefa.setText("Descrição: " + tarefa.getDescricao());
        dataTerminoTarefa.setText("Data Término: " + tarefa.getDataTermino());
        concluidaTarefa.setText("Concluída: " + (tarefa.isConcluida() ? "SIM" : "NÃO"));

        return convertView;
    }
}