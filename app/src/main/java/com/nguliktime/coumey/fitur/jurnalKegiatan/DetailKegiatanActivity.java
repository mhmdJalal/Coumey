package com.nguliktime.coumey.fitur.jurnalKegiatan;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fitur.jurnalMapel.DaftarJurnalActivity;
import com.nguliktime.coumey.fitur.jurnalMapel.DetailJurnalActivity;
import com.nguliktime.coumey.fragment.DatePickerFragment;
import com.nguliktime.coumey.helper.FunctionHelper;
import com.nguliktime.coumey.helper.RealmHelper;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailKegiatanActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    EditText kegiatan, divisi;
    Spinner keterangan;
    TextView tanggal;
    String sKegiatan, sDivisi, sKeterangan, sTanggal;
    Integer id;
    RealmHelper realmHelper;
    Realm realm;
    Toolbar toolbar;
    FunctionHelper functionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();

        realmHelper = new RealmHelper(realm);
        functionHelper = new FunctionHelper();

        toolbar = findViewById(R.id.toolbar);
        kegiatan = findViewById(R.id.kegiatan);
        divisi = findViewById(R.id.divisi);
        tanggal = findViewById(R.id.tanggal);
        keterangan = findViewById(R.id.keterangan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Jurnal Kegiatan");

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        sKegiatan = getIntent().getStringExtra("kegiatan");
        sDivisi = getIntent().getStringExtra("divisi");
        sTanggal = getIntent().getStringExtra("tanggal");
        sKeterangan = getIntent().getStringExtra("keterangan");

        kegiatan.setText(sKegiatan);
        divisi.setText(sDivisi);
        tanggal.setText(sTanggal);
        keterangan.setPrompt(sKeterangan);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tanggal.setText(currentDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDate:
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
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
                                realmHelper.deleteKegiatan(id);
                                functionHelper.showToastSuccess(DetailKegiatanActivity.this, "Berhasil dihapus");
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
            case R.id.btnUpdate:
                if (!kegiatan.getText().toString().isEmpty() && !divisi.getText().toString().isEmpty() && !keterangan.getSelectedItem().toString().isEmpty() && !tanggal.getText().toString().isEmpty()){
                    realmHelper.update_kegiatan(id, kegiatan.getText().toString().trim(), divisi.getText().toString().trim(), keterangan.getSelectedItem().toString().trim(), tanggal.getText().toString().trim());
                    functionHelper.showToastSuccess(DetailKegiatanActivity.this, "Berhasil diubah");
                    startActivity(new Intent(DetailKegiatanActivity.this, ActivityDaftarKegiatan.class));
                    finish();
                }else {
                    functionHelper.showToastWarning(DetailKegiatanActivity.this, "Terdapat inputan yang kosong");
                }
                break;
        }
    }
}
