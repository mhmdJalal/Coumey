package com.nguliktime.coumey.fitur.jurnalKegiatan;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nguliktime.coumey.R;
import com.nguliktime.coumey.adapter.KegiatanAdapter;
import com.nguliktime.coumey.adapter.PengeluaranAdapter;
import com.nguliktime.coumey.fitur.coumey.DaftarPengeluaran;
import com.nguliktime.coumey.helper.FunctionHelper;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.KegiatanModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.zhanghai.android.materialedittext.MaterialEditText;

public class ActivityDaftarKegiatan extends AppCompatActivity {
    Toolbar toolbar;
    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    List<KegiatanModel> kegiatanModels;
    KegiatanAdapter kegiatanAdapter;
    FunctionHelper functionHelper;
    LinearLayout linearLayout_1;
    RelativeLayout relativeLayout_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();

        toolbar = findViewById(R.id.toolbar);
        linearLayout_1 = findViewById(R.id.linear_1);
        relativeLayout_1 = findViewById(R.id.relative_1);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Jurnal Kegiatan");

        realmHelper = new RealmHelper(realm);
        kegiatanModels = new ArrayList<>();
        functionHelper = new FunctionHelper();

        kegiatanModels = realmHelper.getKegiatan();
        show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                searchDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_text_search, null, false);

        final MaterialEditText nama;
        nama = view.findViewById(R.id.nama);
        nama.setHint("Cari berdasarkan nama kegiatan");
        builder.setView(view);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!nama.getText().toString().isEmpty()){
                    kegiatanModels = realmHelper.get_kegiatan_by_name(nama.getText().toString());
                    if (!kegiatanModels.isEmpty()){
                        show();
                    }else{
                        MDToast toast = MDToast.makeText(ActivityDaftarKegiatan.this, "Pencarian terhadap " + nama.getText().toString() +" tidak ditemukan", 1000, MDToast.TYPE_INFO);
                        toast.show();
                        recyclerView.setVisibility(View.GONE);
                        linearLayout_1.setVisibility(View.GONE);
                        relativeLayout_1.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(ActivityDaftarKegiatan.this, "Silakan isi terlebih dahulu", Toast.LENGTH_SHORT).show();
                    searchDialog();
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

    public void show(){
        if (kegiatanModels.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            linearLayout_1.setVisibility(View.VISIBLE);
        }else{
            kegiatanAdapter = new KegiatanAdapter(this, kegiatanModels);
            recyclerView.setAdapter(kegiatanAdapter);
        }
    }
}
