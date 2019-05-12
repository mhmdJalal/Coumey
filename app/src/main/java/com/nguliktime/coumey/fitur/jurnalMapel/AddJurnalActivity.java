package com.nguliktime.coumey.fitur.jurnalMapel;

import android.app.DatePickerDialog;
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
import com.nguliktime.coumey.model.JurnalModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;

public class AddJurnalActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    Toolbar toolbar;
    Button btnSimpan, btnDate;
    EditText tugas;
    Spinner mapel, minggu;
    TextView tvDate;
    String sMapel, sMinggu, sTugas;
    Realm realm;
    RealmHelper realmHelper;
    JurnalModel jurnalModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jurnal_mapel);

        //Set up Realm
        realm = Realm.getDefaultInstance();

        // Inisialisasi
        toolbar = findViewById(R.id.toolbar);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnDate = findViewById(R.id.btnDate);
        mapel = findViewById(R.id.mapel);
        minggu = findViewById(R.id.minggu);
        tugas = findViewById(R.id.tugas);
        tvDate = findViewById(R.id.tvDate);
        if (tvDate.getText().toString().isEmpty()) tvDate.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Jurnal Mapel");

        btnSimpan.setOnClickListener(this);
        btnDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSimpan){
            sMapel = mapel.getSelectedItem().toString();
            sMinggu = minggu.getSelectedItem().toString();
            sTugas = tugas.getText().toString();

            if (!sMapel.isEmpty() && !sMinggu.isEmpty() && !sTugas.isEmpty() && !tvDate.getText().toString().isEmpty()){
                jurnalModel = new JurnalModel();
                jurnalModel.setMapel(sMapel);
                jurnalModel.setMinggu(sMinggu);
                jurnalModel.setTugas(sTugas);
                jurnalModel.setDeadline(tvDate.getText().toString());

                realmHelper = new RealmHelper(realm);
                realmHelper.saveJurnal(jurnalModel);

                MDToast toast = MDToast.makeText(AddJurnalActivity.this, "Berhasil Disimpan!", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                toast.show();
                tugas.setText("");

            }else{
                MDToast toast = MDToast.makeText(AddJurnalActivity.this, "Terdapat inputan yang kosong!", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
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
    }
}
