package com.projects.educacidadaoapp.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.educacidadaoapp.R;
import com.projects.educacidadaoapp.model.Curso;
import com.projects.educacidadaoapp.model.CursoDataModel;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }

    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRecyclerTitulo;
        TextView textViewRecyclerDescricao;
        TextView textViewRecyclerInicio;
        TextView textViewRecyclerTermino;
        TextView textViewRecyclerHorario;
        TextView textViewRecyclerInstrutor;
        TextView textViewRecyclerCondicao;
        TextView textViewRecyclerValor;

        public ViewHolder (View itemView) {
            super(itemView);
            textViewRecyclerTitulo = itemView.findViewById(R.id.textViewRecyclerTitulo);
            textViewRecyclerDescricao = itemView.findViewById(R.id.textViewRecyclerDescricao);
            textViewRecyclerInicio = itemView.findViewById(R.id.textViewRecyclerInicio);
            textViewRecyclerTermino = itemView.findViewById(R.id.textViewRecyclerTermino);
            textViewRecyclerHorario = itemView.findViewById(R.id.textViewRecyclerHorario);
            textViewRecyclerInstrutor = itemView.findViewById(R.id.textViewRecyclerInstrutor);
            textViewRecyclerCondicao = itemView.findViewById(R.id.textViewRecyclerCondicao);
            textViewRecyclerValor = itemView.findViewById(R.id.textViewRecyclerValor);

            itemView.setOnClickListener(view -> {
                if (clickListener != null) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener((view -> {
                if (longClickListener != null) {
                    return longClickListener.onItemLongClick(view, getAdapterPosition());
                }
                return false;
            }));

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(
                R.layout.curso_recycler_view,
                parent,
                false
        );
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Curso c = CursoDataModel.getInstance().getCurso(position);
        holder.textViewRecyclerTitulo.setText(c.getTitulo());
        holder.textViewRecyclerDescricao.setText(c.getDescricao());
        holder.textViewRecyclerInicio.setText(c.getInicio());
        holder.textViewRecyclerTermino.setText(c.getTermino());
        holder.textViewRecyclerHorario.setText(c.getHorario());
        holder.textViewRecyclerInstrutor.setText(c.getInstrutor());
        holder.textViewRecyclerCondicao.setText(c.getCondicao());
        holder.textViewRecyclerValor.setText(c.getValor());
    }

    @Override
    public int getItemCount() {
        return CursoDataModel.getInstance().getCursosSize();
    }

}
