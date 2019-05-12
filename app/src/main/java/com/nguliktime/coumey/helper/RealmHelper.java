package com.nguliktime.coumey.helper;

import android.util.Log;

import com.nguliktime.coumey.model.JurnalModel;
import com.nguliktime.coumey.model.KegiatanModel;
import com.nguliktime.coumey.model.PengeluaranModel;
import com.nguliktime.coumey.model.VidconModel;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm){
        this.realm = realm;
    }

    /* Part of Save */
    public void save(final PengeluaranModel pengeluaranModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(PengeluaranModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    pengeluaranModel.setId(nextId);
                    PengeluaranModel model = realm.copyToRealm(pengeluaranModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    public void saveJurnal(final JurnalModel jurnalModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(JurnalModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    jurnalModel.setId(nextId);
                    jurnalModel.setKeterangan("Belum Selesai");
                    JurnalModel model = realm.copyToRealm(jurnalModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    public void saveVidcon(final VidconModel vidconModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(VidconModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    vidconModel.setId(nextId);
                    vidconModel.setKeterangan("Belum Selesai");
                    VidconModel model = realm.copyToRealm(vidconModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    public void saveKegiatan(final KegiatanModel kegiatanModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(KegiatanModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    kegiatanModel.setId(nextId);
                    kegiatanModel.setKeterangan("Belum Selesai");
                    KegiatanModel model = realm.copyToRealm(kegiatanModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }


    /* Part of get data */
    public List<PengeluaranModel> getAllData(){
        RealmResults<PengeluaranModel> results = realm.where(PengeluaranModel.class).findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<PengeluaranModel> getSpendingByName(String name){
        RealmResults<PengeluaranModel> results = realm.where(PengeluaranModel.class)
                .like("nama", name, Case.INSENSITIVE)
                .equalTo("nama", name, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<KegiatanModel> get_kegiatan_by_name(String kegiatan){
        RealmResults<KegiatanModel> results = realm.where(KegiatanModel.class)
                .like("kegiatan", "?"+kegiatan+"?", Case.INSENSITIVE)
                .equalTo("kegiatan", kegiatan, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<JurnalModel> getJurnalByMateri(String tugas){
        RealmResults<JurnalModel> results = realm.where(JurnalModel.class)
                .like("tugas", tugas, Case.INSENSITIVE)
                .equalTo("tugas", tugas, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<PengeluaranModel> getByDate(String date){
        RealmResults<PengeluaranModel> results = realm.where(PengeluaranModel.class)
                .equalTo("tanggal", date, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<KegiatanModel> get_kegiatan_by_date(String date){
        RealmResults<KegiatanModel> results = realm.where(KegiatanModel.class)
                .equalTo("tanggal", date, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<KegiatanModel> get_kegiatan_by_keterangan(String ket){
        RealmResults<KegiatanModel> results = realm.where(KegiatanModel.class)
                .equalTo("keterangan", ket, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<JurnalModel> getByMapel(String mapel){
        RealmResults<JurnalModel> results = realm.where(JurnalModel.class)
                .equalTo("mapel", mapel, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<JurnalModel> getByMinggu(String minggu){
        RealmResults<JurnalModel> results = realm.where(JurnalModel.class)
                .equalTo("minggu", minggu, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public VidconModel getVidconDate(String tanggal, String keterangan){
        VidconModel model = realm.where(VidconModel.class)
                .equalTo("tanggal", tanggal, Case.INSENSITIVE)
                .equalTo("keterangan", keterangan)
                .findFirst();
        return model;
    }

    public List<JurnalModel> getJurnal(){
        RealmResults<JurnalModel> results = realm.where(JurnalModel.class).findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<KegiatanModel> getKegiatan(){
        RealmResults<KegiatanModel> results = realm.where(KegiatanModel.class).findAll()
                .sort("id", Sort.ASCENDING);
        return results;
    }

    public List<VidconModel> getVidcon(){
        RealmResults<VidconModel> results = realm.where(VidconModel.class).findAll()
                .sort("id", Sort.ASCENDING);
        return results;
    }

    public List<PengeluaranModel> getPengeluaranByMonth(String bulan){
        RealmResults<PengeluaranModel> results = realm.where(PengeluaranModel.class)
                .equalTo("bulan", bulan, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<JurnalModel> getByKeterangan(String keterangan){
        RealmResults<JurnalModel> results  = realm.where(JurnalModel.class)
                .equalTo("keterangan", keterangan, Case.INSENSITIVE)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    public List<PengeluaranModel> getSpendingBetweenMonth(Integer bulan1, Integer bulan2){
        RealmResults<PengeluaranModel> results = realm.where(PengeluaranModel.class)
                .greaterThanOrEqualTo("idBulan", bulan1)
                .lessThanOrEqualTo("idBulan", bulan2)
                .findAll()
                .sort("id", Sort.DESCENDING);
        return results;
    }

    /* Part of Update */
    public void update(final Integer id, final String nama, final Integer jumlah, final Integer harga, final Integer subtotal, final String tanggal){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PengeluaranModel model = realm.where(PengeluaranModel.class)
                        .equalTo("id", id)
                        .findFirst();
                model.setNama(nama);
                model.setJumlah(jumlah);
                model.setHarga(harga);
                model.setSubtotal(subtotal);
                model.setTanggal(tanggal);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("pppp", "onSuccess: Update Successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public void updateJurnal(final Integer id, final String mapel, final String minggu, final String tugas, final String keterangan, final String tanggal){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                JurnalModel model = realm.where(JurnalModel.class)
                        .equalTo("id", id)
                        .findFirst();
                model.setMapel(mapel);
                model.setMinggu(minggu);
                model.setTugas(tugas);
                model.setKeterangan(keterangan);
                model.setDeadline(tanggal);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("pppp", "onSuccess: Update Successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public void update_kegiatan(final Integer id, final String kegiatan, final String divisi, final String keterangan, final String tanggal){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                KegiatanModel model = realm.where(KegiatanModel.class)
                        .equalTo("id", id)
                        .findFirst();
                model.setKegiatan(kegiatan);
                model.setDivisi(divisi);
                model.setKeterangan(keterangan);
                model.setTanggal(tanggal);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("pppp", "onSuccess: Update Successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }

    public void updateVidcon(final Integer id, final String mapel, final String materi, final String tanggal, final String jam, final String keterangan){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                VidconModel model = realm.where(VidconModel.class)
                        .equalTo("id", id)
                        .findFirst();
                model.setMapel(mapel);
                model.setMateri(materi);
                model.setJam(jam);
                model.setKeterangan(keterangan);
                model.setTanggal(tanggal);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("pppp", "onSuccess: Update Successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }


    /* Part of Delete */
    public void delete(Integer id){
        final RealmResults<PengeluaranModel> model = realm.where(PengeluaranModel.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }

    public void deleteJurnal(Integer id){
        final RealmResults<JurnalModel> model = realm.where(JurnalModel.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }

    public void deleteVidcon(Integer id){
        final RealmResults<VidconModel> model = realm.where(VidconModel.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }

    public void deleteKegiatan(Integer id){
        final RealmResults<KegiatanModel> model = realm.where(KegiatanModel.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }
}
