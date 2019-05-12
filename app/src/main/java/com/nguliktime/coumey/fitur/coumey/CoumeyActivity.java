package com.nguliktime.coumey.fitur.coumey;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nguliktime.coumey.FilterActivity;
import com.nguliktime.coumey.R;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.PengeluaranModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import me.zhanghai.android.materialedittext.MaterialEditText;

public class CoumeyActivity extends AppCompatActivity implements View.OnClickListener{
    android.support.v7.widget.Toolbar toolbar;
    CardView cvSee, cvFilter;
    TextView tvPengeluaran, tvSpenToday;
    Realm realm;
    PengeluaranModel pengeluaranModel;
    Long pengeluaran;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coumey);

        // Setup Realm
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        // Inisialisasi
        toolbar = findViewById(R.id.toolbar);
        cvSee = findViewById(R.id.cvSee);
        cvFilter = findViewById(R.id.cvFilter);
        tvPengeluaran = findViewById(R.id.tvPengeluaran);
        tvSpenToday = findViewById(R.id.tvSpenToday);
        pengeluaranModel = new PengeluaranModel();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Coumey");

        cvSee.setOnClickListener(this);
        cvFilter.setOnClickListener(this);

        show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cvSee:
                startActivity(new Intent(this, DaftarPengeluaran.class));
                break;
            case R.id.cvFilter:
                Intent intent = new Intent(this, FilterActivity.class);
                intent.putExtra("levelFilter", "coumey");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        show();
    }

    @SuppressLint("SetTextI18n")
    private void show(){
        Calendar c = Calendar.getInstance();
        RealmResults<PengeluaranModel> total = realm.where(PengeluaranModel.class).findAll();
        pengeluaran = total.sum("subtotal").longValue();

        tvPengeluaran.setText("Rp " + pengeluaran.toString());

        RealmResults<PengeluaranModel> totaltdy = realm.where(PengeluaranModel.class)
                .equalTo("tanggal", DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime()))
                .findAll();
        Long Spentdy = totaltdy.sum("subtotal").longValue();

        tvSpenToday.setText("Rp " + Spentdy.toString());
    }

    public void add(View view){
        startActivity(new Intent(CoumeyActivity.this, AddCoumeyActivity.class));
    }


}
