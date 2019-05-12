package com.nguliktime.coumey.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class VidconModel extends RealmObject {

    @PrimaryKey private Integer id;
    private String mapel;
    private String materi;
    private String tanggal;
    private String jam;
    private String keterangan;

    @Override
    public Realm getRealm() {
        return super.getRealm();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMateri(String materi) {
        this.materi = materi;
    }

    public String getMateri() {
        return materi;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getJam() {
        return jam;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
