package com.nguliktime.coumey.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fitur.jurnalKegiatan.DetailKegiatanActivity;
import com.nguliktime.coumey.model.KegiatanModel;

import java.util.List;

public class KegiatanAdapter extends RecyclerView.Adapter<KegiatanAdapter.MyViewHolder> {
    List<KegiatanModel> kegiatanModels;
    Context context;

    public KegiatanAdapter(Context context, List<KegiatanModel> kegiatanModels){
        this.kegiatanModels = kegiatanModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kegiatan, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final KegiatanModel model = kegiatanModels.get(position);
        holder.kegiatan.setText(model.getKegiatan());
        holder.keterangan.setText(model.getKeterangan());
        holder.tanggal.setText(model.getTanggal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(v.getContext(), DetailKegiatanActivity.class);
                intent.putExtra("id", model.getId().toString());
                intent.putExtra("kegiatan", model.getKegiatan());
                intent.putExtra("divisi", model.getDivisi());
                intent.putExtra("keterangan", model.getKeterangan());
                intent.putExtra("tanggal", model.getTanggal());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kegiatanModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView kegiatan, keterangan, tanggal;

        public MyViewHolder(View itemView) {
            super(itemView);
            kegiatan = itemView.findViewById(R.id.tvKegiatan);
            keterangan = itemView.findViewById(R.id.tvKeterangan);
            tanggal = itemView.findViewById(R.id.tvTanggal);
        }
    }
}
