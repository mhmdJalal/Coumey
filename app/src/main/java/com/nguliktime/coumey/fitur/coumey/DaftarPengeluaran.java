package com.nguliktime.coumey.fitur.coumey;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nguliktime.coumey.R;
import com.nguliktime.coumey.adapter.PengeluaranAdapter;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.PengeluaranModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.zhanghai.android.materialedittext.MaterialEditText;

public class DaftarPengeluaran extends AppCompatActivity {
    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    PengeluaranAdapter pengeluaranAdapter;
    List<PengeluaranModel> pengeluaranModels;
    Toolbar toolbar;
    LinearLayout linearLayout_1;
    RelativeLayout relative_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pengeluaran);

        toolbar = findViewById(R.id.toolbar);
        linearLayout_1 = findViewById(R.id.linear_1);
        relative_1 = findViewById(R.id.relative_1);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Coumey");

        // Setup Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();

        realmHelper = new RealmHelper(realm);
        pengeluaranModels = new ArrayList<>();

        pengeluaranModels = realmHelper.getAllData();
        show();
    }

    private void searchDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_text_search, null, false);

        final MaterialEditText nama;
        nama = view.findViewById(R.id.nama);
        nama.setHint("Cari berdasarkan nama");
        builder.setView(view);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!nama.getText().toString().isEmpty()){
                    pengeluaranModels = realmHelper.getSpendingByName(nama.getText().toString());
                    if (!pengeluaranModels.isEmpty()){
                        show();
                    }else{
                        MDToast toast = MDToast.makeText(DaftarPengeluaran.this, "Pencarian terhadap " + nama.getText().toString() +" tidak ditemukan", 1000, MDToast.TYPE_INFO);
                        toast.show();
                        recyclerView.setVisibility(View.GONE);
                        linearLayout_1.setVisibility(View.GONE);
                        relative_1.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(DaftarPengeluaran.this, "Silakan isi terlebih dahulu", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        show();
    }

    private void showToast(String pesan){
        MDToast toast = MDToast.makeText(this, pesan, 1000, MDToast.TYPE_INFO);
        toast.show();
    }

    public void show(){
        if (pengeluaranModels.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            linearLayout_1.setVisibility(View.VISIBLE);
        }else {
            pengeluaranAdapter = new PengeluaranAdapter(this, pengeluaranModels);
            recyclerView.setAdapter(pengeluaranAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_search:
                searchDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
