package com.nguliktime.coumey.fitur.vidcon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nguliktime.coumey.DaftarFilterActivity;
import com.nguliktime.coumey.R;
import com.nguliktime.coumey.adapter.VidconAdapter;
import com.nguliktime.coumey.fitur.coumey.DaftarPengeluaran;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.VidconModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import me.zhanghai.android.materialedittext.MaterialEditText;

public class VidconActivity extends AppCompatActivity{
    Toolbar toolbar;
    Context context = this;
    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    VidconAdapter vidconAdapter;
    List<VidconModel> vidconModels;
    MaterialSearchView searchView;
    Typeface typeface;
    TextView textView1;
    LinearLayout linearLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vidcon);

        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        vidconModels = new ArrayList<>();
        linearLayout1 = findViewById(R.id.linear_2);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Video Conference");

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        textView1 = findViewById(R.id.textView1);
        typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/roboto_regular.ttf");
        textView1.setTypeface(typeface);

        vidconModels = realmHelper.getVidcon();
        show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.video_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_vidcon:
                link_vicon();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void show(){
        if (vidconModels.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            linearLayout1.setVisibility(View.VISIBLE);
        }else{
            vidconAdapter = new VidconAdapter(this, vidconModels);
            recyclerView.setAdapter(vidconAdapter);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        vidconModels = realmHelper.getVidcon();
        show();
    }

    public void add(View view){
        startActivity(new Intent(VidconActivity.this, AddVidconActivity.class));
    }

    private void link_vicon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflater = LayoutInflater.from(context).inflate(R.layout.layout_spinner_vicon,null,false);
        final Spinner keterangan = inflater.findViewById(R.id.vicon);

        builder.setView(inflater);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri webpage = Uri.parse("http://bit.ly/"+keterangan.getSelectedItem().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }else {
                    MDToast toast = MDToast.makeText(context, "Kesalahan saat memasukkan link", 1000, MDToast.TYPE_INFO);
                    toast.show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}

