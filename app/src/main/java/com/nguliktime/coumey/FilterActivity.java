package com.nguliktime.coumey;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.nguliktime.coumey.fragment.DatePickerFragment;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.PengeluaranModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    Toolbar toolbar;
    CardView byBulan, byBulan2, byKeterangan, byMapel;
    Realm realm;
    RealmHelper realmHelper;
    List<PengeluaranModel> pengeluaranModels;
    private String FILTER = null;
    private String FILTER_BY = null;
    private String currentDate = null;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        realmHelper = new RealmHelper(realm);
        pengeluaranModels = new ArrayList<>();

        FILTER_BY = getIntent().getStringExtra("levelFilter");

        if (FILTER_BY.equals("coumey")){
            setContentView(R.layout.activity_filter_coumey);

            toolbar = findViewById(R.id.toolbar);
            byBulan = findViewById(R.id.byBulan);
            byBulan2 = findViewById(R.id.byBulan2);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Coumey");

            byBulan.setOnClickListener(this);
            byBulan2.setOnClickListener(this);
        }else if(FILTER_BY.equals("jurnal")){
            setContentView(R.layout.activity_filter_jurnal_mapel);

            toolbar = findViewById(R.id.toolbar);
            byKeterangan = findViewById(R.id.byKeterangan);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Jurnal Mapel");

            byKeterangan.setOnClickListener(this);
        }else {
            setContentView(R.layout.activity_filter_kegiatan);

            toolbar = findViewById(R.id.toolbar);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Jurnal Kegiatan");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.byTanggal:
                FILTER = "byTanggal";
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
            case R.id.byBulan:
                FILTER = "byBulan";
                filter();
                break;
            case R.id.byBulan2:
                FILTER = "betweenBulan";
                filter();
                break;
            case R.id.byKeterangan:
                FILTER = "byKeterangan";
                filter();
                break;
            case R.id.byMapel:
                FILTER = "byMapel";
                filter();
                break;
            case R.id.byMinggu:
                FILTER = "byMinggu";
                filter();
                break;
            default:
                break;
        }
    }

    @SuppressLint("InflateParams")
    private void filter(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View[] inflater = {null};
        final Spinner[] bulan = new Spinner[1];
        final Spinner[] b_bulan1 = new Spinner[1];
        final Spinner[] b_bulan2 = new Spinner[1];
        final Spinner[] spinners = new Spinner[3];

        switch (FILTER){
            case "byBulan":
                inflater[0] = LayoutInflater.from(context).inflate(R.layout.layout_spinner_by_month,null,false);
                break;
            case "betweenBulan":
                inflater[0] = LayoutInflater.from(context).inflate(R.layout.layout_spinner_between_month,null,false);
                break;
            case "byKeterangan":
                inflater[0] = LayoutInflater.from(context).inflate(R.layout.layout_spinner_keterangan,null,false);
                break;
            case "byMapel":
                inflater[0] = LayoutInflater.from(context).inflate(R.layout.layout_spinner_by_mapel,null,false);
                break;
            case "byMinggu":
                inflater[0] = LayoutInflater.from(context).inflate(R.layout.layout_spinner_by_minggu,null,false);
                break;
        }

        builder.setView(inflater[0]);

        bulan[0] = inflater[0].findViewById(R.id.bulan);
        b_bulan1[0] = inflater[0].findViewById(R.id.b_bulan1);
        b_bulan2[0] = inflater[0].findViewById(R.id.b_bulan2);
        spinners[0] = inflater[0].findViewById(R.id.keterangan);
        spinners[1] = inflater[0].findViewById(R.id.mapel);
        spinners[2] = inflater[0].findViewById(R.id.minggu);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (FILTER){
                    case "byBulan":
                        Intent intent = new Intent(context, DaftarFilterActivity.class);
                        intent.putExtra("filter", FILTER);
                        intent.putExtra("bulan", bulan[0].getSelectedItem().toString());
                        intent.putExtra("levelFilter", "coumey");
                        startActivity(intent);
                        break;
                    case "betweenBulan":
                        if (b_bulan2[0].getSelectedItemId() < b_bulan1[0].getSelectedItemId()){
                            showToast("Pastikan Anda memasukkan bulan dengan benar");
                            break;
                        }
                        Intent intent1 = new Intent(context, DaftarFilterActivity.class);
                        intent1.putExtra("bulan1", String.valueOf(b_bulan1[0].getSelectedItemId()));
                        intent1.putExtra("bulan2", String.valueOf(b_bulan2[0].getSelectedItemId()));
                        intent1.putExtra("filter", FILTER);
                        intent1.putExtra("levelFilter", "coumey");
                        startActivity(intent1);
                        break;
                    case "byKeterangan":
                        Intent intent2 = new Intent(context, DaftarFilterActivity.class);
                        intent2.putExtra("filter", FILTER);
                        intent2.putExtra("keterangan", spinners[0].getSelectedItem().toString());
                        intent2.putExtra("levelFilter", "jurnal");
                        startActivity(intent2);
                        break;
                    case "byMapel":
                        Intent intent3 = new Intent(context, DaftarFilterActivity.class);
                        intent3.putExtra("filter", FILTER);
                        intent3.putExtra("mapel", spinners[1].getSelectedItem().toString());
                        intent3.putExtra("levelFilter", "jurnal");
                        startActivity(intent3);
                        break;
                    case "byMinggu":
                        intent3 = new Intent(context, DaftarFilterActivity.class);
                        intent3.putExtra("filter", FILTER);
                        intent3.putExtra("minggu", spinners[2].getSelectedItem().toString());
                        intent3.putExtra("levelFilter", "jurnal");
                        startActivity(intent3);
                        break;
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showToast(String pesan){
        MDToast toast = MDToast.makeText(this, pesan, 1000, MDToast.TYPE_INFO);
        toast.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        Intent intent = new Intent(context, DaftarFilterActivity.class);
        intent.putExtra("tanggal", currentDate);
        intent.putExtra("filter", FILTER);
        intent.putExtra("levelFilter", FILTER_BY);
        startActivity(intent);
    }

    public void filter_by_keterangan_kegiatan(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflater = LayoutInflater.from(context).inflate(R.layout.layout_spinner_keterangan,null,false);
        final Spinner keterangan = inflater.findViewById(R.id.keterangan);

        builder.setView(inflater);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent2 = new Intent(context, DaftarFilterActivity.class);
                intent2.putExtra("filter", "byKeterangan");
                intent2.putExtra("keterangan", keterangan.getSelectedItem().toString());
                intent2.putExtra("levelFilter", "kegiatan");
                startActivity(intent2);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
