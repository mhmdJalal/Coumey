package com.nguliktime.coumey.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fitur.jurnalMapel.DetailJurnalActivity;
import com.nguliktime.coumey.model.JurnalModel;

import java.util.List;

public class JurnalAdapter extends RecyclerView.Adapter<JurnalAdapter.MyViewHolder> {
    List<JurnalModel> jurnalModels;
    Context context;

    public  JurnalAdapter(Context context, List<JurnalModel> jurnalModels){
        this.context = context;
        this.jurnalModels = jurnalModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jurnal, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final JurnalModel model = jurnalModels.get(position);
        holder.mapel.setText(model.getMapel());
        holder.tugas.setText(model.getTugas());
        holder.keterangan.setText(model.getKeterangan());
        holder.deadline.setText(model.getDeadline());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailJurnalActivity.class);
                intent.putExtra("id", model.getId().toString());
                intent.putExtra("mapel", model.getMapel());
                intent.putExtra("tugas", model.getTugas());
                intent.putExtra("minggu", model.getMinggu());
                intent.putExtra("keterangan", model.getKeterangan());
                intent.putExtra("deadline", model.getDeadline());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jurnalModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mapel, tugas, keterangan, deadline;

        public MyViewHolder(View itemView){
            super(itemView);
            mapel = itemView.findViewById(R.id.tvMapel);
            tugas = itemView.findViewById(R.id.tvTugas);
            keterangan = itemView.findViewById(R.id.tvKeterangan);
            deadline = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
