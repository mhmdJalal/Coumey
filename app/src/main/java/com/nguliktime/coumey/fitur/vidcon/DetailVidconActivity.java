package com.nguliktime.coumey.fitur.vidcon;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.nguliktime.coumey.adapter.VidconAdapter;
import com.nguliktime.coumey.fitur.coumey.DaftarPengeluaran;
import com.nguliktime.coumey.fitur.coumey.DetailPengeluaran;
import com.nguliktime.coumey.fragment.DatePickerFragment;
import com.nguliktime.coumey.helper.FunctionHelper;
import com.nguliktime.coumey.helper.RealmHelper;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailVidconActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    Button btnhapus, btnubah, btnDate;
    EditText mapel, materi, jam;
    Spinner keterangan;
    TextView tanggal;
    String sMapel, sMateri, sJam, sKeterangan, sTanggal;
    Integer id;
    RealmHelper realmHelper;
    Realm realm;
    Toolbar toolbar;
    FunctionHelper functionHelper;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vidcon);

        //Set up Realm
        context = this;
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        functionHelper = new FunctionHelper();

        // Inisialisasi
        mapel = findViewById(R.id.mapel);
        materi = findViewById(R.id.materi);
        jam = findViewById(R.id.jam);
        keterangan = findViewById(R.id.keterangan);
        toolbar = findViewById(R.id.toolbar);
        tanggal = findViewById(R.id.tanggal);
        btnhapus = findViewById(R.id.btnHapus);
        btnubah = findViewById(R.id.btnUpdate);
        btnDate = findViewById(R.id.btnDate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Video Conference");

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        sMapel = getIntent().getStringExtra("mapel");
        sMateri = getIntent().getStringExtra("materi");
        sJam = getIntent().getStringExtra("jam");
        sKeterangan = getIntent().getStringExtra("keterangan");
        sTanggal = getIntent().getStringExtra("tanggal");

        mapel.setText(sMapel);
        materi.setText(sMateri);
        jam.setText(sJam);
        keterangan.setPrompt(sKeterangan);
        tanggal.setText(sTanggal);

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
        switch (v.getId()){
            case R.id.btnDate:
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                break;
            case R.id.btnUpdate:
                if (mapel.getText().toString().isEmpty() && materi.getText().toString().isEmpty() && jam.getText().toString().isEmpty() && keterangan.getSelectedItem().toString().isEmpty() && tanggal.getText().toString().isEmpty()){
                    functionHelper.showToastWarning(context, "terdapat inputan yang kosong");
                }else {
                    realmHelper.updateVidcon(id, mapel.getText().toString(), materi.getText().toString(), tanggal.getText().toString(), jam.getText().toString(), keterangan.getSelectedItem().toString());
                    functionHelper.showToastSuccess(context, "Data berhasil diubah");
                    finish();
                    mapel.setText("");
                    materi.setText("");
                    jam.setText("");
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
                                realmHelper.deleteVidcon(id);
                                functionHelper.showToastSuccess(context, "Berhasil dihapus");
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
