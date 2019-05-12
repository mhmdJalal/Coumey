package com.nguliktime.coumey.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KegiatanModel extends RealmObject {
    @PrimaryKey private Integer id;
    private String kegiatan;
    private String divisi;
    private String tanggal;
    private String keterangan;

    @Override
    public Realm getRealm() {
        return super.getRealm();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public Integer getId() {
        return id;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public String getDivisi() {
        return divisi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
