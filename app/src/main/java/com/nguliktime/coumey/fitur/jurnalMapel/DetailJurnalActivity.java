package com.nguliktime.coumey.fitur.jurnalMapel;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fragment.DatePickerFragment;
import com.nguliktime.coumey.helper.RealmHelper;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailJurnalActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    Button btnhapus, btnubah, btnDate;
    EditText mapel, tugas, minggu;
    Spinner keterangan;
    TextView tanggal;
    String sMapel, sTugas, sMinggu, sKeterangan, sDeadline;
    Integer id;
    RealmHelper realmHelper;
    Realm realm;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jurnal_mapel);

        //Set up Realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        // Inisialisasi
        mapel = findViewById(R.id.mapel);
        tugas = findViewById(R.id.tugas);
        minggu = findViewById(R.id.minggu);
        keterangan = findViewById(R.id.keterangan);
        toolbar = findViewById(R.id.toolbar);
        tanggal = findViewById(R.id.tanggal);
        btnhapus = findViewById(R.id.btnHapus);
        btnubah = findViewById(R.id.btnUpdate);
        btnDate = findViewById(R.id.btnDate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Jurnal Mapel");

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        sMapel = getIntent().getStringExtra("mapel");
        sTugas = getIntent().getStringExtra("tugas");
        sMinggu = getIntent().getStringExtra("minggu");
        sKeterangan = getIntent().getStringExtra("keterangan");
        sDeadline = getIntent().getStringExtra("deadline");

        mapel.setText(sMapel);
        tugas.setText(sTugas);
        minggu.setText(sMinggu);
        keterangan.setPrompt(sKeterangan);
        tanggal.setText(sDeadline);

        btnhapus.setOnClickListener(this);
        btnubah.setOnClickListener(this);
        btnDate.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        final MDToast[] toast = new MDToast[2];
        switch (v.getId()){
            case R.id.btnDate:
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
            case R.id.btnUpdate:
                if (mapel.getText().toString().isEmpty() && tugas.getText().toString().isEmpty() && minggu.getText().toString().isEmpty() && keterangan.getSelectedItem().toString().isEmpty() && tanggal.getText().toString().isEmpty()){
                    toast[2] = MDToast.makeText(DetailJurnalActivity.this, "Terdapat inputan yang kosong!", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
                    toast[2].show();
                }else {
                    realmHelper.updateJurnal(id, mapel.getText().toString(), minggu.getText().toString(), tugas.getText().toString(), keterangan.getSelectedItem().toString(), tanggal.getText().toString());
                    toast[0] = MDToast.makeText(DetailJurnalActivity.this, "Data berhasil diubah!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                    toast[0].show();
                    finish();
                    mapel.setText("");
                    minggu.setText("");
                    tugas.setText("");
                    keterangan.setPrompt("");
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
                                realmHelper.deleteJurnal(id);
                                toast[0] = MDToast.makeText(DetailJurnalActivity.this, "Berhasil dihapus", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                                toast[0].show();
                                startActivity(new Intent(DetailJurnalActivity.this, DaftarJurnalActivity.class));
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
}
