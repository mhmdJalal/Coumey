package com.nguliktime.coumey.fitur.jurnalKegiatan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nguliktime.coumey.DaftarFilterActivity;
import com.nguliktime.coumey.FilterActivity;
import com.nguliktime.coumey.R;

public class JurnalKegiatan extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    CardView cvSee, cvFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal_kegiatan);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Jurnal Kegiatan");

        cvSee = findViewById(R.id.cvSee);
        cvFilter = findViewById(R.id.cvFilter);

        cvSee.setOnClickListener(this);
        cvFilter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cvSee:
                startActivity(new Intent(JurnalKegiatan.this, ActivityDaftarKegiatan.class));
                break;
            case R.id.cvFilter:
                Intent intent = new Intent(this, FilterActivity.class);
                intent.putExtra("levelFilter", "kegiatan");
                startActivity(intent);
                break;
        }
    }

    public void add(View view){
        startActivity(new Intent(JurnalKegiatan.this, ActivityAddKegiatan.class));
    }
}
