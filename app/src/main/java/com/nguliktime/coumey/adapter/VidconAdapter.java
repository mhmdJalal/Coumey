package com.nguliktime.coumey.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fitur.vidcon.DetailVidconActivity;
import com.nguliktime.coumey.model.VidconModel;

import java.util.List;

public class VidconAdapter extends RecyclerView.Adapter<VidconAdapter.MyViewHolder> {
    List<VidconModel> vidconModels;
    Context context;

    public VidconAdapter(Context context, List<VidconModel> vidconModels){
        this.context = context;
        this.vidconModels = vidconModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_vidcon, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VidconModel model = vidconModels.get(position);
        holder.mapel.setText(model.getMapel());
        holder.keterangan.setText(model.getKeterangan());
        holder.tanggal.setText(model.getTanggal());
        holder.materi.setText(model.getMateri());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailVidconActivity.class);
                intent.putExtra("id", model.getId().toString());
                intent.putExtra("mapel", model.getMapel());
                intent.putExtra("materi", model.getMateri());
                intent.putExtra("tanggal", model.getTanggal());
                intent.putExtra("jam", model.getJam());
                intent.putExtra("keterangan", model.getKeterangan());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vidconModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mapel, keterangan, materi, tanggal;

        public MyViewHolder(View itemView){
            super(itemView);
            mapel = itemView.findViewById(R.id.tvMapel);
            keterangan = itemView.findViewById(R.id.tvKeterangan);
            tanggal = itemView.findViewById(R.id.tvTanggal);
            materi = itemView.findViewById(R.id.tvMateri);
        }
    }
}
