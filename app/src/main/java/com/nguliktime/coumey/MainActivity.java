package com.nguliktime.coumey;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nguliktime.coumey.fitur.coumey.CoumeyActivity;
import com.nguliktime.coumey.fitur.jurnalKegiatan.JurnalKegiatan;
import com.nguliktime.coumey.fitur.jurnalMapel.JurnalActivity;
import com.nguliktime.coumey.fitur.vidcon.VidconActivity;
import com.nguliktime.coumey.helper.FunctionHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    android.support.v7.widget.Toolbar toolbar;
    CardView cvCoumey, cvJurnal, cvVidcon, cvKegiatan;
    Typeface typeface;
    FunctionHelper functionHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/roboto_regular.ttf");

        // Inisialisasi
        toolbar = findViewById(R.id.toolbar);
        cvCoumey = findViewById(R.id.cvCoumey);
        cvJurnal = findViewById(R.id.cvJurnal);
        cvVidcon = findViewById(R.id.cvVicon);
        cvKegiatan = findViewById(R.id.cvJurnalKegiatan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("COURRIN");
        functionHelper = new FunctionHelper();

        cvCoumey.setOnClickListener(this);
        cvJurnal.setOnClickListener(this);
        cvVidcon.setOnClickListener(this);
        cvKegiatan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cvCoumey:
                startActivity(new Intent(MainActivity.this, CoumeyActivity.class));
                break;
            case R.id.cvJurnal:
                startActivity(new Intent(MainActivity.this, JurnalActivity.class));
                break;
            case R.id.cvVicon:
                startActivity(new Intent(MainActivity.this, VidconActivity.class));
                break;
            case R.id.cvJurnalKegiatan:
                startActivity(new Intent(MainActivity.this, JurnalKegiatan.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            case R.id.saran:
                startActivity(new Intent(MainActivity.this, SaranDanMasukan.class));
                break;
            case R.id.keluar:
                showDialog();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.app_name)
                .setMessage("Keluar dari Aplikasi?")
                .setIcon(R.drawable.idea2)
                .setCancelable(false)
                .setPositiveButton("Yes, sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
    }
}
