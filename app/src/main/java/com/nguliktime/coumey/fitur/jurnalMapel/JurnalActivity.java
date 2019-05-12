package com.nguliktime.coumey.fitur.jurnalMapel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.FilterActivity;

public class JurnalActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    CardView cvSee, cvFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal_mapel);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Jurnal Mapel");

        cvSee = findViewById(R.id.cvSee);
        cvFilter = findViewById(R.id.cvFilter);

        cvSee.setOnClickListener(this);
        cvFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cvSee:
                startActivity(new Intent(JurnalActivity.this, DaftarJurnalActivity.class));
                break;
            case R.id.cvFilter:
                Intent intent = new Intent(this, FilterActivity.class);
                intent.putExtra("levelFilter", "jurnal");
                startActivity(intent);
                break;
        }
    }

    public void add(View view){
        startActivity(new Intent(JurnalActivity.this, AddJurnalActivity.class));
    }
}
