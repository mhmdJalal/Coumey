package com.nguliktime.coumey.fitur.coumey;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fragment.DatePickerFragment;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.PengeluaranModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddCoumeyActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    android.support.v7.widget.Toolbar toolbar;
    Button btnSimpan, btnDate;
    EditText nama, jumlah, harga;
    TextView tvDate;
    String sNama;
    Integer sJumlah, sHarga, subtotal;
    Realm realm;
    RealmHelper realmHelper;
    PengeluaranModel pengeluaranModel;

    private static String BULAN = null;
    private Integer ID_BULAN = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coumey);

        //Set up Realm
        realm = Realm.getDefaultInstance();

        // Inisialisasi
        toolbar = findViewById(R.id.toolbar);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnDate = findViewById(R.id.btnDate);
        nama = findViewById(R.id.nama);
        jumlah = findViewById(R.id.jumlah);
        harga = findViewById(R.id.harga);
        tvDate = findViewById(R.id.tvDate);
        if (tvDate.getText().toString().isEmpty()) tvDate.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Coumey");

        btnSimpan.setOnClickListener(this);
        btnDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSimpan){
            sNama = nama.getText().toString();

            if (!sNama.isEmpty() && !jumlah.getText().toString().isEmpty() && !harga.getText().toString().isEmpty() && !tvDate.getText().toString().isEmpty()){
                sJumlah = Integer.parseInt(jumlah.getText().toString());
                sHarga = Integer.parseInt(harga.getText().toString());
                subtotal = sJumlah * sHarga;
                pengeluaranModel = new PengeluaranModel();
                pengeluaranModel.setNama(sNama);
                pengeluaranModel.setJumlah(sJumlah);
                pengeluaranModel.setHarga(sHarga);
                pengeluaranModel.setSubtotal(subtotal);
                pengeluaranModel.setTanggal(tvDate.getText().toString());
                pengeluaranModel.setBulan(BULAN);
                pengeluaranModel.setIdBulan(ID_BULAN);

                realmHelper = new RealmHelper(realm);
                realmHelper.save(pengeluaranModel);

                MDToast toast = MDToast.makeText(AddCoumeyActivity.this, "Berhasil Disimpan!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                toast.show();
                nama.setText("");
                harga.setText("");
                jumlah.setText("");
                nama.requestFocus();
            }else{
                MDToast toast = MDToast.makeText(AddCoumeyActivity.this, "Terdapat inputan yang kosong!", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
                toast.show();
            }
        }else if (v == btnDate) {
            android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tvDate.setText(currentDateString);
        tvDate.setVisibility(View.VISIBLE);

        ID_BULAN = c.get(Calendar.MONTH);

        switch (ID_BULAN){
            case 0:
                BULAN = "Januari";
                break;
            case 1:
                BULAN = "Februari";
                break;
            case 2:
                BULAN = "Maret";
                break;
            case 3:
                BULAN = "April";
                break;
            case 4:
                BULAN = "Mei";
                break;
            case 5:
                BULAN = "Juni";
                break;
            case 6:
                BULAN = "Juli";
                break;
            case 7:
                BULAN = "Agustus";
                break;
            case 8:
                BULAN = "September";
                break;
            case 9:
                BULAN = "Oktober";
                break;
            case 10:
                BULAN = "November";
                break;
            case 11:
                BULAN = "Desember";
                break;
        }
    }
}
