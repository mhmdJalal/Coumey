package com.nguliktime.coumey.fitur.jurnalKegiatan;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fitur.coumey.AddCoumeyActivity;
import com.nguliktime.coumey.fragment.DatePickerFragment;
import com.nguliktime.coumey.helper.FunctionHelper;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.KegiatanModel;
import com.nguliktime.coumey.model.PengeluaranModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.zhanghai.android.materialedittext.MaterialEditText;

public class ActivityAddKegiatan extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    Toolbar toolbar;
    TextView date;
    MaterialEditText divisi, kegiatan;
    Realm realm;
    RealmHelper realmHelper;
    KegiatanModel kegiatanModel;
    FunctionHelper functionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kegiatan);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();

        functionHelper = new FunctionHelper();
        toolbar = findViewById(R.id.toolbar);
        divisi = findViewById(R.id.divisi);
        kegiatan = findViewById(R.id.kegiatan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Jurnal Kegiatan");

        date = findViewById(R.id.tvDate);
        if (date.getText().toString().isEmpty()) date.setVisibility(View.GONE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        date.setVisibility(View.VISIBLE);
        date.setText(currentDateString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSimpan:
                if (!divisi.getText().toString().isEmpty() && !kegiatan.getText().toString().isEmpty() && !date.getText().toString().isEmpty()){
                    kegiatanModel = new KegiatanModel();
                    kegiatanModel.setDivisi(divisi.getText().toString().trim());
                    kegiatanModel.setKegiatan(kegiatan.getText().toString().trim());
                    kegiatanModel.setTanggal(date.getText().toString().trim());

                    realmHelper = new RealmHelper(realm);
                    realmHelper.saveKegiatan(kegiatanModel);
                    divisi.setText("");
                    kegiatan.setText("");
                    divisi.requestFocus();

                    functionHelper.showToastSuccess(this, "Berhasil disimpan");
                }else{
                    functionHelper.showToastWarning(this, "Terdapat inputan yang kosong");
                }
                break;
            case R.id.btnDate:
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "date picker");
                break;
        }
    }
}