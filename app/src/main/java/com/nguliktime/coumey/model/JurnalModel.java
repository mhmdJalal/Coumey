package com.nguliktime.coumey.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class JurnalModel extends RealmObject {

    @PrimaryKey private Integer id;
    private String mapel;
    private String minggu;
    private String tugas;
    private String deadline;
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

    public void setMinggu(String minggu) {
        this.minggu = minggu;
    }

    public String getMinggu() {
        return minggu;
    }

    public void setTugas(String tugas) {
        this.tugas = tugas;
    }

    public String getTugas() {
        return tugas;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
