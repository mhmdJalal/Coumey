package com.nguliktime.coumey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.nguliktime.coumey.R;
import com.nguliktime.coumey.adapter.JurnalAdapter;
import com.nguliktime.coumey.adapter.KegiatanAdapter;
import com.nguliktime.coumey.adapter.PengeluaranAdapter;
import com.nguliktime.coumey.helper.RealmHelper;
import com.nguliktime.coumey.model.JurnalModel;
import com.nguliktime.coumey.model.KegiatanModel;
import com.nguliktime.coumey.model.PengeluaranModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DaftarFilterActivity extends AppCompatActivity {
    Realm realm;
    RealmHelper realmHelper;
    TextView pengeluaran;
    RecyclerView recyclerView;
    CardView cardView;
    Toolbar toolbar;
    LinearLayout linearLayout_1;

    PengeluaranAdapter pengeluaranAdapter;
    List<PengeluaranModel> pengeluaranModels;

    JurnalAdapter jurnalAdapter;
    List<JurnalModel> jurnalModels;

    KegiatanAdapter kegiatanAdapter;
    List<KegiatanModel> kegiatanModels;

    private String filtering = null;
    private String bulan = null;
    private String KETERANGAN = null;
    private String levelFilter = null;
    private String TANGGAL = null;
    private String MAPEL = null;
    private String MINGGU = null;
    private Integer bulan1 = null;
    private Integer bulan2 = null;
    private Long lpengeluaran = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_filter);

        toolbar = findViewById(R.id.toolbar);
        cardView = findViewById(R.id.cardView);
        recyclerView = findViewById(R.id.recyclerView);
        pengeluaran = findViewById(R.id.tvPengeluaran);
        linearLayout_1 = findViewById(R.id.linear_1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Setup Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getDefaultInstance();

        realmHelper = new RealmHelper(realm);
        pengeluaranModels = new ArrayList<>();
        jurnalModels = new ArrayList<>();

        filtering = getIntent().getStringExtra("filter");
        levelFilter = getIntent().getStringExtra("levelFilter");

        if (levelFilter.equals("coumey")){
            getSupportActionBar().setTitle("Coumey");
        }else if(levelFilter.endsWith("jurnal")){
            getSupportActionBar().setTitle("Jurnal Mapel");
            cardView.setVisibility(View.GONE);
        }else{
            getSupportActionBar().setTitle("Jurnal Kegiatan");
            cardView.setVisibility(View.GONE);
        }

        switch (filtering){
            case "byTanggal":
                TANGGAL = getIntent().getStringExtra("tanggal");
                if (levelFilter.equals("coumey")){
                    pengeluaranModels = realmHelper.getByDate(TANGGAL);
                    RealmResults<PengeluaranModel> total = realm.where(PengeluaranModel.class)
                            .equalTo("tanggal", TANGGAL).findAll();
                    lpengeluaran = total.sum("subtotal").longValue();
                    pengeluaran.setText(String.valueOf("Rp " + lpengeluaran));
                }else {
                    kegiatanModels = realmHelper.get_kegiatan_by_date(TANGGAL);
                }
                break;
            case "byBulan":
                bulan = getIntent().getStringExtra("bulan");
                pengeluaranModels = realmHelper.getPengeluaranByMonth(bulan);
                RealmResults<PengeluaranModel> total = realm.where(PengeluaranModel.class)
                        .equalTo("bulan", bulan).findAll();
                lpengeluaran = total.sum("subtotal").longValue();
                pengeluaran.setText(String.valueOf("Rp " + lpengeluaran));
                break;
            case "betweenBulan":
                bulan1 = Integer.parseInt(getIntent().getStringExtra("bulan1"));
                bulan2 = Integer.parseInt(getIntent().getStringExtra("bulan2"));
                pengeluaranModels = realmHelper.getSpendingBetweenMonth(bulan1, bulan2);
                total = realm.where(PengeluaranModel.class)
                        .greaterThanOrEqualTo("idBulan", bulan1)
                        .lessThanOrEqualTo("idBulan", bulan2)
                        .findAll();
                lpengeluaran = total.sum("subtotal").longValue();
                pengeluaran.setText(String.valueOf("Rp " + lpengeluaran));
                break;
            case "byKeterangan":
                KETERANGAN = getIntent().getStringExtra("keterangan");
                if (levelFilter.equals("coumey")){
                    jurnalModels = realmHelper.getByKeterangan(KETERANGAN);
                }else{
                    kegiatanModels = realmHelper.get_kegiatan_by_keterangan(KETERANGAN);
                }
                break;
            case "byMapel":
                MAPEL = getIntent().getStringExtra("mapel");
                jurnalModels = realmHelper.getByMapel(MAPEL);
                break;
            case "byMinggu":
                MINGGU = getIntent().getStringExtra("minggu");
                jurnalModels = realmHelper.getByMinggu(MINGGU);
                break;
        }

        if (levelFilter.equals("coumey")){
            if (pengeluaranModels.isEmpty()){
                if (filtering == null){
                    showToast("Tidak ada yang anda pilih");
                }else{
                    switch (filtering){
                        case "byTanggal":
                            showToast("Pada tanggal yang anda pilih belum ada pengeluaran");
                            break;
                        case "byBulan":
                            showToast("Pada bulan "+bulan+" belum ada pengeluaran");
                            break;
                        case "betweenBulan":
                            showToast("Belum ada pengeluaran antara bulan tersebut");
                            break;
                    }
                }
                finish();
            }else {
                showSpending();
            }
        }else if(levelFilter.equals("jurnal")){
            if (jurnalModels.isEmpty()){
                switch (filtering){
                    case "byKeterangan":
                        showToast("Filter untuk kategori '" + KETERANGAN + "' tidak ditemukan");
                        break;
                    case "byMapel":
                        showToast("Filter untuk Mata Pelajaran '" + MAPEL + "' tidak ditemukan");
                        break;
                    case "byMinggu":
                        showToast("Filter untuk '" + MINGGU + "' tidak ditemukan");
                        break;
                    default:
                        showToast("Tidak ada yang anda pilih");
                        break;
                }
                finish();
            }else {
                showJurnal();
            }
        }else {
            if (kegiatanModels.isEmpty()){
                switch (filtering){
                    case "byTanggal":
                        showToast("Pada tanggal yang anda pilih belum ada pengeluaran");
                        break;
                    case "byKeterangan":
                        showToast("Filter untuk kategori '" + KETERANGAN + "' tidak ditemukan");
                        break;
                    default:
                        showToast("Tidak ada yang anda pilih");
                        break;
                }
                finish();
            }else {
                showKegiatan();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (levelFilter.equals("coumey")){
            showSpending();
        }else if(levelFilter.equals("jurnal")){
            showJurnal();
        }else{
            showKegiatan();
        }
    }

    private void showToast(String pesan){
        MDToast toast = MDToast.makeText(this, pesan, 1000, MDToast.TYPE_INFO);
        toast.show();
    }

    public void showSpending(){
        if (pengeluaranModels.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            linearLayout_1.setVisibility(View.VISIBLE);
        }else{
            pengeluaranAdapter = new PengeluaranAdapter(this, pengeluaranModels);
            recyclerView.setAdapter(pengeluaranAdapter);
        }
    }

    public void showJurnal(){
        if (jurnalModels.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            linearLayout_1.setVisibility(View.VISIBLE);
        }else{
            jurnalAdapter = new JurnalAdapter(this, jurnalModels);
            recyclerView.setAdapter(jurnalAdapter);
        }
    }

    public void showKegiatan(){
        if (kegiatanModels.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            linearLayout_1.setVisibility(View.VISIBLE);
        }else{
            kegiatanAdapter = new KegiatanAdapter(this, kegiatanModels);
            recyclerView.setAdapter(kegiatanAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        return true;
    }
}
