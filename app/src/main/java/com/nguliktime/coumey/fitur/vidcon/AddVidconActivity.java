package com.nguliktime.coumey.fitur.vidcon;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dd.processbutton.FlatButton;
import com.nguliktime.coumey.R;
import com.nguliktime.coumey.fragment.DatePickerFragment;
import com.nguliktime.coumey.helper.FunctionHelper;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.VidconModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.DateFormat;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddVidconActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    Toolbar toolbar;
    FlatButton btnSimpan, btnDate;
    EditText materi, jam;
    Spinner mapel;
    TextView tvDate;
    String sMapel, sMateri, sJam;
    Realm realm;
    RealmHelper realmHelper;
    VidconModel vidconModel;
    FunctionHelper functionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vidcon);

        //Set up Realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();

        // Inisialisasi
        toolbar = findViewById(R.id.toolbar);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnDate = findViewById(R.id.btnDate);
        mapel = findViewById(R.id.mapel);
        materi = findViewById(R.id.materi);
        jam = findViewById(R.id.jam);
        tvDate = findViewById(R.id.tvDate);
        functionHelper = new FunctionHelper();
        if (tvDate.getText().toString().isEmpty()) tvDate.setVisibility(View.GONE);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Video Conference");
        mapel.setPrompt("Pilih Mata Pelajaran");

        btnSimpan.setOnClickListener(this);
        btnDate.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v == btnSimpan){
            sMapel = mapel.getSelectedItem().toString();
            sMateri = materi.getText().toString();
            sJam = jam.getText().toString();

            if (!sMapel.isEmpty() && !sMateri.isEmpty() && !sJam.isEmpty() && !tvDate.getText().toString().isEmpty()){
                vidconModel = new VidconModel();
                vidconModel.setMapel(sMapel);
                vidconModel.setTanggal(tvDate.getText().toString());
                vidconModel.setMateri(sMateri);
                vidconModel.setJam(sJam);

                realmHelper = new RealmHelper(realm);
                realmHelper.saveVidcon(vidconModel);

                functionHelper.showToastSuccess(this, "Berhasil disimpan");
                materi.setText("");
                jam.setText("");
                materi.requestFocus();
            }else{
                MDToast toast = MDToast.makeText(AddVidconActivity.this, "Terdapat inputan yang kosong!", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
                toast.show();
            }
        }else if (v == btnDate) {
            android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        }
    }
}
