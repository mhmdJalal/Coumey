package com.nguliktime.coumey.fitur.coumey;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fragment.DatePickerFragment;
import com.nguliktime.coumey.helper.RealmHelper;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailPengeluaran extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    Button btnhapus, btnubah, btnDate;
    EditText nama, harga, jumlah;
    TextView tanggal, subtotal;
    String snama, stanggal;
    Integer id, sharga, sjumlah, ssubtotal, editsubtotal;
    RealmHelper realmHelper;
    Realm realm;
    Toolbar toolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengeluaran);

        //Set up Realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        // Inisialisasi
        toolbar = findViewById(R.id.toolbar);
        nama = findViewById(R.id.nama);
        harga = findViewById(R.id.harga);
        jumlah = findViewById(R.id.jumlah);
        subtotal = findViewById(R.id.subtotal);
        tanggal = findViewById(R.id.tanggal);
        btnhapus = findViewById(R.id.btnHapus);
        btnubah = findViewById(R.id.btnUpdate);
        btnDate = findViewById(R.id.btnDate);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Detail Pengeluaran");


        id = Integer.parseInt(getIntent().getStringExtra("id"));
        snama = getIntent().getStringExtra("nama");
        sharga = Integer.parseInt(getIntent().getStringExtra("harga"));
        sjumlah = Integer.parseInt(getIntent().getStringExtra("jumlah"));
        ssubtotal = Integer.parseInt(getIntent().getStringExtra("subtotal"));
        stanggal = getIntent().getStringExtra("tanggal");

        nama.setText(snama);
        harga.setText(sharga.toString());
        jumlah.setText(sjumlah.toString());
        subtotal.setText("Subtotal Rp" + ssubtotal.toString());
        tanggal.setText(stanggal);

        btnhapus.setOnClickListener(this);
        btnubah.setOnClickListener(this);
        btnDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final MDToast[] toast = new MDToast[2];
        switch (v.getId()){
            case R.id.btnDate:
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
            case R.id.btnUpdate:
                if (nama.getText().toString().isEmpty() && jumlah.getText().toString().isEmpty() && harga.getText().toString().isEmpty()){
                    toast[2] = MDToast.makeText(DetailPengeluaran.this, "Terdapat inputan yang kosong!", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
                    toast[2].show();
                }else {
                    editsubtotal = Integer.parseInt(jumlah.getText().toString()) * Integer.parseInt(harga.getText().toString());
                    realmHelper.update(id, nama.getText().toString(), Integer.parseInt(jumlah.getText().toString()), Integer.parseInt(harga.getText().toString()), editsubtotal, tanggal.getText().toString());
                    toast[0] = MDToast.makeText(DetailPengeluaran.this, "Data berhasil diubah!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    toast[0].show();
                    startActivity(new Intent(DetailPengeluaran.this, DaftarPengeluaran.class));
                    finish();
                    nama.setText("");
                    harga.setText("");
                    jumlah.setText("");
                    subtotal.setText("");
                    tanggal.setText("");
                }
                break;
            case R.id.btnHapus:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(R.string.app_name)
                        .setMessage("Yakin ingin dihapus?")
                        .setIcon(R.drawable.idea2)
                        .setCancelable(false)
                        .setPositiveButton("Yes, sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                realmHelper.delete(id);
                                toast[0] = MDToast.makeText(DetailPengeluaran.this, "Berhasil dihapus", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                toast[0].show();
                                startActivity(new Intent(DetailPengeluaran.this, DaftarPengeluaran.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tanggal.setText(currentDateString);
    }
}
