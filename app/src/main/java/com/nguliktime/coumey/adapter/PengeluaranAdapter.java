package com.nguliktime.coumey.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nguliktime.coumey.fitur.coumey.DetailPengeluaran;
import com.nguliktime.coumey.R;
import com.nguliktime.coumey.model.PengeluaranModel;

import java.util.List;

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranAdapter.MyViewHolder> {
    List<PengeluaranModel> pengeluaranModels;
    MaterialSearchView.OnQueryTextListener onQueryTextListener;
    Context context;

    public PengeluaranAdapter(Context context, List<PengeluaranModel> pengeluaranModels){
        this.context = context;
        this.pengeluaranModels = pengeluaranModels;
    }

    public PengeluaranAdapter(List<PengeluaranModel> pengeluaranModels) {
        this.pengeluaranModels = pengeluaranModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pengeluaran, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PengeluaranModel model = pengeluaranModels.get(position);
        holder.nama.setText(model.getNama());
        holder.subtotal.setText("Rp " + model.getSubtotal().toString());
        holder.tanggal.setText(model.getTanggal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailPengeluaran.class);
                intent.putExtra("id", model.getId().toString());
                intent.putExtra("nama", model.getNama());
                intent.putExtra("harga", model.getHarga().toString());
                intent.putExtra("jumlah", model.getJumlah().toString());
                intent.putExtra("subtotal", model.getSubtotal().toString());
                intent.putExtra("tanggal", model.getTanggal());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pengeluaranModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, subtotal, tanggal;

        public MyViewHolder(View itemView){
            super(itemView);
            nama = itemView.findViewById(R.id.tvNama);
            subtotal = itemView.findViewById(R.id.tvSubtotal);
            tanggal = itemView.findViewById(R.id.tvTanggal);
        }

    }
}
