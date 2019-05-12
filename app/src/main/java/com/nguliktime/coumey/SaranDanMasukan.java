package com.nguliktime.coumey;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nguliktime.coumey.helper.FunctionHelper;

import me.zhanghai.android.materialedittext.MaterialEditText;

public class SaranDanMasukan extends AppCompatActivity{
    MaterialEditText subject, deskripsi;
    Toolbar toolbar;
    FunctionHelper functionHelper;
    Typeface typeface;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saran_dan_masukan);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Saran dan Masukkan");
        subject = findViewById(R.id.title);
        deskripsi  = findViewById(R.id.deskripsi);
        textView = findViewById(R.id.textView);
        functionHelper = new FunctionHelper();
        typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/roboto_regular.ttf");
        textView.setTypeface(typeface);
    }

    public void send_email(View view){
        if (!subject.getText().toString().isEmpty() && !deskripsi.getText().toString().isEmpty()){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            String mailto = "mailto:muhamadjalaludin68@gmail.com"+
                    "?subject=" + Uri.encode("[Courrin] " + subject.getText().toString()) +
                    "&body=" + Uri.encode(deskripsi.getText().toString());
            intent.setData(Uri.parse(mailto));

            try {
                startActivity(Intent.createChooser(intent, "Choose an email client"));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                functionHelper.showToastWarning(SaranDanMasukan.this, "There is no email client installed");
            }
        }else {
            functionHelper.showToastWarning(SaranDanMasukan.this, "Silakan isi terlebih dahulu");
        }
    }

}